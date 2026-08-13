class Solution {

    class Node {
        int l, r;
        int prefix, suffix, max;
        char leftChar, rightChar;

        Node(int l, int r) {
            this.l = l;
            this.r = r;
        }
    }

    Node[] tree;
    char[] s;

    public int[] longestRepeating(String str, String queryCharacters,
                                  int[] queryIndices) {

        s = str.toCharArray();
        int n = s.length;

        tree = new Node[4 * n];

        build(1, 0, n - 1);

        int k = queryIndices.length;
        int[] ans = new int[k];

        for (int i = 0; i < k; i++) {

            int index = queryIndices[i];
            char ch = queryCharacters.charAt(i);

            s[index] = ch;

            update(1, 0, n - 1, index);

            ans[i] = tree[1].max;
        }

        return ans;
    }

    private void build(int node, int l, int r) {

        tree[node] = new Node(l, r);

        if (l == r) {

            tree[node].prefix = 1;
            tree[node].suffix = 1;
            tree[node].max = 1;

            tree[node].leftChar = s[l];
            tree[node].rightChar = s[l];

            return;
        }

        int mid = l + (r - l) / 2;

        build(node * 2, l, mid);
        build(node * 2 + 1, mid + 1, r);

        merge(node);
    }

    private void update(int node, int l, int r, int index) {

        if (l == r) {

            tree[node].prefix = 1;
            tree[node].suffix = 1;
            tree[node].max = 1;

            tree[node].leftChar = s[l];
            tree[node].rightChar = s[l];

            return;
        }

        int mid = l + (r - l) / 2;

        if (index <= mid) {
            update(node * 2, l, mid, index);
        } else {
            update(node * 2 + 1, mid + 1, r, index);
        }

        merge(node);
    }

    private void merge(int node) {

        Node left = tree[node * 2];
        Node right = tree[node * 2 + 1];
        Node curr = tree[node];

        curr.leftChar = left.leftChar;
        curr.rightChar = right.rightChar;

        curr.prefix = left.prefix;
        curr.suffix = right.suffix;

        curr.max = Math.max(left.max, right.max);

        if (left.rightChar == right.leftChar) {

            curr.max = Math.max(
                curr.max,
                left.suffix + right.prefix
            );

            int leftLength = left.r - left.l + 1;
            int rightLength = right.r - right.l + 1;

            if (left.prefix == leftLength) {
                curr.prefix = leftLength + right.prefix;
            }

            if (right.suffix == rightLength) {
                curr.suffix = rightLength + left.suffix;
            }
        }
    }
}