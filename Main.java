import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static ArrayList<Maze> mazes = new ArrayList<>();

    public static void main(String[] args) {
        Scanner si = new Scanner(System.in);
        String input = "";
        System.out.println("Loading Mazes...");
        readMazes();
        System.out.println("Mazes Loaded.");

        while(!input.equals("9")){
            System.out.println("What would you like to do? ");
            System.out.println("1. solve a maze");
            System.out.println("2. print a maze");
            System.out.println("3. Generate a new maze");
            System.out.println("9. exit");
            input = si.nextLine();

            if(input.equals("1")){
                System.out.println("Which maze would you like to solve? (1-" + mazes.size() + ")");
                int mazeNum = Integer.parseInt(si.nextLine()) - 1;
                mazes.get(mazeNum).solveMaze();
            }
            else if(input.equals("2")){
                System.out.println("Which maze would you like to print? (1-" + mazes.size() + ")");
                int mazeNum = Integer.parseInt(si.nextLine()) - 1;
                mazes.get(mazeNum).printMaze();
            }
            else if(input.equals("3")){
                System.out.println("What size should the maze be? (e.g. 10 for a 10x10 maze)");
                int size = Integer.parseInt(si.nextLine());
                WeightedMaze newMaze = new WeightedMaze(size, size);

                mazes.add(newMaze);
                System.out.println("Your new Maze: ");
                newMaze.printMaze();
                
            }
        }
        for(int i = 0; i < mazes.size(); i++){
            try {
                mazes.get(i).saveMaze("mazes/maze" + (i + 1) + ".maze");
            } catch (IOException e) {
                System.out.println("Error saving maze " + (i + 1));
            }
        }


        si.close();
    }
    

    static void readMazes(){
        for (int i = 1; i <= 10; i++){
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
