class Solution {
    public int distinctSubseqII(String s) {

        final long MOD = 1_000_000_007;

        long[] dp = new long[26];
        long total = 0;

        for (char ch : s.toCharArray()) {

            int index = ch - 'a';

            long old = dp[index];

            dp[index] = (total + 1) % MOD;

            total = (total + dp[index] - old + MOD) % MOD;
        }

        return (int) total;
    }
}