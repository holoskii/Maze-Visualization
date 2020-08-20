import javax.swing.*;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class AskSize implements ActionListener {
    public AskSize(Creator _creator){
        creator = _creator;
        _this = this;
    }

    public void getDimenstions() {
        frame = new JFrame("Maze");
        frame.setLayout(null);
        frame.getContentPane().setLayout(null);
        frame.setBounds(100, 50, 360, 260);
        frame.add(new JLabel("Size of field") { {
            this.setBounds(30, 10, 120, 30);
            this.setHorizontalAlignment(SwingConstants.CENTER);
        }});
        frame.add(new JButton("Small") { {
            this.setBounds(30, 50, 120, 30);
            this.addActionListener(_this);
        }});
        frame.add(new JButton("Average") { {
            this.setBounds(30, 90, 120, 30);
            this.addActionListener(_this);
        }});
        frame.add(new JButton("Big") { {
            this.setBounds(30, 130, 120, 30);
            this.addActionListener(_this);
        }});
        frame.add(new JButton("Huge") { {
            this.setBounds(30, 170, 120, 30);
            this.addActionListener(_this);
        }});

        frame.add(new JLabel("Height") { {
            this.setBounds(200, 10, 120, 30);
            this.setHorizontalAlignment(SwingConstants.CENTER);
        }});
        frame.add(new JLabel("Width") { {
            this.setBounds(200, 90, 120, 30);
            this.setHorizontalAlignment(SwingConstants.CENTER);
        }});

        frame.add(new JLabel("or") { {
            this.setBounds(170, 10, 20, 30);
            this.setHorizontalAlignment(SwingConstants.CENTER);
        }});

        heightField = new TextField("25");
        heightField.setBounds(200, 50, 120, 30);
        frame.add(heightField);
        widthField = new TextField("14");
        widthField.setBounds(200, 130, 120, 30);
        frame.add(widthField);
        frame.add(new JButton("Ok") { {
            this.setBounds(200, 170, 120, 30);
            this.addActionListener(_this);
        }});
        frame.setVisible(true);
        try {
            while (waitingForPush) {Thread.sleep(100);}
        } catch (Exception ignored){}
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String text = ((JButton) e.getSource()).getText();
        if (text.contains("Small")) {
            creator.dimensions = new Pair<>(10, 15);
        }
        else if (text.contains("Average")) {
            creator.dimensions = new Pair<>(20, 30);
        }
        else if (text.contains("Big")) {
            creator.dimensions = new Pair<>(40, 60);
        }
        else if (text.contains("Huge")) {
            creator.dimensions = new Pair<>(120, 180);
        }
        else if (text.contains("Ok")){
            int height = 20, width = 30;
            try {
                height = Integer.parseInt(heightField.getText().replaceAll("\\D", ""));
            } catch (Exception ignored) {}
            try {
                width = Integer.parseInt(widthField.getText().replaceAll("\\D", ""));
            } catch (Exception ignored) {}

            creator.dimensions = new Pair<>(height, width);
        }
        else {
            return;
        }
        waitingForPush = false;
        frame.dispose();
    }

    private boolean waitingForPush = true;
    private AskSize _this; Creator creator; JFrame frame;
    private TextField heightField, widthField;
}

class AskGenerate implements ActionListener {
    public String getWay(){
        JFrame frame = new JFrame("Maze");
        frame.setLayout(null);
        frame.setBounds(100, 50, 400, 265);
        frame.add(new JLabel("How to generate") { {
            this.setBounds(30, 10, 180, 30);
            this.setHorizontalAlignment(SwingConstants.CENTER);
        }});
        frame.add(new JButton("Recursive Backtracker") { {
            this.setBounds(30, 50, 180, 30);
            this.addActionListener(_this);
        }});
        frame.add(new JLabel("combination of both =>") { {
            this.setBounds(30, 90, 180, 30);
            this.setHorizontalAlignment(SwingConstants.CENTER);
        }});
        frame.add(new JButton("Prim's algorithm") { {
            this.setBounds(30, 130, 180, 30);
            this.addActionListener(_this);
        }});
        frame.add(new JButton("Kruskal's algorithm") { {
            this.setBounds(30, 170, 180, 30);
            this.addActionListener(_this);
        }});
        frame.setVisible(true);

        frame.add(new JButton("75:25") { {
            this.setBounds(240, 50, 120, 30);
            this.addActionListener(_this);
        }});
        frame.add(new JButton("50:50") { {
            this.setBounds(240, 90, 120, 30);
            this.addActionListener(_this);
        }});
        frame.add(new JButton("25:75") { {
            this.setBounds(240, 130, 120, 30);
            this.addActionListener(_this);
        }});

        try {
            while (waitingForPush) Thread.sleep(100);
        } catch (Exception ignored){}

        frame.dispose();
        return resultString;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        resultString = ((JButton)e.getSource()).getText();
        waitingForPush = false;
    }

    private boolean waitingForPush = true;
    private AskGenerate _this = this;
    private String resultString;
}

class AskSolve implements ActionListener {
    public String getWay(){
        JFrame frame = new JFrame("Maze");
        frame.setLayout(null);
        frame.setBounds(100, 50, 225, 210);
        frame.add(new JLabel("How to solve") { {
            this.setBounds(30, 10, 160, 30);
            this.setHorizontalAlignment(SwingConstants.CENTER);
        }});
        frame.add(new JButton("Depth First Search") { {
            this.setBounds(30, 50, 160, 30);
            this.addActionListener(_this);
        }});
        frame.add(new JButton("Breadth First Search") { {
            this.setBounds(30, 90, 160, 30);
            this.addActionListener(_this);
        }});
        frame.add(new JButton("Don't Solve") { {
            this.setBounds(30, 130, 160, 30);
            this.addActionListener(_this);
        }});
        frame.setVisible(true);

        try {
            while (waitingForPush) Thread.sleep(100);
        } catch (Exception ignored){}

        frame.dispose();
        return resultString;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        resultString = ((JButton)e.getSource()).getText();
        waitingForPush = false;
    }

    private boolean waitingForPush = true;
    private AskSolve _this = this;
    private String resultString;
}

class AskAnimate implements ActionListener {
    public AskAnimate(MazePanel mp) {
        JFrame frame = new JFrame();
        frame.setLayout(null);
        frame.setBounds(100, 50, 200, 295);
        frame.add(new JLabel("Animation speed") { {
            this.setBounds(30, 10, 130, 30);
            this.setHorizontalAlignment(SwingConstants.CENTER);
        }});
        frame.add(new JButton("Fast") { {
            this.setBounds(30, 50, 130, 30);
            this.addActionListener(_this);
        }});
        frame.add(new JButton("Average") { {
            this.setBounds(30, 90, 130, 30);
            this.addActionListener(_this);
        }});
        frame.add(new JButton("Slow") { {
            this.setBounds(30, 130, 130, 30);
            this.addActionListener(_this);
        }});
        frame.add(new JButton("No animation") { {
            this.setBounds(30, 170, 130, 30);
            this.addActionListener(_this);
        }});
        ch = new JCheckBox("Skip generation");
        ch.setBounds(30, 210, 130, 30);
        frame.add(ch);

        frame.setVisible(true);
        try {
            while(waitingForPush) Thread.sleep(100);
        } catch (Exception ignored) {}

        frame.dispose();

        mp.animateGeneration = !animateGeneration;
        mp.toAnimate = time != 0;
        mp.animationTimeMs = time;
        mp.sleepMS = mp.animationTimeMs / (mp.n * mp.m);
        if (mp.sleepMS == 0) mp.sleepMS++;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String text = ((JButton) e.getSource()).getText();
        if (text.contains("Fast")) {
            time = 10000;
        }
        else if (text.contains("Average")) {
            time = 20000;
        }
        else if (text.contains("Slow")) {
            time = 60000;
        }
        else {
            time = 0;
        }
        animateGeneration = ch.isSelected();
        waitingForPush = false;
    }

    private int time;
    private boolean animateGeneration;
    private AskAnimate _this = this;
    private boolean waitingForPush = true;
    private JCheckBox ch;
}