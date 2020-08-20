import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;

class MAZE {
    public static final int
        WALL = 0, PASSAGE = 1, GENERATED = 2, WAYOUT = 3, FRONTIER = 4;
}

class MazeFrame extends JFrame {
    public MazeFrame(int _n, int _m, String g, String s) {
        super("Maze Solver");
        int n = _n * 2 + 1, m = _m * 2 + 1;
        int blockSize = Math.min(800 / n, 1550 / m);
        this.setSize(blockSize * m + 14, blockSize * n + 37);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        MazePanel panel = new MazePanel(blockSize, n, m);

        this.add(panel);
        this.setVisible(true);
        panel.g.generatorStart(g);
        panel.generated = true;
        panel.s.solveStart(s);
    }
}

class MazePanel extends JPanel {
    public MazePanel(int _blockSize, int _n, int _m) {
        super();
        blockSize = _blockSize; n = _n; m = _m;
        this.setBackground(Color.BLACK);
        this.setSize(blockSize * m, blockSize * n);
        a = new int[n][m];
        new AskAnimate(this);
        this.g = new Generator(this);
        this.s = new Solver(this);
    }

    public void repaintDelay() {
        repaint();
        if ((!generated && animateGeneration) || (generated && toAnimate)) {
            try {
                Thread.sleep(sleepMS);
            } catch (InterruptedException ex) {
                System.out.println("Error while waiting");
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.BLACK);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                switch (a[i][j]) {
                    case MAZE.WALL -> g.setColor(Color.BLACK);
                    case MAZE.PASSAGE -> g.setColor(Color.WHITE);
                    case MAZE.GENERATED -> g.setColor(Color.GRAY);
                    case MAZE.WAYOUT -> g.setColor(Color.ORANGE);
                    case MAZE.FRONTIER -> g.setColor(Color.RED);
                    default -> g.setColor(Color.GREEN);
                }
                g.fillRect(j * blockSize, i * blockSize, blockSize, blockSize);
            }
        }
    }

    public Generator g;
    public Solver s;

    public final int[][] a;
    public final int n, m, blockSize;

    public boolean generated = false;
    public boolean animateGeneration;
    public boolean toAnimate;
    public int sleepMS, animationTimeMs;
}