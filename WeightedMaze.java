import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class WeightedMaze extends ComplexMaze {
    public class WeightedCell extends Maze.Cell {
        private double weight;
        protected  WeightedCell northNeighbor = null;
        protected WeightedCell southNeighbor = null;
        protected WeightedCell eastNeighbor = null;
        protected WeightedCell westNeighbor = null;

        public WeightedCell() {
            super();
            this.weight = 1; 
        }
        public WeightedCell(boolean start, boolean end, boolean isVisited, int r, int c) {
            super(start, end, isVisited, r, c);
            this.weight = 1;
        }
        public WeightedCell(boolean start, boolean end, boolean isVisited, int r, int c, double weight) {
            super(start, end, isVisited, r, c);
            this.weight = weight;
        }
        
        public double getWeight() {return weight;}
        public void setWeight(double weight) {this.weight = weight;}
    }

    // a maze with weighted paths
    private WeightedCell[][] maze;
    private double cost;

    public WeightedMaze(){
        super();
    }

    public WeightedMaze(ArrayList<String> mazeData){
        this.readMaze(mazeData);
    }

    @Override
    public void readMaze(ArrayList<String> mazeData){
        try{
            this.rows = Integer.parseInt(mazeData.get(0));
            this.colls = Integer.parseInt(mazeData.get(1));
            this.mazeType = mazeData.get(2).charAt(0);
            this.time = Double.parseDouble(mazeData.get(3));
            String[][] cellData = new String[rows][colls];
            int dataIndex = 4;
            // Read cell data into 2D array
            for (int r = 0; r < rows; r++){ 
                for (int c = 0; c < colls; c++){
                    cellData[r][c] = mazeData.get(dataIndex);
                    dataIndex++;
                }
                System.out.println();
            }

            // Initialize maze array and add cells
            this.maze = new WeightedCell[rows][colls];
            createCell(0, 0, cellData);

        } catch (Exception e){
            System.out.println("Error reading maze data: " + e);
        }
    }

    
    @Override
    public WeightedCell createCell(int r, int c, String[][] cellData){
        if (maze[r][c] != null){ 
            return maze[r][c];
        }
        boolean isStart = false;
        boolean isEnd = false;
        boolean visited = false;
        double weight = Double.parseDouble(cellData[r][c].substring(6, 10));

        if (cellData[r][c].charAt(0) == '1'){
            isStart = true;
        }
        else if (cellData[r][c].charAt(2) == '1'){
            isEnd = true;
        }
        if (cellData[r][c].charAt(4) == '1'){
            visited = true;
        }
        WeightedCell newCell = new WeightedCell(isStart, isEnd, visited, r, c, weight);
        System.out.print(cellData[r][c] + "\n");
        int startIdx = 10;
        int endIdx = 13;
        maze[r][c] = newCell;
        while(endIdx <= cellData[r][c].length()){
            String neighbor = cellData[r][c].substring(startIdx, endIdx);
            int neighborRow = neighbor.charAt(0) - 48;
            int neighborColl = neighbor.charAt(2) - 48;

            if(neighborRow == r-1 && neighborColl == c){ // North
                newCell.northNeighbor = createCell(neighborRow, neighborColl, cellData);
            }
            else if(neighborRow == r+1 && neighborColl == c){ // South
                newCell.southNeighbor = createCell(neighborRow, neighborColl, cellData);
            }
            else if(neighborRow == r && neighborColl == c+1){ // East
                newCell.eastNeighbor = createCell(neighborRow, neighborColl, cellData);
            }
            else if(neighborRow == r && neighborColl == c-1){ // West
                newCell.westNeighbor = createCell(neighborRow, neighborColl, cellData);
            }
            else{
                throw new IllegalArgumentException("Invalid neighbor coordinates: " + neighbor);
            }
            startIdx += 4;
            endIdx += 4;
        }
        return newCell;
    }

    @Override
    public void generateMaze(){

    }

    public void solveMaze(){
        long startTime = System.currentTimeMillis();
        WeightedCell startCell = null;
        WeightedCell endCell = null;

        // Find start and end cells
        for(int r = 0; r < rows; r++){
            for(int c = 0; c < colls; c++){
                if(maze[r][c].isStart()){
                    startCell = maze[r][c];
                }
                else if(maze[r][c].isEnd()){
                    endCell = maze[r][c];
                }
            }
        }

        if(startCell == null || endCell == null){
            System.out.println("Start or end cell not found in the maze.");
            return;
        }

        dijkstraSolve(startCell);

        time = (System.currentTimeMillis() - startTime) / 1000.0;
    }

    private boolean dijkstraSolve(WeightedCell cellAt){
        cellAt.setVisited(true);
        cost += cellAt.getWeight();
        double shortestNeighborWeight = Double.MAX_VALUE;
        WeightedCell nextCell = null;

        for(int i = 0; i < cellAt.degree(); i++){
            WeightedCell neighbor = (WeightedMaze.WeightedCell)cellAt.neighbors().get(i);
            if(neighbor.isEnd()){
                cost += neighbor.getWeight();
                return true;
            }
            if(!neighbor.isVisited() && neighbor.getWeight() < shortestNeighborWeight){
                shortestNeighborWeight = neighbor.getWeight();
                nextCell = neighbor;
            }
        }
        if(nextCell != null){
            if(dijkstraSolve(nextCell)){
                return true;
            }
        }
        cellAt.setVisited(false);
        cost -= cellAt.getWeight();
        return false;
    }

    public void printMaze(){
        for(int r = 0; r < rows; r++){
            for(int i = 0; i < 3; i++){
                for(int c = 0; c < colls; c++){
                    if(i == 0){
                        if (maze[r][c].hasNorth()){
                            System.out.print("   |   ");
                        }
                        else{
                            System.out.print("       ");
                        }
                    }
                    else if(i == 1){
                        if(maze[r][c].hasWest()){
                            System.out.print("--");
                        }
                        else{
                            System.out.print("  ");
                        }

                        if(maze[r][c].isStart()){
                            System.out.print("\u001B[32m" + maze[r][c].weight + "\u001B[0m");
                        }
                        else if(maze[r][c].isEnd()){
                            System.out.print("\u001B[31m" + maze[r][c].weight + "\u001B[0m");
                        }
                        else if(maze[r][c].isVisited()){
                            System.out.print("\u001B[34m" + maze[r][c].weight + "\u001B[0m");
                        }
                        else{
                            System.out.print(maze[r][c].weight);
                        }

                        if(maze[r][c].hasEast()){
                            System.out.print("--");
                        }
                        else{
                            System.out.print("  ");
                        }
                    }
                    else if (i == 2){
                       if(maze[r][c].hasSouth()){
                        System.out.print("   |   ");
                       }
                        else{
                            System.out.print("       ");
                        }
                    }
                    
                }
                System.out.println();
            }
        }
    }

    public void saveMaze(String fileName) throws IOException{
        PrintWriter outFile = new PrintWriter(fileName);

        outFile.println(this.rows + "," + this.colls + "," + this.mazeType + "," + this.time);
        for(int r = 0; r < this.rows; r++){
            for(int c = 0; c < this.colls; c++){
                if(maze[r][c].isStart()){
                    outFile.print("1 0");
                }
                else if (maze[r][c].isEnd()){
                    outFile.print("0 1");
                }
                else{
                    outFile.print("0 0");
                }

                if(maze[r][c].isVisited()){
                    outFile.print(" 1");
                }
                else{
                    outFile.print(" 0");
                }
                outFile.printf(" %.1f", maze[r][c].getWeight());

                if(maze[r][c].hasNorth()){
                    outFile.print(" " + maze[r][c].northNeighbor.position[0] + "-" + maze[r][c].northNeighbor.position[1]);
                }
                if(maze[r][c].hasWest()){
                    outFile.print(" " + maze[r][c].westNeighbor.position[0] + "-" + maze[r][c].westNeighbor.position[1]);
                }
                if(maze[r][c].hasEast()){
                    outFile.print(" " + maze[r][c].eastNeighbor.position[0] + "-" + maze[r][c].eastNeighbor.position[1]);
                }
                if(maze[r][c].hasSouth()){
                    outFile.print(" " + maze[r][c].southNeighbor.position[0] + "-" + maze[r][c].southNeighbor.position[1]);
                }

                if(c != colls-1)
                    outFile.print(",");
            }
            outFile.print("\n");
        }

        outFile.close();
    }

    public double getCost() {
        return cost;
    }
}
 