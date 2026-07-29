class Solution {
    private static final long LIMIT = 1000001;

    public String smallestPalindrome(String s, int k) {
        int[] freq = new int[26];

        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        char mid = 0;

        for (int i = 0; i < 26; i++) {
            if ((freq[i] & 1) == 1) {
                mid = (char) ('a' + i);
                freq[i]--;
                break;
            }
        }

        int[] half = new int[26];
        int len = 0;

        for (int i = 0; i < 26; i++) {
            half[i] = freq[i] / 2;
            len += half[i];
        }

        if (countWays(half) < k)
            return "";

        StringBuilder left = new StringBuilder();

        for (int pos = 0; pos < len; pos++) {

            for (int c = 0; c < 26; c++) {

                if (half[c] == 0)
                    continue;

                half[c]--;

                long ways = countWays(half);

                if (ways >= k) {
                    left.append((char) ('a' + c));
                    break;
                } else {
                    k -= ways;
                    half[c]++;
                }
            }
        }

        String right = new StringBuilder(left).reverse().toString();

        if (mid == 0)
            return left.toString() + right;

        return left.toString() + mid + right;
    }


    private long countWays(int[] cnt) {
        int total = 0;

        for (int x : cnt)
            total += x;

        long ans = 1;

        for (int x : cnt) {
            if (x == 0)
                continue;

            ans *= combination(total, x);

            if (ans >= LIMIT)
                return LIMIT;

            total -= x;
        }

        return ans;
    }


    private long combination(int n, int r) {

        r = Math.min(r, n - r);

        long res = 1;

        for (int i = 1; i <= r; i++) {
            res = res * (n - i + 1) / i;

            if (res >= LIMIT)
                return LIMIT;
        }

        return res;
    }
}