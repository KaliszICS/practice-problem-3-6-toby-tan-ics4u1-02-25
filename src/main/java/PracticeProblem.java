import java.util.*;

public class PracticeProblem {

    public static int searchMazeMoves(String[][] maze) {
        int[] start = findStart(maze);
        int rows = maze.length;
        int cols = maze[0].length;
        boolean[][] visited = new boolean[rows][cols];
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{start[0], start[1], 0}); // {row, col, steps}
        visited[start[0]][start[1]] = true;

        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; // down, up, right, left

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int r = curr[0], c = curr[1], steps = curr[2];

            if (maze[r][c].equals("F")) {
                return steps;
            }

            for (int[] d : directions) {
                int nr = r + d[0];
                int nc = c + d[1];
                if (inBounds(nr, nc, maze) && !visited[nr][nc] && !maze[nr][nc].equals("*")) {
                    visited[nr][nc] = true;
                    queue.add(new int[]{nr, nc, steps + 1});
                }
            }
        }

        return -1; // if no path found
    }

    public static int noOfPaths(String[][] maze) {
        int[] start = findStart(maze);
        boolean[][] visited = new boolean[maze.length][maze[0].length];
        return countPaths(maze, visited, start[0], start[1]);
    }

    private static int countPaths(String[][] maze, boolean[][] visited, int r, int c) {
        if (!inBounds(r, c, maze) || visited[r][c] || maze[r][c].equals("*")) return 0;
        if (maze[r][c].equals("F")) return 1;

        visited[r][c] = true;

        int total = 0;
        total += countPaths(maze, visited, r + 1, c);
        total += countPaths(maze, visited, r - 1, c);
        total += countPaths(maze, visited, r, c + 1);
        total += countPaths(maze, visited, r, c - 1);

        visited[r][c] = false; // backtrack
        return total;
    }

    private static boolean inBounds(int r, int c, String[][] maze) {
        return r >= 0 && r < maze.length && c >= 0 && c < maze[0].length;
    }

    private static int[] findStart(String[][] maze) {
        int row = maze.length - 1;
        for (int col = 0; col < maze[0].length; col++) {
            if ("S".equals(maze[row][col])) {
                return new int[]{row, col};
            }
        }
        throw new RuntimeException("Start 'S' not found in bottom row");
    }
}
