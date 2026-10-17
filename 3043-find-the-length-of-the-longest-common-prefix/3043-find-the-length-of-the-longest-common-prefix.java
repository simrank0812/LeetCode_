class TrieNode {
    TrieNode[] children = new TrieNode[10];
}

class Trie {
    private TrieNode root = new TrieNode();

    // Insert a number (as string) into the Trie
    public void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int i = c - '0';
            if (node.children[i] == null) {
                node.children[i] = new TrieNode();
            }
            node = node.children[i];
        }
    }

    // Search for longest prefix match
    public int search(String word) {
        int prefixLength = 0;
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int i = c - '0';
            if (node.children[i] == null) break;
            node = node.children[i];
            prefixLength++;
        }
        return prefixLength;
    }
}

class Solution {
    public int longestCommonPrefix(int[] arr1, int[] arr2) {
        Trie trie = new Trie();
        int ans = 0;

        // Insert all numbers from arr1 into Trie
        for (int num : arr1) {
            trie.insert(Integer.toString(num));
        }

        // For each number in arr2, check longest prefix in Trie
        for (int num : arr2) {
            ans = Math.max(ans, trie.search(Integer.toString(num)));
        }

        return ans;
    }
}
