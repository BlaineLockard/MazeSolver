import java.util.ArrayList;

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


    public void printMaze() {

    }

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
        protected ArrayList<Cell> neighbors = new ArrayList<>();

        public Cell() {
        }

        public Cell(boolean isStart, boolean isEnd) {
            this.isStart = isStart;
            this.isEnd = isEnd;
        }

        public void visit() {this.visited = true;}

        public Cell getNeighbor(int index) {return neighbors.get(index);}
        public void addNeighbor(Cell neighbor) {this.neighbors.add(neighbor);}
        public int degree() {return neighbors.size();}
        public void clear() {this.neighbors.clear();}
        public void removeNeighbor(int index) {this.neighbors.remove(index);}

        public boolean isStart() {return isStart;}
        public boolean isEnd() {return isEnd;}
        public boolean isVisited() {return visited;}

        public void setStart(boolean isStart) {this.isStart = isStart;}
        public void setEnd(boolean isEnd) {this.isEnd = isEnd;}
        public void setVisited(boolean visited) {this.visited = visited;}
    }
}

