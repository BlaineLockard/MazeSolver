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
                WeightedMaze newMaze;
                System.out.println("Enter Row amount: ");
                int rows = Integer.parseInt(si.nextLine());
                System.out.println("Enter Column amount: ");
                int cols = Integer.parseInt(si.nextLine());
                System.out.println("Enter Start Row (Enter nothing to use default): ");
                String startRowInput = si.nextLine();
                System.out.println("Enter Start Column (Enter nothing to use default): ");
                String startColInput = si.nextLine();
                System.out.println("Enter End Row (Enter nothing to use default): ");
                String endRowInput = si.nextLine();
                System.out.println("Enter End Column (Enter nothing to use default): ");
                String endColInput = si.nextLine();

                int startRow = startRowInput.isEmpty() ? 0 : Integer.parseInt(startRowInput) - 1;
                int startCol = startColInput.isEmpty() ? 0 : Integer.parseInt(startColInput) - 1;
                int endRow = endRowInput.isEmpty() ? rows - 1 : Integer.parseInt(endRowInput) - 1;
                int endCol = endColInput.isEmpty() ? cols - 1 : Integer.parseInt(endColInput) - 1;

                newMaze = new WeightedMaze(rows, cols, startCol, startRow, endCol, endRow);
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
