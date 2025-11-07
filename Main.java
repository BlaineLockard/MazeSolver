import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static ArrayList<Maze> mazes = new ArrayList<>();
    public static void main(String[] args) {
        System.out.println("Loading Mazes...");
        readMazes();

    }

    static void readMazes(){
        for (int i = 1; i < 5; i++){
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
                System.out.println("Could not open file: " + filename);
                continue;
            }

            String type = mazeData.get(2);
            if (type.equals("s")){
                SimpleMaze sm = new SimpleMaze(mazeData);
                mazes.add(sm);
            }
            else if (type.equals("c")){
                ComplexMaze cm = new ComplexMaze();
                cm.readMaze(mazeData);
                mazes.add(cm);
            }
            else if (type.equals("w")){
                WeightedMaze wm = new WeightedMaze();
                wm.readMaze(mazeData);
                mazes.add(wm);
            }
        }
    }
}
