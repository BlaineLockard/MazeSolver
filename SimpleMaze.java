import java.util.ArrayList;

public class SimpleMaze extends Maze {
    // a maze with one path from start to finish
    
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

            String[][] cellData = new String[rows][colls];
            int r = 0;
            for(int i = 3; i < mazeData.size(); i++){
                System.out.println("Reading row " + r);
                for(int c = 0; c < colls; c++){
                    cellData[r][c] = mazeData.get(i);
                }
                r++;
            }
            System.out.println(cellData.toString());
            
        } catch (Exception e){
            System.out.println("Error reading maze data.");
        }
    }
    public boolean solveMaze(){
        return false;
    }
}
