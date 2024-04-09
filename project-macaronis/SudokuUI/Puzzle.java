import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Sudoku Puzzle Code altered for Java/ UI functions
 * @author Chelsea Joe, Spring 2022, COSC 50
 *
 */
public class Puzzle {

    // Solve Function
    public static ArrayList<ArrayList<Integer>> solve(ArrayList<ArrayList<Integer>> grid) {

        if (solver(grid)){
            System.out.println("Solved Grid:\n");
            print(grid);
        } else {
            System.out.println("invalid puzzle\n");
        }

        return grid;
    }

    // Solver *solve helper* Function
    public static boolean solver(ArrayList<ArrayList<Integer>> grid) {
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(i).size(); j++) {
                if (grid.get(i).get(j) == 0) {
                    for (int k = 1; k <= 9; k++) {
                        if (validSquare(grid, i, j, k)) {
                            grid.get(i).set(j, k);
                            if (solver(grid)) {
                                return true;
                            }
                            grid.get(i).set(j, 0);
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    // check if number is valid
    public static boolean validSquare(ArrayList<ArrayList<Integer>> grid, int row, int column, int num) {

        for (int i = 0; i < 9; i++) {
            if (grid.get(i).get(column) == num) {
                return false;
            }
        }


        for (int i = 0; i < 9; i++) {
            if (grid.get(row).get(i) == num) {
                return false;
            }
        }

        int startRow = row - row % 3;
        int startCol = column - column % 3;


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid.get(i + startRow).get(j + startCol) == num) {
                    return false;
                }
            }
        }

        return true;
    }

    // create puzzle function
    public static ArrayList<ArrayList<Integer>> create(){

        // if it gets stuck on random

        ArrayList<ArrayList<Integer>> grid = new ArrayList<>();

        // initialize array with 0's
        for (int i = 0; i < 9; i++) {
            grid.add(new ArrayList<Integer>());
            for (int j = 0; j < 9; j++) {
                grid.get(i).add(0);
            }
        }

        //Creating a full sudoku
        int column = 0;
        int row = 0;


        while (column < 9) {

            // set valid numbers
            int random = (int) (Math.random() * 10);
            if (random < 10) {
                if (validSquare(grid, row, column, random)) {
                    grid.get(row).set(column, random);

                    column++;

                }
            }
        }
        // second row
        column = 0;
        row = 1;
        while (column < 7) {
            int random = (int) (Math.random() * 10);
            if (random < 10) {
                if (validSquare(grid, row, column, random)) {
                    grid.get(row).set(column, random);
                    column++;

                }
            }
        }

        if (!solver(grid))
        {
            return null;
        }

        //Initialize the parameter representing the random number of indeces that are deleted
        int random1 = (int) (Math.random() * 40) + 25;
        int count = 0;

        // turn random indeces into zeros by a random amount
        while (count < random1) {
            int random2 = (int) (Math.random() * 9);
            int random3 = (int) (Math.random() * 9);

            if (grid.get(random2).get(random3) != 0) {
                grid.get(random2).set(random3, 0);
                count++;

            }

        }
        print(grid);
        return grid;
    }

    // print puzzle function
    public static int print(ArrayList<ArrayList<Integer>> grid) {
        for (int i = 0; i < 9; i++) {

            for (int j = 0; j < 9; j++) {
                System.out.print(grid.get(i).get(j));

            }

            System.out.print("\n");
        }
        return 0;
    }

    public static void main(String[] args){
//        create();
    }

}
