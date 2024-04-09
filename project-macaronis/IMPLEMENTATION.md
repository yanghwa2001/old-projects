# TSE Sudoku Implementation

In a valid Sudoku grid every number from 1 to 9 must appear:

- Only once in every row
- Only once in every column
- Only once in every 3x3 squared region
- The puzzle is presented as a 9x9 valid grid with some numbers missing. A person or program solving the Sudoku puzzle should fill in the missing numbers without violating above mentioned rules. In the above given picture the numbers in red are filled by a solver.

### User interface

The querier's only interface with the user is on the command-line; querier must take 2 arguments. // To be altered

```
./sudoku [command] 
```

For example:

``` bash
$ ./sudoku create
```

### Inputs and outputs

Create Input: the only inputs are command-line parameters; see the User Interface above.

Create Output: a puzzle that has a unique solution with at least 40 missing numbers in the randomized generated puzzle printed to stdout

Solve Input: read the puzzle from stdin and print the solution to stdout (numbers only)

Solve Output: Generate any one possible solution for the given puzzle while not change any given numbers in the puzzle and print the solution to stdout.

### Functional decomposition into modules

We anticipate the following modules or functions:

 1. *main*, which parses arguments and checks for second argument (solve or create)
 2. *solve*, solves the given sudoku puzzle'
 3. *validSquare*, checks whether number is valid within row, column, and square
 4. *solver* loop through each grid space for empty spaces (0) and enter valid solution
 5. *create* generate a random valid puzze. and delete random spaces (0)
 6. *print*, print the sudoku array


### Pseudo code for logic/algorithmic flow

create:

1. create a list of numbers (1-N)
2. for each input cell 
   3. randomize the list
   4. for each number in the list
       5. check that it has not been used in the row
       6. check that it has not been used in the column
       7. identify which of the N boxes is being worked on
            8. check that it has not been used in the box
            9. insert the value

solve:

1. Loop through each line of sudoku puzzle input
2. convert each char number into an integer 
    3. assign each integer into index of array
    4. call solver function
         5. check whether number is valid within row column and square
         6. print solution to sdtout






