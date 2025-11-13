import java.util.ArrayList;

public class WeightedMaze extends Maze {
    // a maze with weighted paths

    public void generateMaze(){

    }
    public void readMaze(ArrayList<String> mazeData){

    }
    public boolean solveMaze(){
        return false;
    }


    public class WeightedCell extends Cell {
        private int weight;

        public WeightedCell() {
            super();
            this.weight = 1; 
        }
        public WeightedCell(boolean start, boolean end, WeightedCell neighbor) {
            super(start, end);
            this.weight = 1;
        }
        public WeightedCell(boolean start, boolean end, WeightedCell neighbor, int weight) {
            super(start, end);
            this.weight = weight;
        }

        public int getWeight() {return weight;}
        public void setWeight(int weight) {this.weight = weight;}
    }
}
