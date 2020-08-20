class Pair<F, S> {
    public F first;
    public S second;
    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }
}

class Coordinate implements Comparable<Coordinate>{
    public int i, j;
    public Coordinate(int _i, int _j) {
        i = _i; j = _j;
    }
    @Override
    public int compareTo(Coordinate o) {
        if (this.i == o.i && this.j == o.j)
            return 0;
        if (this.i == o.i)
            return this.j - o.j;
        return this.i - o.i;
    }
}

class Main {
    public static void main(String[] argv) {
        new Creator();
    }
}

// asks for parameters and launches maze
class Creator {
    public Pair<Integer, Integer> dimensions;
    public String generationString, solvingString;
    public Creator() {
        new AskSize(this).getDimenstions();
        generationString = new AskGenerate().getWay();
        solvingString = new AskSolve().getWay();
        new MazeFrame(dimensions.first, dimensions.second, generationString, solvingString);
    }
}

