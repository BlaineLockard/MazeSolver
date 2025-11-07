public class WeightedCell extends Cell {
    private int weight;

    public WeightedCell() {
        super();
        this.weight = 1; 
    }
    public WeightedCell(boolean start, boolean end, WeightedCell neighbor) {
        super(start, end, neighbor);
        this.weight = 1;
    }
    public WeightedCell(boolean start, boolean end, WeightedCell neighbor, int weight) {
        super(start, end, neighbor);
        this.weight = weight;
    }

    public int getWeight() {return weight;}
    public void setWeight(int weight) {this.weight = weight;}
}