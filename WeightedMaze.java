import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class WeightedMaze extends ComplexMaze {
    public class WeightedCell extends Maze.Cell {
        private double weight;
        private double cost = 0.0;
        private boolean partOfPath = false;

        public WeightedCell() {
            super();
            this.weight = 1; 
        }
        public WeightedCell(boolean start, boolean end, boolean isVisited, int r, int c) {
            super(start, end, isVisited, r, c);
            this.weight = 1;
        }
        public WeightedCell(boolean start, boolean end, boolean partOfPath, int r, int c, double weight) {
            super(start, end, false, r, c);
            this.weight = weight;
            this.partOfPath = partOfPath;
        }
        public WeightedCell(boolean start, boolean end, boolean isVisited, int r, int c, double weight, double cost,  boolean partOfPath) {
            super(start, end, isVisited, r, c);
            this.weight = weight;
            this.cost = cost;
            this.partOfPath = partOfPath;
        }

        @Override
        public WeightedCell getNorthNeighbor() {return (WeightedCell) northNeighbor;}
        @Override
        public WeightedCell getSouthNeighbor() {return (WeightedCell) southNeighbor;}
        @Override
        public WeightedCell getEastNeighbor() {return (WeightedCell) eastNeighbor;}
        @Override
        public WeightedCell getWestNeighbor() {return (WeightedCell) westNeighbor;}

        public double getWeight() {return weight;}
        public void setWeight(int weight) {this.weight = weight;}
        public double getCost() {return cost;}
        public void setCost(double cost) {this.cost = cost;}
        public boolean isPartOfPath() {return partOfPath;}
        public void setPartOfPath(boolean partOfPath) {this.partOfPath = partOfPath;}
    }

    // a maze with weighted paths
    WeightedCell[][] maze;
    private double cost = Double.POSITIVE_INFINITY;

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
            this.cost = Double.parseDouble(mazeData.get(3));
            this.time = Double.parseDouble(mazeData.get(4));
            String[][] cellData = new String[rows][colls];
            int dataIndex = 5;
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
        // Check if cell already exists
        if (maze[r][c] != null){ 
            return maze[r][c];
        }
        // read cell data
        boolean isStart = false;
        boolean isEnd = false;
        boolean isVisited = false;
        boolean partOfPath = false;
        double weight = Double.parseDouble(cellData[r][c].substring(6, 10));

        if (cellData[r][c].charAt(0) == '1'){
            isStart = true;
        }
        else if (cellData[r][c].charAt(2) == '1'){
            isEnd = true;
        }
        if (cellData[r][c].charAt(4) == '1'){
            partOfPath = true;
        }

        WeightedCell newCell = new WeightedCell(isStart, isEnd, isVisited, r, c, weight, 0, partOfPath);

        // Add neighbors
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

    @Override
    public void solveMaze(){
        // start timer
        long startTime = System.currentTimeMillis();
        WeightedCell startCell = null;

        // Find start cell
        for(int r = 0; r < rows; r++){
            for(int c = 0; c < colls; c++){
                if(maze[r][c].isStart()){
                    startCell = maze[r][c];
                }
            }
        }

        // call solving method
        dijkstraSolve(startCell);

        time = (System.currentTimeMillis() - startTime) / 1000.0;
    }

    private boolean dijkstraSolve(WeightedCell startCell){
        // set starting cell cost to 0
        // all other cells set to infinity
        ArrayList<WeightedCell> unvisited = new ArrayList<>();
        WeightedCell currentCell = startCell;
        startCell.setCost(0);
        startCell.setVisit(true);
        startCell.setPartOfPath(false);
        for(int r = 0; r < rows; r++){
            for(int c = 0; c < colls; c++){
                if(maze[r][c] != startCell){
                    maze[r][c].setCost(Double.POSITIVE_INFINITY);
                    unvisited.add(maze[r][c]);
                }
            }
        }

        // while there are unvisited cells
        while(!unvisited.isEmpty()){
            // for each neighbor of current cell
            for(int i = 0; i < currentCell.neighbors().size(); i++){
                WeightedCell neighbor = (WeightedCell) currentCell.neighbors().get(i);
                if(!neighbor.isVisited()){
                    double tentativeCost = currentCell.getCost() + neighbor.getWeight();
                    if(tentativeCost < neighbor.getCost()){
                        neighbor.setCost(tentativeCost);
                    }
                }
            }
            // mark current cell as visited
            currentCell.setVisit(true);
            // remove current cell from unvisited
            unvisited.remove(currentCell);
            // set next current cell to unvisited cell with lowest cost
            double lowestCost = Double.POSITIVE_INFINITY;
            WeightedCell nextCell = null;
            for(WeightedCell cell : unvisited){
                if(cell.getCost() < lowestCost){
                    lowestCost = cell.getCost();
                    nextCell = cell;
                }
            }
            if (nextCell == null){
                break; // no reachable unvisited cells remain
            }
            currentCell = nextCell;

            // check if current cell is end cell
            if(currentCell.isEnd()){
                this.cost = currentCell.getCost();
                // retrace path to mark it
                WeightedCell pathCell = currentCell;
                while(!pathCell.isStart()){
                    pathCell.setPartOfPath(true);
                    // find neighbor with lowest cost
                    double lowestNeighborCost = Double.POSITIVE_INFINITY;
                    WeightedCell nextPathCell = null;
                    for(int i = 0; i < pathCell.neighbors().size(); i++){
                        WeightedCell neighbor = (WeightedCell) pathCell.neighbors().get(i);
                        if(neighbor.getCost() < lowestNeighborCost){
                            lowestNeighborCost = neighbor.getCost();
                            nextPathCell = neighbor;
                        }
                    }
                    pathCell = nextPathCell;
                }
                return true;
            }

        }

        return false;
    }

    public void printMaze(){
        System.out.println("Maze (" + rows + " x " + colls + ") solved in " + time + " seconds with cost " + cost + ": ");
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
                        else if(maze[r][c].isPartOfPath()){
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

        outFile.println(this.rows + "," + this.colls + "," + this.mazeType + ","  + this.cost + "," + this.time);
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

                if(maze[r][c].isPartOfPath()){
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
 