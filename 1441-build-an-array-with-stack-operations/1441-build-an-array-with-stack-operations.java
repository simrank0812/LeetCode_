import java.util.*;

class Solution {
    public List<String> buildArray(int[] target, int n) {
        List<String> ans = new ArrayList<>();
        int index = 0;

        for (int i = 1; i <= n && index < target.length; i++) {
            ans.add("Push");

            if (i == target[index]) {
                index++;
            } else {
                ans.add("Pop");
            }
        }

        return ans;
    }
}