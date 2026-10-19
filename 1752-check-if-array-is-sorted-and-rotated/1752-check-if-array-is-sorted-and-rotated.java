class Solution {
    public boolean check(int[] nums) {
        int count = 0;
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            // Compare current element with the next (circular using modulo)
            if (nums[i] > nums[(i + 1) % n]) {
                count++;
            }
            // More than one drop means not sorted & rotated
            if (count > 1) return false;
        }

        return true;
    }
}
