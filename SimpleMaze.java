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
                }
                System.out.println();
            }

            // Initialize maze array and add cells
            this.maze = new Cell[rows][colls];
            createCell(0, 0, cellData);

            /*  
            for(int r = 0; r < rows; r++){
                for(int c = 0; c < colls; c++){
                    System.out.print(maze[r][c].degree() + " ");
                }
                System.out.println();
            }
            //System.out.println(maze[0][0].neighbors);
            */
            
        } catch (Exception e){
            System.out.println("Error reading maze data: " + e);
        }
    }
    
    public Cell createCell(int r, int c, String[][] cellData){
        if (maze[r][c] != null){ 
            System.out.println("Cell at (" + r + ", " + c + ") already created.");
            return maze[r][c];
        }
        System.out.println("Creating cell at (" + r + ", " + c + ")");
        boolean isStart;
        boolean isEnd;
        if (cellData[r][c].charAt(0) == '1'){
            isStart = true;
            isEnd = false;
        }
        else if (cellData[r][c].charAt(2) == '1'){
            isEnd = true;
            isStart = false;
        }
        else{
            isStart = false;
            isEnd = false;
        }
        Cell newCell = new Cell(isStart, isEnd);
        int startIdx = 4;
        int endIdx = 7;
        maze[r][c] = newCell;
        while(endIdx <= cellData[r][c].length()){
            String neighbor = cellData[r][c].substring(startIdx, endIdx);
            int neighborRow = neighbor.charAt(0) - 48;
            int neighborColl = neighbor.charAt(2) - 48;
            System.out.print("Neighbor "+ neighbor +" at (" + neighborRow + ", " + neighborColl + ")\n");
            newCell.addNeighbor(createCell(neighborRow, neighborColl, cellData));
            startIdx += 4;
            endIdx += 4;
        }
        return newCell;
    }

    public boolean solveMaze(){
        return false;
    }

    public void printMaze(){
        for(int r = 0; r < rows; r++){
            for(int c = 0; c < colls; c++){
                if(maze[r][c].isStart()){
                    System.out.print("S ");
                }
                else if(maze[r][c].isEnd){
                    System.out.print("E ");
                }
                else{
                    System.out.print(". ");
                }

            }
            System.out.println();
        }
    }
}
