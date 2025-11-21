import java.util.ArrayList;

public class WeightedMaze extends ComplexMaze {
    // a maze with weighted paths
    public WeightedMaze(ArrayList<String> mazeData){
        super(mazeData);
    }

    public void generateMaze(){

    }
    public void readMaze(ArrayList<String> mazeData){

    }
    public boolean solveMaze(){
        return false;
    }


    public class WeightedCell extends Cell {
        private double weight;

        public WeightedCell() {
            super();
            this.weight = 1; 
        }
        public WeightedCell(boolean start, boolean end, int r, int c) {
            super(start, end, r, c);
            this.weight = 1;
        }
        public WeightedCell(boolean start, boolean end, int r, int c, double weight) {
            super(start, end, r, c);
            this.weight = weight;
        }

        public double getWeight() {return weight;}
        public void setWeight(int weight) {this.weight = weight;}
    }
}
