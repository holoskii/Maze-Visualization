import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

class Solver {
    void shuffleDirs() {
        for(int count = 0; count < 2; count++) {
            int i = rnd.nextInt(4), j = rnd.nextInt(4);
            int[] temp = dirs[i];
            dirs[i] = dirs[j];
            dirs[j] = temp;
        }
    }

    public Solver (MazePanel _mp) {
        mp = _mp; a = mp.a;
        n = mp.n; m = mp.m;
        rnd = new Random();
        rnd.setSeed(rnd.nextLong());
    }

    public void solveStart(String s) {
        if (s.equals("Depth First Search"))
            DFS();
        else if (s.equals("Breadth First Search"))
            BFS();
        repaintDelay();
    }

    private void DFS() {
        Stack<Coordinate> s = new Stack<>();
        s.push(new Coordinate(1, 1));

        while(!s.empty()) {
            Coordinate p = s.peek();
            a[p.i][p.j] = MAZE.GENERATED;

            boolean f = true;
            shuffleDirs();
            for(int[] d : dirs) {
                int ni = p.i + d[0], nj = p.j + d[1];
                if (a[ni][nj] == MAZE.PASSAGE) {
                    if (a[ni + d[0]][nj + d[1]] == MAZE.GENERATED) {
                        a[ni][nj] = MAZE.GENERATED;
                    }
                    else {
                        s.push(new Coordinate(ni + d[0], nj + d[1]));
                        f = false;
                    }
                }
            }
            if (f) s.pop();


            Coordinate prev = s.elementAt(0);
            for(Coordinate pp : s) {
                if (a[pp.i][pp.j] == MAZE.GENERATED) {
                    a[pp.i][pp.j] = a[(pp.i + prev.i) / 2][(pp.j + prev.j) / 2] = MAZE.WAYOUT;
                    prev = pp;
                }
            }
            repaintDelay();
            if (p.compareTo(new Coordinate(n - 2, m - 2)) == 0) break;
            prev = s.elementAt(0);
            for(Coordinate pp : s) {
                if (a[pp.i][pp.j] == MAZE.WAYOUT) {
                    a[pp.i][pp.j] = a[(pp.i + prev.i) / 2][(pp.j + prev.j) / 2] = MAZE.GENERATED;
                    prev = pp;
                }
            }
        }
    }

    private void BFS() {
        // array of path
        ArrayList<ArrayList<Coordinate>> q = new ArrayList<>();
        ArrayList<Coordinate> path = new ArrayList<>();
        path.add(new Coordinate(1, 1));
        q.add(path);
        while(!q.isEmpty()) {
            // extract path from queue of paths
            path = q.remove(0);
            Coordinate p = path.get(path.size() - 1);
            // and add all paths that adjacent with it
            shuffleDirs();
            for (int[] d : dirs) {
                int ni = p.i + d[0], nj = p.j + d[1];
                if (a[ni][nj] == MAZE.PASSAGE) {
                    a[ni + d[0]][nj + d[1]] = a[ni][nj] = MAZE.GENERATED;
                    ArrayList<Coordinate> newPath = new ArrayList<>(path);
                    newPath.add(new Coordinate(ni + d[0], nj + d[1]));
                    q.add(newPath);
                }
            }
            // we need to color current path
            Coordinate prev = path.get(0);
            for (Coordinate pp : path) {
                a[pp.i][pp.j] = a[prev.i][prev.j] =
                    a[(pp.i + prev.i) / 2][(pp.j + prev.j) / 2] =
                    MAZE.WAYOUT;
                prev = pp;
            }
            repaintDelay();
            if (p.i == n - 2 && p.j == m - 2) break;
            prev = path.get(0);
            for (Coordinate pp : path) {
                a[pp.i][pp.j] = a[prev.i][prev.j] =
                     a[(pp.i + prev.i) / 2][(pp.j + prev.j) / 2] =
                     MAZE.GENERATED;
                prev = pp;
            }
        }
    }

    private void repaintDelay() {
        mp.repaintDelay();
    }

    private final Random rnd;
    private final MazePanel mp;
    private final int n, m;
    private final int[][] a;
    private int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
}