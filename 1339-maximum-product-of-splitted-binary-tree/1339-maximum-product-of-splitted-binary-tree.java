class Solution {
    long total = 0;
    long max = 0;
    int MOD = 1000000007;

    public int maxProduct(TreeNode root) {
        total = findSum(root);
        dfs(root);
        return (int)(max % MOD);
    }

    private long findSum(TreeNode root) {
        if (root == null) return 0;
        return root.val + findSum(root.left) + findSum(root.right);
    }

    private long dfs(TreeNode root) {
        if (root == null) return 0;

        long sum = root.val + dfs(root.left) + dfs(root.right);
        max = Math.max(max, sum * (total - sum));

        return sum;
    }
}