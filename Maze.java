import java.util.ArrayList;
import java.io.IOException;

public abstract class Maze {
    protected double time;
    protected int rows;
    protected int colls;
    protected char mazeType;
    protected Cell[][] maze;


    public Maze(){
        this.rows = 5;
        this.colls = 5;
        generateMaze();
    }
    public Maze(int rows, int colls, char mazeType){
        this.rows = rows;
        this.colls = colls;
        this.mazeType = mazeType;
        generateMaze();
    }


    public abstract void printMaze();
    public abstract void saveMaze(String fileName) throws IOException;

    public abstract void readMaze(ArrayList<String> mazeData);
    public abstract void generateMaze();
    public abstract boolean solveMaze();

    public double getTime() {return time;}
    public int getWidth() {return rows;}
    public int getHeight() {return colls;}
    public char getType() {return mazeType;}
    public Cell[][] getMaze() {return maze;}

    

    
    public class Cell {
        protected boolean isStart = false;
        protected boolean isEnd = false;
        protected boolean visited = false;
        protected Cell northNeighbor = null;
        protected Cell southNeighbor = null;
        protected Cell eastNeighbor = null;
        protected Cell westNeighbor = null;
        protected int[] position = new int[2];

        public Cell() {
        }

        public Cell(boolean isStart, boolean isEnd, int row, int col) {
            this.isStart = isStart;
            this.isEnd = isEnd;
            this.position[0] = row;
            this.position[1] = col;
        }

        public void visit() {this.visited = true;}

        public void clear(){
            northNeighbor = null;
            southNeighbor = null;
            eastNeighbor = null;
            westNeighbor = null;
        }
        public ArrayList<Cell> neighbors() {
            ArrayList<Cell> neighbors = new ArrayList<>();
            if (northNeighbor != null) neighbors.add(northNeighbor);
            if (southNeighbor != null) neighbors.add(southNeighbor);
            if (eastNeighbor != null) neighbors.add(eastNeighbor);
            if (westNeighbor != null) neighbors.add(westNeighbor);
            return neighbors;
        }
        public int degree() {
            int degree = 0;
            if (northNeighbor != null) degree++;
            if (southNeighbor != null) degree++;
            if (eastNeighbor != null) degree++;
            if (westNeighbor != null) degree++;
            return degree;
        }
        public Cell getNorthNeighbor() {return northNeighbor;}
        public Cell getSouthNeighbor() {return southNeighbor;}
        public Cell getEastNeighbor() {return eastNeighbor;}
        public Cell getWestNeighbor() {return westNeighbor;}
        public boolean hasNorth(){return northNeighbor != null;}
        public boolean hasEast(){return eastNeighbor != null;}
        public boolean hasWest(){return westNeighbor != null;}
        public boolean hasSouth(){return southNeighbor != null;}

        public boolean isStart() {return isStart;}
        public boolean isEnd() {return isEnd;}
        public boolean isVisited() {return visited;}

        public void setStart(boolean isStart) {this.isStart = isStart;}
        public void setEnd(boolean isEnd) {this.isEnd = isEnd;}
        public void setVisited(boolean visited) {this.visited = visited;}
    }
}

