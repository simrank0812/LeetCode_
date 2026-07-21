class Solution {
    public int maxActiveSectionsAfterTrade(String s) {
        int n = s.length();
        int ans = 0;
        int pre = Integer.MIN_VALUE;
        int mx = 0;

        int i = 0;
        while (i < n) {
            int j = i;
            while (j < n && s.charAt(j) == s.charAt(i)) {
                j++;
            }

            int len = j - i;

            if (s.charAt(i) == '1') {
                ans += len;
            } else {
                mx = Math.max(mx, pre + len);
                pre = len;
            }

            i = j;
        }

        return ans + mx;
    }
}