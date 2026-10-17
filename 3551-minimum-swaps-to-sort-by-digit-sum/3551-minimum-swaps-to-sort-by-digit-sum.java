import java.util.*;

class Solution {
    public int minSwaps(int[] nums) {
        int n = nums.length;
        int ans = 0;

        // Step 1: Sort nums by digit sum, then by value
        int[] sortedNums = Arrays.stream(nums)
                .boxed()
                .sorted(Comparator.comparingInt((Integer num) -> getDigitSum(num))
                        .thenComparingInt(num -> num))
                .mapToInt(Integer::intValue)
                .toArray();

        // Step 2: Map each number to its target index in sorted array
        Map<Integer, Integer> numToIndex = new HashMap<>();
        for (int i = 0; i < n; i++) {
            numToIndex.put(sortedNums[i], i);
        }

        // Step 3: Cycle detection to count swaps
        Set<Integer> seen = new HashSet<>();
        for (int i = 0; i < n; i++) {
            if (seen.contains(i) || numToIndex.get(nums[i]) == i) continue;

            int cycleSize = 0;
            int j = i;
            while (seen.add(j)) {
                j = numToIndex.get(nums[j]);
                cycleSize++;
            }
            ans += Math.max(cycleSize - 1, 0);
        }

        return ans;
    }

    // Helper function to compute digit sum
    private int getDigitSum(int num) {
        int sum = 0;
        while (num > 0) {
            sum += num % 10;
            num /= 10;
        }
        return sum;
    }
}
