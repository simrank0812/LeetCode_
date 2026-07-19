class Solution {
    public String smallestSubsequence(String s) {
        int[] last = new int[26];
        for (int i = 0; i < s.length(); i++) {
            last[s.charAt(i) - 'a'] = i;
        }

        boolean[] seen = new boolean[26];
        StringBuilder stack = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (seen[c - 'a']) {
                continue;
            }

            while (stack.length() > 0) {
                char top = stack.charAt(stack.length() - 1);
                if (top > c && last[top - 'a'] > i) {
                    seen[top - 'a'] = false;
                    stack.deleteCharAt(stack.length() - 1);
                } else {
                    break;
                }
            }

            stack.append(c);
            seen[c - 'a'] = true;
        }

        return stack.toString();
    }
}