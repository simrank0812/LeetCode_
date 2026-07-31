import java.util.*;

class Solution {
    public int minimumPushes(String word) {
        int[] freq = new int[26];

        for (char c : word.toCharArray()) {
            freq[c - 'a']++;
        }

        Arrays.sort(freq);

        int ans = 0;
        for (int i = 0; i < 26; i++) {
            ans += freq[25 - i] * (i / 8 + 1);
        }

        return ans;
    }
}