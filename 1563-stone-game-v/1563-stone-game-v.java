class Solution {

    int[][] dp;
    int[] prefix;
    int[] stones;

    public int stoneGameV(int[] stoneValue) {

        stones = stoneValue;
        int n = stones.length;

        prefix = new int[n + 1];

        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + stones[i];
        }

        dp = new int[n][n];

        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], -1);
        }

        return solve(0, n - 1);
    }

    private int solve(int left, int right) {

        // Only one stone
        if (left >= right) {
            return 0;
        }

        if (dp[left][right] != -1) {
            return dp[left][right];
        }

        int ans = 0;

        // Sum of entire range
        int total = prefix[right + 1] - prefix[left];

        int leftSum = 0;

        for (int k = left; k < right; k++) {

            leftSum += stones[k];

            int rightSum = total - leftSum;

            if (leftSum < rightSum) {

                // Right part is discarded
                ans = Math.max(
                    ans,
                    leftSum + solve(left, k)
                );

            } 
            else if (leftSum > rightSum) {

                // Left part is discarded
                ans = Math.max(
                    ans,
                    rightSum + solve(k + 1, right)
                );

            } 
            else {

                // Equal sums: Alice can choose either side
                ans = Math.max(
                    ans,
                    Math.max(
                        leftSum + solve(left, k),
                        rightSum + solve(k + 1, right)
                    )
                );
            }
        }

        return dp[left][right] = ans;
    }
}