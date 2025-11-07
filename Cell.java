import java.util.ArrayList;

public class Cell {
    protected boolean isStart = false;
    protected boolean isEnd = false;
    protected boolean visited = false;
    protected ArrayList<Cell> neighbors = new ArrayList<>();

    public Cell() {
    }

    public Cell(boolean isStart, boolean isEnd, Cell neighbor) {
        this.isStart = isStart;
        this.isEnd = isEnd;
        this.neighbors.add(neighbor);
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
