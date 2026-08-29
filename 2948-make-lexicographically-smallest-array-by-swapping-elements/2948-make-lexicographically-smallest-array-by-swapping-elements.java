class Solution {
    public int[] lexicographicallySmallestArray(int[] nums, int limit) {

        int n = nums.length;

        // Store indices
        Integer[] indices = new Integer[n];

        for (int i = 0; i < n; i++) {
            indices[i] = i;
        }

        // Sort indices according to nums values
        Arrays.sort(indices, (i, j) -> Integer.compare(nums[i], nums[j]));

        int[] ans = new int[n];

        int i = 0;

        while (i < n) {

            int j = i + 1;

            // Find one connected group
            while (j < n &&
                   nums[indices[j]] - nums[indices[j - 1]] <= limit) {
                j++;
            }

            // Get original indices of this group
            Integer[] group = Arrays.copyOfRange(indices, i, j);

            // Sort original indices
            Arrays.sort(group);

            // Smallest values go to smallest indices
            for (int k = i; k < j; k++) {
                ans[group[k - i]] = nums[indices[k]];
            }

            i = j;
        }

        return ans;
    }
}