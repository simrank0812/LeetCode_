class Solution {
    public String shortestBeautifulSubstring(String s, int k) {
        int n = s.length();

        int left = 0;
        int ones = 0;

        String ans = "";

        for (int right = 0; right < n; right++) {

            // Add current character
            if (s.charAt(right) == '1') {
                ones++;
            }

            // Too many 1s -> shrink from left
            while (ones > k) {
                if (s.charAt(left) == '1') {
                    ones--;
                }
                left++;
            }

            // Remove unnecessary leading zeros
            while (left < right && s.charAt(left) == '0') {
                left++;
            }

            // Exactly k ones -> beautiful substring
            if (ones == k) {
                String curr = s.substring(left, right + 1);

                // Better length OR same length but lexicographically smaller
                if (ans.equals("")
                        || curr.length() < ans.length()
                        || (curr.length() == ans.length()
                            && curr.compareTo(ans) < 0)) {

                    ans = curr;
                }
            }
        }

        return ans;
    }
}