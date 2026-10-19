import java.util.*;

class Solution {
    public List<Integer> targetIndices(int[] nums, int target) {
        Arrays.sort(nums); // Step 1: Sort the array
        List<Integer> result = new ArrayList<>();
        
        // Step 2: Collect indices of target
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                result.add(i);
            }
        }
        
        return result;
    }
}
