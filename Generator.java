import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.TreeSet;

class Generator {
    public Generator (MazePanel _mp) {
        mp = _mp; a = mp.a;
        n = mp.n; m = mp.m;
        rnd = new Random();
        rnd.setSeed(rnd.nextLong());
    }

    public void generatorStart(String s) {
        for(int i = 0; i < n; i++)
            for(int j = 0; j < m; j++)
                a[i][j] = MAZE.WALL;

        switch (s) {
            case "Recursive Backtracker" -> recursiveBacktracker(new Coordinate(-1, -1));
            case "75:25" -> growingTree(75);
            case "50:50" -> growingTree(50);
            case "25:75" -> growingTree(25);
            case "Prim's algorithm" -> prims();
            case "Kruskal's algorithm" -> kruskal();
        }

        for(int i = 0; i < n; i++)
            for(int j = 0; j < m; j++)
                if(a[i][j] != MAZE.WALL)
                    a[i][j] = MAZE.PASSAGE;

        repaintDelay();
    }

    private void growingTree(int fr) {
        ArrayList<Coordinate> cells = new ArrayList<>();
        cells.add(getRandomPoint());
        while (cells.size() != 0) {
            int idx;

            if (rnd.nextInt(100) < fr)
                idx = cells.size() - 1;
            else
                idx = rnd.nextInt(cells.size());

            Coordinate p1 = cells.get(idx);

            ArrayList<Coordinate> pp = getAdjWalls(p1);
            if (pp.size() != 0) {
                Coordinate p2 = pp.get(0);
                a[p1.i][p1.j] = a[(p1.i + p2.i) / 2][(p1.j + p2.j) / 2] = a[p2.i][p2.j] = MAZE.PASSAGE;
                cells.add(p2);
            }
            else {
                cells.remove(idx);
            }

            // needed to paint frontier in red
            cells.removeIf(np -> (getAdjWalls(np).size() == 0));
            cells.forEach(np -> a[np.i][np .j] = MAZE.FRONTIER);
            repaintDelay();
            cells.forEach(np -> a[np.i][np .j] = MAZE.PASSAGE);
        }
    }

    private void prims() {
        // create frontier, put a random point into it
        ArrayList<Coordinate> frontier = new ArrayList<>();

        frontier.add(getRandomPoint());
        while(frontier.size() != 0) {
            // pick a point
            int idx = rnd.nextInt(frontier.size());
            Coordinate p = frontier.get(idx);
            frontier.remove(idx);

            // mark it as visited and add edge
            a[p.i][p.j] = MAZE.PASSAGE;
            ArrayList<Coordinate> pos = new ArrayList<>();

            int[][] dirs = {{0, 2}, {2, 0}, {0, -2}, {-2, 0}};
            for(int[] dir : dirs) {
                int ni = p.i + dir[0], nj = p.j + dir[1];
                if (ni >= 0 && ni < n && nj >= 0 && nj < m && a[ni][nj] != MAZE.WALL)
                    pos.add(new Coordinate(ni, nj));
            }

            if (pos.size() != 0) {
                Coordinate parent = pos.get(rnd.nextInt(pos.size()));
                a[(parent.i + p.i) / 2][(parent.j + p.j) / 2] = MAZE.PASSAGE;
            }

            // add other points
            frontier.addAll(getAdjWalls(p));
            frontier.removeIf(np -> (a[np.i][np .j] != MAZE.WALL));
            frontier.forEach(np -> a[np.i][np .j] = MAZE.FRONTIER);
            repaintDelay();
            frontier.forEach(np -> a[np.i][np .j] = MAZE.WALL);
        }
    }

    private void recursiveBacktracker(Coordinate p) {
        if (p.i == -1 || p.j == -1)
            p = getRandomPoint();
        repaintDelay();

        ArrayList<Coordinate> walls = getAdjWalls(p);
        if (walls.size() == 0) return;
        Coordinate np = walls.get(rnd.nextInt(walls.size()));

        a[p.i][p.j] = a[np.i][np.j] = a[(p.i + np.i) / 2][(p.j + np.j) / 2] = MAZE.PASSAGE;
        recursiveBacktracker(np);
        a[np.i][np.j] = a[(p.i + np.i) / 2][(p.j + np.j) / 2] = MAZE.GENERATED;
        if (walls.size() > 1) recursiveBacktracker(p);
    }

    private void kruskal() {
        int oldSleep = mp.sleepMS;
        mp.sleepMS /= 2;
        ArrayList<Pair<Coordinate, Coordinate>> edges = new ArrayList<>();
        ArrayList<TreeSet<Coordinate>> trees = new ArrayList<>();
        for(int i = 1; i < n; i += 2) {
            for(int j = 1; j < m; j += 2) {
                if (i + 2 < n) edges.add(new Pair<>(new Coordinate(i, j), new Coordinate(i + 2, j)));
                if (j + 2 < m) edges.add(new Pair<>(new Coordinate(i, j), new Coordinate(i, j + 2)));
                TreeSet<Coordinate> ts = new TreeSet<>();
                ts.add(new Coordinate(i, j));
                trees.add(ts);
            }
        }
        Collections.shuffle(edges);

        for(Pair<Coordinate, Coordinate> edge : edges) {
            int idxTree = 0, idxTree1 = -1, idxTree2 = -1;
            Coordinate p1 = edge.first, p2 = edge.second;
            for(TreeSet<Coordinate> v : trees) {
                if (v.contains(p1)) idxTree1 = idxTree;
                if (v.contains(p2)) idxTree2 = idxTree;
                if (idxTree1 != -1 && idxTree2 != -1)
                    break;
                idxTree++;
            }
            if (idxTree1 != idxTree2) {
                trees.get(idxTree1).addAll(trees.get(idxTree2));
                trees.remove(idxTree2);
                a[p1.i][p1.j] = a[p2.i][p2.j] =
                        a[(p1.i + p2.i) / 2][(p1.j + p2.j) / 2] = MAZE.PASSAGE;
                repaintDelay();
            }
        }
        mp.sleepMS = oldSleep;
    }

    private void repaintDelay() {
        mp.repaintDelay();
    }

    private ArrayList<Coordinate> getAdjWalls(Coordinate p) {
        ArrayList<Coordinate> v = new ArrayList<>();
        int[][] dirs = {{0, 2}, {2, 0}, {0, -2}, {-2, 0}};
        for(int[] dir : dirs) {
            int ni = p.i + dir[0], nj = p.j + dir[1];
            if (ni >= 0 && ni < n && nj >= 0 && nj < m && a[ni][nj] == MAZE.WALL)
                v.add(new Coordinate(ni, nj));
        }
        Collections.shuffle(v);
        return v;
    }

    private Coordinate getRandomPoint() {
        int i = rnd.nextInt(n / 2 - 1) * 2 + 3;
        int j = rnd.nextInt(m / 2 - 1) * 2 + 3;
        return new Coordinate(i, j);
    }

    private final MazePanel mp;
    private final Random rnd;
    private final int n, m;
    private final int[][] a;
}