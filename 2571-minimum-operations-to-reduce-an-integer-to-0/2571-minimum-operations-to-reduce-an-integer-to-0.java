class Solution {
    HashMap<Integer, Integer> memo = new HashMap<>();

    public int minOperations(int n) {
        if (n == 0) return 0;
        if ((n & (n - 1)) == 0) return 1; // power of 2

        if (memo.containsKey(n)) return memo.get(n);

        int lowBit = Integer.lowestOneBit(n);

        int ans = 1 + Math.min(
                minOperations(n - lowBit),
                minOperations(n + lowBit)
        );

        memo.put(n, ans);
        return ans;
    }
}