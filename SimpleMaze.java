import java.util.ArrayList;

public class SimpleMaze extends Maze {
    // a maze with one path from start to finish
    
    public SimpleMaze(ArrayList<String> mazeData){
        readMaze(mazeData);
        printMaze();
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

            
            //System.out.println(maze[0][0].neighbors);

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
        if (cellData[r][c].charAt(0) == '1'){
            isStart = true;
        }
        else if (cellData[r][c].charAt(2) == '1'){
            isEnd = true;
        }
        Cell newCell = new Cell(isStart, isEnd, r, c);
        int startIdx = 4;
        int endIdx = 7;
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

    public boolean solveMaze(){
        long startTime = System.currentTimeMillis();


        long endTime = System.currentTimeMillis();
        time = (startTime - endTime)/1000.0;
        return true;
    }

    public void printMaze(){
        for(int r = 0; r < rows; r++){
            for(int i = 0; i < 3; i++){
                for(int c = 0; c < colls; c++){
                    if(i == 0){
                        if (maze[r][c].northNeighbor != null){
                            System.out.print("  |  ");
                        }
                        else{
                            System.out.print("     ");
                        }
                    }
                    else if(i == 1){
                        if(maze[r][c].westNeighbor != null){
                            System.out.print("--");
                        }
                        else{
                            System.out.print("  ");
                        }

                        if(maze[r][c].isStart()){
                            System.out.print("\u001B[32m" + maze[r][c].degree() + "\u001B[0m");
                        }
                        else if(maze[r][c].isEnd()){
                            System.out.print("\u001B[31m" + maze[r][c].degree() + "\u001B[0m");
                        }
                        else if(maze[r][c].isVisited()){
                            System.out.print("\033[0;1m" + maze[r][c].degree() + "\u001B[0m");
                        }
                        else{
                            System.out.print(maze[r][c].degree());
                        }

                        if(maze[r][c].eastNeighbor != null){
                            System.out.print("--");
                        }
                        else{
                            System.out.print("  ");
                        }
                    }
                    else if (i == 2){
                       if(maze[r][c].southNeighbor != null){
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
}
