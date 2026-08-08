
class Solution {
    public int[] validSequence(String word1, String word2) {

        int n = word1.length();
        int m = word2.length();

        // suf[i] = earliest index in word1 from which
        // word2[j...] can be matched exactly.
        int[] suf = new int[m + 1];

        // -1 means impossible
        for (int i = 0; i <= m; i++) {
            suf[i] = -1;
        }

        // Empty suffix can always be matched
        suf[m] = n;

        int i = n - 1;

        for (int j = m - 1; j >= 0; j--) {

            while (i >= 0 && word1.charAt(i) != word2.charAt(j)) {
                i--;
            }

            if (i < 0) {
                break;
            }

            suf[j] = i;
            i--;
        }

        int[] ans = new int[m];

        int p = 0;
        int j = 0;
        boolean usedMismatch = false;

        while (p < n && j < m) {

            // Exact match
            if (word1.charAt(p) == word2.charAt(j)) {

                // Take it
                ans[j] = p;
                p++;
                j++;

            } else {

                // We can use our one mismatch
                if (!usedMismatch) {

                    /*
                     * After taking p as mismatch,
                     * word2[j+1...] must be matched exactly.
                     */
                    if (j + 1 == m || 
                        (suf[j + 1] != -1 && suf[j + 1] > p)) {

                        ans[j] = p;
                        usedMismatch = true;
                        p++;
                        j++;
                    } else {
                        p++;
                    }

                } else {
                    p++;
                }
            }
        }

        if (j != m) {
            return new int[0];
        }

        return ans;
    }
}