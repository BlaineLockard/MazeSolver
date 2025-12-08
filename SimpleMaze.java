import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.IOException;

public class SimpleMaze extends Maze {
    // a maze with one path from start to finish

    public SimpleMaze(){
        super();
    }
    
    public SimpleMaze(ArrayList<String> mazeData){
        readMaze(mazeData);
    }

    
    public void generateMaze(){

    }
    

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
            /* 
            for(int r = 0; r < rows; r++){
                for(int c = 0; c < colls; c++){
                    System.out.print(cellData[r][c] + ", ");
                }
                System.out.println();
            }
            */
            // Initialize maze array and add cells
            this.maze = new Cell[rows][colls];
            createCell(0, 0, cellData);

            
            //System.out.println(maze[0][0].degree());

        } catch (Exception e){
            System.out.println("Error reading maze data: " + e);
        }
    }
    

    public Cell createCell(int r, int c, String[][] cellData){
        if (maze[r][c] != null){ 
            //System.out.println("Cell at (" + r + ", " + c + ") already created.");
            return maze[r][c];
        }
        //System.out.println("Creating cell at (" + r + ", " + c + ")");
        boolean isStart = false;
        boolean isEnd = false;
        boolean visited = false;
        if (cellData[r][c].charAt(0) == '1'){
            isStart = true;
        }
        else if (cellData[r][c].charAt(2) == '1'){
            isEnd = true;
        }
        if (cellData[r][c].charAt(4) == '1'){
            visited = true;
        }
        Cell newCell = new Cell(isStart, isEnd, visited, r, c);
        int startIdx = 6;
        int endIdx = 9;
        maze[r][c] = newCell;
        while(endIdx <= cellData[r][c].length()){
            String neighbor = cellData[r][c].substring(startIdx, endIdx);
            int neighborRow = neighbor.charAt(0) - 48;
            int neighborColl = neighbor.charAt(2) - 48;
            //System.out.print("Neighbor "+ neighbor +" at (" + neighborRow + ", " + neighborColl + ")\n");
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


    public void solveMaze(){
        long startTime = System.currentTimeMillis();
        Cell currentCell = null;
        for(int r = 0; r < rows; r++){
            for(int c = 0; c < colls; c++){
                if(maze[r][c].isStart()){
                    currentCell = maze[r][c];
                    break;
                }
            }
        }

        traverse(currentCell);

        time = (System.currentTimeMillis() - startTime) / 1000.0;
        if (time <= 0.0){
            time = 0.001;
        }
        System.out.println("Maze solved in " + time + " seconds.");
    }


    public boolean traverse(Cell cell){
        cell.setVisit(true);
        if(cell.isEnd()){
            return true;
        }
        for(Cell neighbor : cell.neighbors()){
            if(!neighbor.isVisited()){
                if(traverse(neighbor)){
                    return true;
                }
            }
        }
        cell.setVisit(false);
        return false;
    }

    
    public void printMaze(){
        System.out.println("Maze (" + rows + " x " + colls + ") solved in " + time + " seconds: ");
        for(int r = 0; r < rows; r++){
            for(int i = 0; i < 3; i++){
                for(int c = 0; c < colls; c++){
                    if(i == 0){
                        if (maze[r][c].hasNorth()){
                            System.out.print("  |  ");
                        }
                        else{
                            System.out.print("     ");
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
                            System.out.print("\u001B[32m\u004f\u001B[0m");
                        }
                        else if(maze[r][c].isEnd()){
                            System.out.print("\u001B[31m\u004f\u001B[0m");
                        }
                        else if(maze[r][c].isVisited()){
                            System.out.print("\u001B[34m\u004f\u001B[0m");
                        }
                        else{
                            System.out.print("\u004f");
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
                        System.out.print("  |  ");
                       }
                        else{
                            System.out.print("     ");
                        }
                    }
                    
                }
                System.out.println();
            }
        }
        /* 
        for(int r = 0; r < rows; r++){
                for(int c = 0; c < colls; c++){
                    if(maze[r][c].westNeighbor != null){
                        System.out.print(maze[r][c].westNeighbor.position[0] + " " + maze[r][c].westNeighbor.position[1] + " ");
                    }
                    else{
                        System.out.print("null ");
                    }
                }
                System.out.println();
            }
        */
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
}
