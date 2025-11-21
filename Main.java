import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static ArrayList<Maze> mazes = new ArrayList<>();
    public static void main(String[] args) {
        System.out.println("Loading Mazes...");
        readMazes();

        for(int i = 1; i < mazes.size()+1; i++){
            try{
                mazes.get(i-1).printMaze();
                mazes.get(i-1).saveMaze("mazes/maze" + i + ".maze");
            }
            catch(IOException e){
                System.out.println("Error saving maze " + i + ": " + e.getMessage());
            }
    }
    }

    static void readMazes(){
        for (int i = 1; i <= 5; i++){
            String filename = "mazes/maze" + i + ".maze";
            ArrayList<String> mazeData = new ArrayList<>();
            try {
                Scanner fileScanner = new Scanner(new File(filename));

                while (fileScanner.hasNextLine()){
                    String line = fileScanner.nextLine();
                    String[] lines = line.split(",");
                    for(String l : lines){
                        mazeData.add(l);
                    }
                }
                fileScanner.close();


            } catch (IOException e) {
                continue;
            }

            String type = mazeData.get(2);
            if (type.equals("s")){
                SimpleMaze sm = new SimpleMaze(mazeData);
                mazes.add(sm);
            }
            else if (type.equals("c")){
                ComplexMaze cm = new ComplexMaze(mazeData);
                mazes.add(cm);
            }
            else if (type.equals("w")){
                WeightedMaze wm = new WeightedMaze(mazeData);
                mazes.add(wm);
            }
        }
    }
}
