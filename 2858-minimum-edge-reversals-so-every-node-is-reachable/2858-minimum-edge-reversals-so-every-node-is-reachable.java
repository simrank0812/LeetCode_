import java.util.*;

class Solution {
    List<int[]>[] graph;
    int[] ans;

    public int[] minEdgeReversals(int n, int[][] edges) {
        graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        // cost = 0 -> original direction
        // cost = 1 -> reversed direction
        for (int[] e : edges) {
            int u = e[0], v = e[1];
            graph[u].add(new int[]{v, 0});
            graph[v].add(new int[]{u, 1});
        }

        ans = new int[n];

        ans[0] = dfs1(0, -1);
        dfs2(0, -1);

        return ans;
    }

    // Count reversals needed when root is 0
    private int dfs1(int node, int parent) {
        int res = 0;
        for (int[] next : graph[node]) {
            int nei = next[0];
            int cost = next[1];
            if (nei == parent) continue;
            res += cost + dfs1(nei, node);
        }
        return res;
    }

    // Reroot DP
    private void dfs2(int node, int parent) {
        for (int[] next : graph[node]) {
            int nei = next[0];
            int cost = next[1];
            if (nei == parent) continue;

            if (cost == 0)
                ans[nei] = ans[node] + 1;
            else
                ans[nei] = ans[node] - 1;

            dfs2(nei, node);
        }
    }
}