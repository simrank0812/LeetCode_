class Solution {
    public int minMoves(String[] classroom, int energy) {

        int m = classroom.length;
        int n = classroom[0].length();

        int[][] litterId = new int[m][n];

        int startR = 0;
        int startC = 0;
        int litterCount = 0;

        // Find S and assign an ID to every L
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                char ch = classroom[i].charAt(j);

                if (ch == 'S') {
                    startR = i;
                    startC = j;
                }
                else if (ch == 'L') {
                    litterId[i][j] = litterCount++;
                }
            }
        }

        // No litter
        if (litterCount == 0) {
            return 0;
        }

        int totalMasks = 1 << litterCount;
        int allCollected = totalMasks - 1;

        /*
         * visited[row][col][energy][mask]
         *
         * true = this state has already been visited
         */
        boolean[][][][] visited =
            new boolean[m][n][energy + 1][totalMasks];

        // State: row, col, remaining energy, collected mask
        Queue<int[]> queue = new LinkedList<>();

        queue.offer(new int[]{
            startR,
            startC,
            energy,
            0
        });

        visited[startR][startC][energy][0] = true;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        int moves = 0;

        while (!queue.isEmpty()) {

            int size = queue.size();

            // Process one BFS level
            while (size-- > 0) {

                int[] state = queue.poll();

                int r = state[0];
                int c = state[1];
                int currEnergy = state[2];
                int mask = state[3];

                // All litter collected
                if (mask == allCollected) {
                    return moves;
                }

                // No energy left
                if (currEnergy == 0) {
                    continue;
                }

                // Try 4 directions
                for (int k = 0; k < 4; k++) {

                    int nr = r + dr[k];
                    int nc = c + dc[k];

                    // Outside grid
                    if (nr < 0 || nr >= m ||
                        nc < 0 || nc >= n) {
                        continue;
                    }

                    // Obstacle
                    if (classroom[nr].charAt(nc) == 'X') {
                        continue;
                    }

                    // Every normal move consumes 1 energy
                    int newEnergy = currEnergy - 1;

                    // R resets energy
                    if (classroom[nr].charAt(nc) == 'R') {
                        newEnergy = energy;
                    }

                    // Update litter mask
                    int newMask = mask;

                    if (classroom[nr].charAt(nc) == 'L') {

                        int id = litterId[nr][nc];

                        newMask = mask | (1 << id);
                    }

                    // Avoid repeated states
                    if (!visited[nr][nc][newEnergy][newMask]) {

                        visited[nr][nc][newEnergy][newMask] = true;

                        queue.offer(new int[]{
                            nr,
                            nc,
                            newEnergy,
                            newMask
                        });
                    }
                }
            }

            moves++;
        }

        return -1;
    }
}