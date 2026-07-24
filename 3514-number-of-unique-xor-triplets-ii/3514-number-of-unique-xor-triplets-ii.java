class Solution {
    public int uniqueXorTriplets(int[] nums) {
        int mx = 0;
        for (int x : nums) {
            mx = Math.max(mx, x);
        }
        mx <<= 1;

        boolean[] pairXor = new boolean[mx];

        // Store all possible XORs of two elements
        for (int a : nums) {
            for (int b : nums) {
                pairXor[a ^ b] = true;
            }
        }

        boolean[] ans = new boolean[mx];

        // XOR every pair XOR with a third element
        for (int x = 0; x < mx; x++) {
            if (pairXor[x]) {
                for (int c : nums) {
                    ans[x ^ c] = true;
                }
            }
        }

        int count = 0;
        for (boolean v : ans) {
            if (v) count++;
        }

        return count;
    }
}