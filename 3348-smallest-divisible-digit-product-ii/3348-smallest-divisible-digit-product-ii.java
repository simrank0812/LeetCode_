class Solution {
    private int[][] g;          // g[i][j] = min digits (from 2,3,4,6,8,9) covering 2^i * 3^j
    private static final int[] E2 = {0,0,1,0,2,0,1,0,3,0};
    private static final int[] E3 = {0,0,0,1,0,0,1,0,0,2};
    private static final int[] E5 = {0,0,0,0,0,1,0,0,0,0};
    private static final int[] E7 = {0,0,0,0,0,0,0,1,0,0};

    public String smallestNumber(String num, long t) {
        int a = 0, b = 0, c = 0, d = 0;
        long x = t;
        while (x % 2 == 0) { x /= 2; a++; }
        while (x % 3 == 0) { x /= 3; b++; }
        while (x % 5 == 0) { x /= 5; c++; }
        while (x % 7 == 0) { x /= 7; d++; }
        if (x != 1) return "-1";

        // Build tiny DP table g (a <= 47, b <= 30 since t <= 10^14)
        g = new int[a + 1][b + 1];
        int[] cand = {2, 3, 4, 6, 8, 9};
        for (int i = 0; i <= a; i++) {
            for (int j = 0; j <= b; j++) {
                if (i == 0 && j == 0) continue;
                int best = Integer.MAX_VALUE;
                for (int dig : cand) {
                    int ii = Math.max(0, i - E2[dig]);
                    int jj = Math.max(0, j - E3[dig]);
                    if (ii == i && jj == j) continue;
                    best = Math.min(best, g[ii][jj]);
                }
                g[i][j] = best + 1;
            }
        }

        int n = num.length();
        int[] digs = new int[n];
        int firstZero = n;
        for (int i = 0; i < n; i++) {
            digs[i] = num.charAt(i) - '0';
            if (digs[i] == 0 && firstZero == n) firstZero = i;
        }

        // Prefix counts of prime exponents
        int[] c2 = new int[n + 1], c3 = new int[n + 1], c5 = new int[n + 1], c7 = new int[n + 1];
        for (int i = 0; i < n; i++) {
            c2[i + 1] = c2[i] + E2[digs[i]];
            c3[i + 1] = c3[i] + E3[digs[i]];
            c5[i + 1] = c5[i] + E5[digs[i]];
            c7[i + 1] = c7[i] + E7[digs[i]];
        }

        // Case 1: num itself works
        if (firstZero == n && c2[n] >= a && c3[n] >= b && c5[n] >= c && c7[n] >= d) {
            return num;
        }

        // Case 2: keep a prefix num[0..i-1], place a bigger digit at i,
        //         then the smallest feasible suffix. Try i from right to left.
        int start = Math.min(n - 1, firstZero);
        for (int i = start; i >= 0; i--) {
            int k = n - 1 - i;  // suffix length after position i
            int ra0 = Math.max(0, a - c2[i]);
            int rb0 = Math.max(0, b - c3[i]);
            int rc0 = Math.max(0, c - c5[i]);
            int rd0 = Math.max(0, d - c7[i]);

            // Prune: one digit can lower f by at most 1
            if (f(ra0, rb0, rc0, rd0) > k + 1) continue;

            for (int dg = digs[i] + 1; dg <= 9; dg++) {
                int ra = Math.max(0, ra0 - E2[dg]);
                int rb = Math.max(0, rb0 - E3[dg]);
                int rc = Math.max(0, rc0 - E5[dg]);
                int rd = Math.max(0, rd0 - E7[dg]);
                if (f(ra, rb, rc, rd) <= k) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(num, 0, i).append((char) ('0' + dg));
                    fill(sb, k, ra, rb, rc, rd);
                    return sb.toString();
                }
            }
        }

        // Case 3: answer is longer than num
        int L = Math.max(n + 1, f(a, b, c, d));
        StringBuilder sb = new StringBuilder();
        fill(sb, L, a, b, c, d);
        return sb.toString();
    }

    // Minimum digits needed to cover requirement 2^ra * 3^rb * 5^rc * 7^rd
    private int f(int ra, int rb, int rc, int rd) {
        return g[ra][rb] + rc + rd;
    }

    // Append the smallest zero-free string of length k covering the requirement
    private void fill(StringBuilder sb, int k, int ra, int rb, int rc, int rd) {
        int needed = f(ra, rb, rc, rd);
        for (int i = 0; i < k - needed; i++) sb.append('1');   // leading 1s
        while (needed > 0) {
            for (int dg = 2; dg <= 9; dg++) {
                int nra = Math.max(0, ra - E2[dg]);
                int nrb = Math.max(0, rb - E3[dg]);
                int nrc = Math.max(0, rc - E5[dg]);
                int nrd = Math.max(0, rd - E7[dg]);
                if (f(nra, nrb, nrc, nrd) <= needed - 1) {
                    sb.append((char) ('0' + dg));
                    ra = nra; rb = nrb; rc = nrc; rd = nrd;
                    needed--;
                    break;
                }
            }
        }
    }
}