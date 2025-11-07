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
    public Maze(ArrayList<String> mazeData){
        readMaze(mazeData);
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

}
