class Solution {
    public String minRemoveToMakeValid(String s) {
        StringBuilder sb = new StringBuilder(s);
        int open = 0;

        // Remove invalid ')'
        for (int i = 0; i < sb.length(); i++) {
            char c = sb.charAt(i);
            if (c == '(') {
                open++;
            } else if (c == ')') {
                if (open == 0) {
                    sb.setCharAt(i, '#');
                } else {
                    open--;
                }
            }
        }

        // Remove extra '(' from right to left
        for (int i = sb.length() - 1; i >= 0 && open > 0; i--) {
            if (sb.charAt(i) == '(') {
                sb.setCharAt(i, '#');
                open--;
            }
        }

        // Build answer
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) != '#') {
                ans.append(sb.charAt(i));
            }
        }

        return ans.toString();
    }
}