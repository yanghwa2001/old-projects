# Final Sudoku Design Spec

the **Sudoku Solver** checks each row, column, and box, and inserts a number (1-9) checking whether each is valid before finalizing the insert. 

the **Sudoku Creator** parses arguments and generates random numbers within a given sudoku puzzle

A Design Spec should contain several sections:


### User interface

The sudoku solver and creator's only interface with the user is on the command-line; it must always have one argument.

```
sudoku [command]
```

For example:

``` bash
$ ./sudoku create
```


### Inputs and outputs

Input: the only inputs are command-line parameters; see the User Interface above.

create Output: print out an unsolved sudoku puzzle (a mix of zeros and numbers)
solve Output: print out a solved sudoku puzzle in stdout


### Functional decomposition into modules

We anticipate the following modules or functions for solve:

 1. *main*, which parses arguments and initializes other modules
 2. various helper functions

 We anticipate the following modules or functions for create:

 1. *main*, which parses arguments and initializes other modules
 2. various helper functions

And some helper modules that provide data structures:

 1. *array* containg 9 numbers within a single sudoku row
 2. *array* containing 9 sudoku rows, each containing 9 numbers


### Pseudocode

Create pseudocode:
1. Generate a random number between 1-9 inclusive for two of the arrays while checking if each row/column is valid, then solve the incomplete sudoku
2. Generate a random number key 1- 9 inclusive and delete value associated with index -- the number of indices deleted are also random, but enough to generate a solvable sudoku
3. Print out unsolved puzzle to stdout

Example:
```
0 0 0 0 0 6 7 0 0 
0 4 0 0 0 0 0 2 0 
0 0 0 0 0 0 3 4 6 
1 0 0 0 5 0 0 0 0 
0 0 7 0 9 0 0 1 0 
0 9 0 2 1 0 0 0 7 
0 3 5 0 0 0 0 0 4 
8 6 1 0 0 4 0 0 0 
9 0 0 0 3 2 8 0 1 
```

Solver pseudocode:
1. create array of 9 arrays
2. check for input for spaces as delimeter
3. add numbers/zeros until next space is encountered into each array (9 values for each array)
4. backtrack by inserting number 1 - 9 inclusive into each empty spot and checking each row and column if the number is valid (check by array keys which are also labeled--

```
1 2 3 4 5 6 7 8 9
7 8 9 1 2 3 4 5 6
4 5 6 7 8 9 1 2 3
5 1 2 6 4 7 9 3 8
9 3 4 8 1 5 2 6 7
8 6 7 9 3 2 5 1 4
2 4 8 3 7 1 6 9 5
3 9 5 2 6 4 8 7 1
6 7 1 5 9 8 3 4 2
```
5. print solved puzzle into stdout


### Testing Plan

0. Test the program with various forms of incorrect command-line arguments to ensure that its command-line parsing, and validation of those parameters, works correctly.

1. Test whether ./sudoku solve can validate invalid sudoku tables (tables with invalid number of lines, unsolvable sudoku tables)

2. Test the sudoku with the 'create' function 

3. Test the sudoku with the 'solve' function with three different puzzles

4. Test the sudoku with a number of fuzzy tests to ensure functionality of both sudoku 'create' and sudoku 'solve'


### Extra Credit
Colors! - C
____________
userinterface.h - module that includes functions that change colors of printing (commented out since it affected testing.sh)
- functions are called in print method in sudoku.c


User Interface! - Java
______________________
SUDOKUGUI - directory that holds files include GUI's written Java with modes 
- basic (BasicSudokuGUI.java)
- rainbow (FancySudokuGUI.java) ------
- mario
- elitist
- professor

IMAGES - includes .jpg images of necessary modes

(Import SUDOKUGUI and IMAGES directories into IntelliJIDEA since VSCode cannot reference Images Directory)

Diificulty - C
______________
difficulty_helper (in sudoku.c) - helper function that allows the user to change the difficulty of the puzzle in when creating one.
It changes the number of blanks that would be in the created sudoku puzzle.
