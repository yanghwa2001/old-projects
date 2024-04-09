/*
* sudoku.c - CS50 Final Project
*
* Chelsea Joe, Jordan Jones, Abigail Kayser, Ryan Lee - 
* CS50 Spring, May 31, 2022
*/
#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include <stdlib.h>
#include <time.h>
#include "./libcs50/file.h"
#include "userinterface.h"

// Variable for the number of rows and columns

#define N 9


int solve(char* solve);
bool validSquare(int grid[N][N], int row, int column, int num);
bool solver(int grid[N][N]);
void print(int grid[N][N]);
int create(char* difficulty);
int difficulty_helper (char *difficulty);
bool validGrid(int board[N][N]);




int main(int argc, char *argv[]) {


    //char* difficulty;

    if (argc < 2 || argc > 3) {
        fprintf(stderr, "Insufficient number of command line arguments. Correct usage is as follows: ./sudoku command [difficulty]\n");
        return 1;
    }

    char* option = argv[1];

    if (argc == 3){
        char* difficulty = argv[2];
        if (strcmp(option, "create") == 0) {
             if (strcmp(difficulty, "easy") == 0 || strcmp(difficulty, "medium") == 0 || strcmp(difficulty, "hard") == 0) {
                return create(difficulty);
             }
             else {
                fprintf(stderr, "Difficulty can only be: 'easy, medium, or hard'\n");
                return -1;
             }
        }
    }

    if (strcmp(option, "create") == 0) {
        return create("medium");

    } else if (strcmp(option, "solve") == 0) {
        //call solve
        return solve(option);
    } else {
        fprintf(stderr, "Error: usage: ./sudoku function (where function is create or solve)\n");
        return 2;
    }
    return 0;
}

/**
 * solve: converts the inputed 9x9 grid (char) into a 9x9 grid containing int
 * 
 * Input: A x A characters, representing an unsolved sudoku puzzle
 * 
 */
int solve(char* solve) {
    printf("Enter puzzle:\n");

    int grid[N][N];
    char* delim = " ";
    char* line;
    int check = 0;

    // assign each numHolder to array assuming that there are N lines
    for (int i = 0; i < N; i++) {
        if ((line = readlinep()) != NULL) {
            check = i;
            char* ptr = strtok(line, delim);

            if (ptr == NULL) {
                free(line);
                printf("Invalid puzzle length: too few rows\n");
                return 5;
            }

            while (ptr != NULL) {

                for (int j = 0; j < N; j++) {
                    if (ptr == NULL) {
                        free(line);
                        printf("Invalid input: incorrect number of columns\n");
                        return 5;
                    }
                    char* nptr; 
                    long ret;

                    ret = strtol(ptr, &nptr, 10);

                    //If part of the input is not an integer, return non 0
                    if (strcmp(nptr, "") != 0) {
                        printf("Invalid input: input must only be an integer\n");
                        free(line);
                        return 3;
                    }

                    grid[i][j] = ret;
                    ptr = strtok(NULL, delim); 
                }
            }

        } else {
            if (check != i) {
                printf("Invalid puzzle length: too few rows\n");
                return 4;
                
            }
        }
        free(line);
    }

    // Check for another line of input and if so, return non 0
    if ((line = readlinep()) != NULL) {
        if (*line != '\0') {
            printf("\nInvalid puzzle length: too many rows\n");
            free(line);
            return 5;
        }
        free(line);
    }

    if (!validGrid(grid)) {
        printf("Invalid grid, must contain numbers between 0 and 9\n");
        return 15;
    }

    //print the grid
    printf("Original grid: \n");
    print(grid);

    //Solve the grid
    printf("Solving...\n");



    bool newGrid = solver(grid);

    // Test to see if each grid does not contain duplicates
    for (int row = 0; row < N; row++) {
        for (int column = 0; column < N; column++) {
            int startRow = row - (row % 3);
            int startCol = column - (column % 3);

            for (int k = 1; k <=N; k++) {
                int count = 0;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (grid[i + startRow][j + startCol] == k) {
                            count++;
                        }
                    }
                }
                if (count > 1) {
                    printf("Invalid puzzle\n");
                    return 10;
                }
            }
        }
    }


    if (newGrid == true){
        printf("Solved Grid:\n");
        print(grid);
    } else {
        fprintf(stderr, "Invalid puzzle\n");
        return 6;
    }

    
    return 0;
}


/**
 * validGrid: checks whether all the numbers in the grid are within 1-9 range
 * 
 * Input: A x A characters, representing the inputed unsolved puzzle
 * 
 * Returns: false if any of the numbers in the board are not within 1-9. True if they are all within that range.
 */
bool validGrid(int board[N][N]) {
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N;j++) {
            if (board[i][j] < 0 || board[i][j] > 9) {
                return false;
            }
        }
    }
    return true;
}



/**
 * solver: Helper function for "solve". Takes the converted grid from "solve" and solves the puzzle
 * Helper function for "solve". Takes the converted grid from "solve" and solves the puzzle
 * 
 * Input: the multidimensional array converted by "solve."
 * 
 * Returns: true if puzzle is solveable, false if not
 *  Adapted from https://afteracademy.com/blog/sudoku-solver
 */
bool solver(int grid[N][N]) {
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            if (grid[i][j] == 0) {
                for (int k = 1; k <= N; k++) {
                    if (validSquare(grid, i, j, k)) {
                        grid[i][j] = k;
                        if (solver(grid)) {
                            return true;
                        }
                        grid[i][j] = 0;
                    }
                }
                return false;
            }
        }
    }
    return true;
}

/**
 * validSquare: Helper function for solver, which checks whether the inputed number follows the sudoku guidelines.
 * 
 * Input: multidimensional array representing the unsolved sudoku puzzle, the current row and column, and the number that 
 * validSquare is checking
 * 
 * Returns: False if the number does not make a "validsquare", else return true.
 * 
 * Adapted from https://afteracademy.com/blog/sudoku-solver
 */
bool validSquare(int grid[N][N], int row, int column, int num) {

    for (int i = 0; i < N; i++) {
        if (grid[i][column] == num) {
            return false;
        }
    }

   
    for (int i = 0; i < N; i++) {
        if (grid[row][i] == num) {
            return false;
        }
    }

    int startRow = row - (row % 3);
    int startCol = column - (column % 3);

    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            if (grid[i + startRow][j + startCol] == num) {
                return false;
            }
        }
    }

    return true;
}


/**
 * print: Prints the multidimentional array (sudoku)
 * 
 * Input: multidimensional array representing the unsolved sudoku puzzle
 * 
 * Returns nothing.
 * 
 */
void print(int grid[N][N]) {

    //srand(time(NULL));   // Initialization, should only be called once.
    for (int i = 0; i < N; i++) {

        for (int j = 0; j < N; j++) {
            // int r = rand(); 
            // int random = r%9;

            // // print a random color
            // random_color(random);
            printf("%d ",grid[i][j]);
        }

        printf("\n");
    }

    //reset();
    printf("\n");
}

/**
 * Create: function that makes an unsolved sudoku puzzle
 * 
 * Input (optional): difficulty, which is a string that changes how "difficult" the puzzle is by altering how many indeces are deleted.
 * 
 * Returns: int 0 upon successfully creating an unsolved sudoku
 * 
 */
int create(char* difficulty) {

    int grid[9][9] = {0};

    srand(time(NULL));   // Initialization, should only be called once.
    
    //Creating a full sudoku
    for (int row = 0; row < 2; row++) {
        int column = 0;

        while (column < 9) {
            int r = rand(); 
            int random = r%10;
            if (validSquare(grid, row, column, random)) {
                grid[row][column] = random;
                
                column++;            

            } else {
                break;
            }
        }
    }
    //Call solver on the grid that has two valid rows to complete the sudoku
    solver(grid);

    //Initialize the parameter representing the random number of indeces that are deleted
    // Range between 41 and 59
    int random1 = difficulty_helper (difficulty);

    int count = 0;

    // turn random indeces into zeros by a random amount 
    while (count < random1) {
        int r2 = rand(); 
        int random2 = r2%9;
        int r3 = rand(); 
        int random3 = r3%9;

        if (grid[random2][random3] != 0) {
            grid[random2][random3] = 0;
            count++;
        }

    }

    // Print final grid

    print(grid);



    return 0;
}

/**
 * difficulty_helper: Helpder function for changing the difficulty of the sudoku puzzle.
 * 
 * Input: string representing the desired difficulty. 
 * 
 * Returns: random number that the create function will use to determine how many "blanks" the puzzle will have.
 * 
 */
int difficulty_helper (char *option){
    int r1 = rand(); 
    int random1;
    if (strcmp(option, "easy") == 0){
        // 40 - 49
        random1 = r1%10 + 39;
    }
        // 49 - 58
    if (strcmp(option, "medium") == 0){
        random1 = r1%10 + 49;
    }
        // 59 - 64
    if (strcmp(option, "hard") == 0) {
        random1 = r1%7 + 58;
    }
    return random1;
}
