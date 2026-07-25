class Solution {
    public int[] canSeePersonsCount(int[] heights) {
        int n = heights.length;
        int[] ans = new int[n];
        Stack<Integer> stack = new Stack<>();

        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && heights[i] > stack.peek()) {
                stack.pop();
                ans[i]++;
            }

            if (!stack.isEmpty()) {
                ans[i]++;
            }

            stack.push(heights[i]);
        }

        return ans;
    }
}