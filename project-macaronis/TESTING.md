# Sudoku Testing Plan

## Test 1
   Test the program with various forms of incorrect command-line arguments to ensure that its command-line parsing, and validation of those parameters, works correctly.
```
f004n4j@plank:~/cs50/labs/project-macaronis$ myvalgrind ./sudoku
==8479== Memcheck, a memory error detector
==8479== Copyright (C) 2002-2017, and GNU GPL'd, by Julian Seward et al.
==8479== Using Valgrind-3.13.0 and LibVEX; rerun with -h for copyright info
==8479== Command: ./sudoku
==8479== 
Insufficient number of command line arguments. Correct usage is as follows: ./sudoku command [difficulty]
==8479== 
==8479== HEAP SUMMARY:
==8479==     in use at exit: 0 bytes in 0 blocks
==8479==   total heap usage: 0 allocs, 0 frees, 0 bytes allocated
==8479== 
==8479== All heap blocks were freed -- no leaks are possible
==8479== 
==8479== For counts of detected and suppressed errors, rerun with: -v
==8479== ERROR SUMMARY: 0 errors from 0 contexts (suppressed: 0 from 0)

f004n4j@plank:~/cs50/labs/project-macaronis$ myvalgrind ./sudoku this is too many inputs
==9924== Memcheck, a memory error detector
==9924== Copyright (C) 2002-2017, and GNU GPL'd, by Julian Seward et al.
==9924== Using Valgrind-3.13.0 and LibVEX; rerun with -h for copyright info
==9924== Command: ./sudoku this is too many inputs
==9924== 
Insufficient number of command line arguments. Correct usage is as follows: ./sudoku command [difficulty]
==9924== 
==9924== HEAP SUMMARY:
==9924==     in use at exit: 0 bytes in 0 blocks
==9924==   total heap usage: 0 allocs, 0 frees, 0 bytes allocated
==9924== 
==9924== All heap blocks were freed -- no leaks are possible
==9924== 
==9924== For counts of detected and suppressed errors, rerun with: -v
==9924== ERROR SUMMARY: 0 errors from 0 contexts (suppressed: 0 from 0)

f004n4j@plank:~/cs50/labs/project-macaronis$ myvalgrind ./sudoku not_a_function
==11316== Memcheck, a memory error detector
==11316== Copyright (C) 2002-2017, and GNU GPL'd, by Julian Seward et al.
==11316== Using Valgrind-3.13.0 and LibVEX; rerun with -h for copyright info
==11316== Command: ./sudoku not_a_function
==11316== 
Error: usage: ./sudoku function (where function is create or solve)
==11316== 
==11316== HEAP SUMMARY:
==11316==     in use at exit: 0 bytes in 0 blocks
==11316==   total heap usage: 0 allocs, 0 frees, 0 bytes allocated
==11316== 
==11316== All heap blocks were freed -- no leaks are possible
==11316== 
==11316== For counts of detected and suppressed errors, rerun with: -v
==11316== ERROR SUMMARY: 0 errors from 0 contexts (suppressed: 0 from 0)

f004n4j@plank:~/cs50/labs/project-macaronis$ myvalgrind ./sudoku create not_a_difficulty
==12043== Memcheck, a memory error detector
==12043== Copyright (C) 2002-2017, and GNU GPL'd, by Julian Seward et al.
==12043== Using Valgrind-3.13.0 and LibVEX; rerun with -h for copyright info
==12043== Command: ./sudoku create not_a_difficulty
==12043== 
Difficulty can only be: 'easy, medium, or hard'
==12043== 
==12043== HEAP SUMMARY:
==12043==     in use at exit: 0 bytes in 0 blocks
==12043==   total heap usage: 0 allocs, 0 frees, 0 bytes allocated
==12043== 
==12043== All heap blocks were freed -- no leaks are possible
==12043== 
==12043== For counts of detected and suppressed errors, rerun with: -v
==12043== ERROR SUMMARY: 0 errors from 0 contexts (suppressed: 0 from 0)
```

## Test 2
   Test whether ./sudoku solve can validate invalid sudoku tables (tables with invalid number of lines, unsolvable sudoku tables)

```
f004q8d@plank:~/cs50/project-macaronis$ myvalgrind ./sudoku solve < ./testfiles/unsolvable 
==3843== Memcheck, a memory error detector
==3843== Copyright (C) 2002-2017, and GNU GPL'd, by Julian Seward et al.
==3843== Using Valgrind-3.13.0 and LibVEX; rerun with -h for copyright info
==3843== Command: ./sudoku solve
==3843== 
Enter puzzle:
Original grid: 
6 6 0 0 0 3 7 8 0 
2 3 5 7 8 0 1 0 6 
0 0 0 0 4 0 0 3 5 
1 0 0 4 0 0 0 9 8 
0 4 0 2 9 8 0 0 7 
0 9 7 0 6 0 4 5 0 
0 1 2 0 0 5 0 0 4 
0 0 0 9 0 0 0 7 3 
9 7 0 0 3 0 0 0 0 
Solving...
Invalid puzzle
==3843== 
==3843== HEAP SUMMARY:
==3843==     in use at exit: 0 bytes in 0 blocks
==3843==   total heap usage: 12 allocs, 12 frees, 10,026 bytes allocated
==3843== 
==3843== All heap blocks were freed -- no leaks are possible
==3843== 
==3843== For counts of detected and suppressed errors, rerun with: -v
==3843== ERROR SUMMARY: 0 errors from 0 contexts (suppressed: 0 from 0)

f004n4j@plank:~/cs50/labs/project-macaronis$ myvalgrind ./sudoku solve < testfiles/unsolvable2
==19665== Memcheck, a memory error detector
==19665== Copyright (C) 2002-2017, and GNU GPL'd, by Julian Seward et al.
==19665== Using Valgrind-3.13.0 and LibVEX; rerun with -h for copyright info
==19665== Command: ./sudoku solve
==19665== 
Enter puzzle:
Original grid: 
0 0 0 1 0 6 7 8 0 
0 1 5 0 0 9 3 0 0 
6 0 9 3 0 0 4 2 0 
1 2 3 0 6 5 8 0 7 
4 0 7 8 9 0 0 6 0 
0 0 0 7 0 0 0 0 1 
3 4 0 5 1 0 0 7 8 
0 0 2 0 3 0 6 1 4 
0 0 1 6 0 0 5 0 0 

Solving...
Invalid puzzle
==19665== 
==19665== HEAP SUMMARY:
==19665==     in use at exit: 0 bytes in 0 blocks
==19665==   total heap usage: 12 allocs, 12 frees, 10,026 bytes allocated
==19665== 
==19665== All heap blocks were freed -- no leaks are possible
==19665== 
==19665== For counts of detected and suppressed errors, rerun with: -v
==19665== ERROR SUMMARY: 0 errors from 0 contexts (suppressed: 0 from 0)

f004q8d@plank:~/cs50/project-macaronis$ myvalgrind ./sudoku solve < ./testfiles/incorrect_lines
==4015== Memcheck, a memory error detector
==4015== Copyright (C) 2002-2017, and GNU GPL'd, by Julian Seward et al.
==4015== Using Valgrind-3.13.0 and LibVEX; rerun with -h for copyright info
==4015== Command: ./sudoku solve
==4015== 
Enter puzzle:
Invalid puzzle length: too few lines
==4015== 
==4015== HEAP SUMMARY:
==4015==     in use at exit: 0 bytes in 0 blocks
==4015==   total heap usage: 11 allocs, 11 frees, 9,945 bytes allocated
==4015== 
==4015== All heap blocks were freed -- no leaks are possible
==4015== 
==4015== For counts of detected and suppressed errors, rerun with: -v
==4015== ERROR SUMMARY: 0 errors from 0 contexts (suppressed: 0 from 0)

f004q8d@plank:~/cs50/project-macaronis$ myvalgrind ./sudoku solve < ./testfiles/incorrect_lines2
==4099== Memcheck, a memory error detector
==4099== Copyright (C) 2002-2017, and GNU GPL'd, by Julian Seward et al.
==4099== Using Valgrind-3.13.0 and LibVEX; rerun with -h for copyright info
==4099== Command: ./sudoku solve
==4099== 
Enter puzzle:

Invalid puzzle length: too many rows
==4099== 
==4099== HEAP SUMMARY:
==4099==     in use at exit: 0 bytes in 0 blocks
==4099==   total heap usage: 12 allocs, 12 frees, 10,026 bytes allocated
==4099== 
==4099== All heap blocks were freed -- no leaks are possible
==4099== 
==4099== For counts of detected and suppressed errors, rerun with: -v
==4099== ERROR SUMMARY: 0 errors from 0 contexts (suppressed: 0 from 0)

f004n4j@plank:~/cs50/labs/project-macaronis$ myvalgrind ./sudoku solve < ./testfiles/invalid1==32622== Memcheck, a memory error detector
==32622== Copyright (C) 2002-2017, and GNU GPL'd, by Julian Seward et al.
==32622== Using Valgrind-3.13.0 and LibVEX; rerun with -h for copyright info
==32622== Command: ./sudoku solve
==32622== 
Enter puzzle:
Invalid input: incorrect number of columns
==32622== 
==32622== HEAP SUMMARY:
==32622==     in use at exit: 0 bytes in 0 blocks
==32622==   total heap usage: 5 allocs, 5 frees, 9,459 bytes allocated
==32622== 
==32622== All heap blocks were freed -- no leaks are possible
==32622== 
==32622== For counts of detected and suppressed errors, rerun with: -v
==32622== ERROR SUMMARY: 0 errors from 0 contexts (suppressed: 0 from 0)

f004n4j@plank:~/cs50/labs/project-macaronis$ myvalgrind ./sudoku solve < ./testfiles/invalid2
==32694== Memcheck, a memory error detector
==32694== Copyright (C) 2002-2017, and GNU GPL'd, by Julian Seward et al.
==32694== Using Valgrind-3.13.0 and LibVEX; rerun with -h for copyright info
==32694== Command: ./sudoku solve
==32694== 
Enter puzzle:
Invalid input: incorrect number of columns
==32694== 
==32694== HEAP SUMMARY:
==32694==     in use at exit: 0 bytes in 0 blocks
==32694==   total heap usage: 7 allocs, 7 frees, 9,621 bytes allocated
==32694== 
==32694== All heap blocks were freed -- no leaks are possible
==32694== 
==32694== For counts of detected and suppressed errors, rerun with: -v
==32694== ERROR SUMMARY: 0 errors from 0 contexts (suppressed: 0 from 0)

f004n4j@plank:~/cs50/labs/project-macaronis$ myvalgrind ./sudoku solve < ./testfiles/invalid3
==32893== Memcheck, a memory error detector
==32893== Copyright (C) 2002-2017, and GNU GPL'd, by Julian Seward et al.
==32893== Using Valgrind-3.13.0 and LibVEX; rerun with -h for copyright info
==32893== Command: ./sudoku solve
==32893== 
Enter puzzle:
Invalid grid, must contain numbers between 0 and 9
==32893== 
==32893== HEAP SUMMARY:
==32893==     in use at exit: 0 bytes in 0 blocks
==32893==   total heap usage: 12 allocs, 12 frees, 10,026 bytes allocated
==32893== 
==32893== All heap blocks were freed -- no leaks are possible
==32893== 
==32893== For counts of detected and suppressed errors, rerun with: -v
==32893== ERROR SUMMARY: 0 errors from 0 contexts (suppressed: 0 from 0)
```

## Test 3
   Test the sudoku with the 'create' function
```
f004n4j@plank:~/cs50/labs/project-macaronis$ myvalgrind ./sudoku create
==26571== Memcheck, a memory error detector
==26571== Copyright (C) 2002-2017, and GNU GPL'd, by Julian Seward et al.
==26571== Using Valgrind-3.13.0 and LibVEX; rerun with -h for copyright info
==26571== Command: ./sudoku create
==26571== 
0 0 0 0 0 0 4 0 9 
2 9 0 0 0 0 1 7 8 
0 0 0 1 8 0 0 3 0 
1 0 0 0 6 0 8 9 7 
3 5 0 0 0 1 0 2 4 
0 7 0 0 2 0 3 0 0 
0 3 0 6 0 8 0 4 0 
7 0 0 0 0 0 5 0 6 
0 0 0 0 0 0 7 0 0 

random: 50
==26571== 
==26571== HEAP SUMMARY:
==26571==     in use at exit: 0 bytes in 0 blocks
==26571==   total heap usage: 1 allocs, 1 frees, 1,024 bytes allocated
==26571== 
==26571== All heap blocks were freed -- no leaks are possible
==26571== 
==26571== For counts of detected and suppressed errors, rerun with: -v
==26571== ERROR SUMMARY: 0 errors from 0 contexts (suppressed: 0 from 0)

f004q8d@plank:~/cs50/project-macaronis$ ./sudoku create easy
8 2 3 0 4 0 0 0 9 
1 5 7 8 0 9 0 0 4 
4 0 9 2 0 7 0 5 0 
0 0 4 0 5 6 0 9 7 
3 0 5 9 0 0 4 2 0 
6 0 8 4 0 2 3 0 0 
0 0 1 6 0 0 0 0 2 
7 4 2 5 0 1 0 0 0 
9 0 6 7 2 0 0 0 1 
f004q8d@plank:~/cs50/project-macaronis$ ./sudoku create medium
0 5 8 0 0 0 6 7 0 
0 0 0 0 0 0 0 0 0 
0 0 9 0 5 8 0 0 4 
0 0 0 0 6 5 0 9 0 
5 4 6 0 0 7 0 0 3 
8 9 0 0 1 3 4 6 0 
2 0 0 0 0 1 0 8 6 
0 0 4 0 0 2 0 3 0 
0 8 0 5 3 0 7 0 0 

f004n4j@plank:~/cs50/labs/project-macaronis$ ./sudoku create hard
8 0 0 0 4 0 6 0 0 
0 0 5 0 0 9 0 0 0 
0 0 0 0 0 0 0 0 0 
0 3 0 0 5 0 0 0 0 
0 0 6 0 0 0 0 0 0 
7 0 0 0 0 0 4 0 0 
2 0 0 0 3 0 9 0 0 
0 0 3 0 0 0 0 0 2 
0 0 4 7 6 0 0 3 0 
```

## Test 4
   Test the sudoku with the 'solve' function with three different puzzles

``` 
f004q8d@plank:~/cs50/project-macaronis$ myvalgrind ./sudoku solve < ./testfiles/test1
==28899== Memcheck, a memory error detector
==28899== Copyright (C) 2002-2017, and GNU GPL'd, by Julian Seward et al.
==28899== Using Valgrind-3.13.0 and LibVEX; rerun with -h for copyright info
==28899== Command: ./sudoku solve
==28899== 
Enter puzzle:
Original grid: 
0 1 2 3 4 0 6 8 9 
4 0 0 0 8 9 0 2 0 
0 0 0 0 2 0 0 4 5 
0 2 0 4 5 6 7 9 0 
5 4 6 7 9 8 2 1 0 
8 0 0 0 1 3 4 5 6 
0 0 0 0 3 0 0 0 4 
3 5 1 9 0 4 0 0 2 
0 0 4 0 0 2 0 3 0 

Solving...
Solved Grid:
7 1 2 3 4 5 6 8 9 
4 3 5 6 8 9 1 2 7 
6 8 9 1 2 7 3 4 5 
1 2 3 4 5 6 7 9 8 
5 4 6 7 9 8 2 1 3 
8 9 7 2 1 3 4 5 6 
2 6 8 5 3 1 9 7 4 
3 5 1 9 7 4 8 6 2 
9 7 4 8 6 2 5 3 1 

==28899== 
==28899== HEAP SUMMARY:
==28899==     in use at exit: 0 bytes in 0 blocks
==28899==   total heap usage: 12 allocs, 12 frees, 10,026 bytes allocated
==28899== 
==28899== All heap blocks were freed -- no leaks are possible
==28899== 
==28899== For counts of detected and suppressed errors, rerun with: -v
==28899== ERROR SUMMARY: 0 errors from 0 contexts (suppressed: 0 from 0) 

f004q8d@plank:~/cs50/project-macaronis$ ./sudoku solve < ./testfiles/test2
Enter puzzle:
Original grid: 
0 1 0 2 0 0 7 0 0 
0 0 6 7 0 0 1 0 0 
7 8 9 1 3 0 0 4 0 
1 0 0 3 0 0 6 0 8 
3 5 0 6 9 0 0 0 2 
6 9 8 4 1 0 3 0 0 
4 0 0 0 2 1 0 0 0 
8 3 0 0 7 4 0 0 0 
0 7 0 0 0 3 8 2 0 

Solving...
Solved Grid:
5 1 3 2 4 6 7 8 9 
2 4 6 7 8 9 1 3 5 
7 8 9 1 3 5 2 4 6 
1 2 4 3 5 7 6 9 8 
3 5 7 6 9 8 4 1 2 
6 9 8 4 1 2 3 5 7 
4 6 5 8 2 1 9 7 3 
8 3 2 9 7 4 5 6 1 
9 7 1 5 6 3 8 2 4 

f004q8d@plank:~/cs50/project-macaronis$ myvalgrind ./sudoku solve < ./testfiles/test3
==32534== Memcheck, a memory error detector
==32534== Copyright (C) 2002-2017, and GNU GPL'd, by Julian Seward et al.
==32534== Using Valgrind-3.13.0 and LibVEX; rerun with -h for copyright info
==32534== Command: ./sudoku solve
==32534== 
Enter puzzle:
Original grid: 
7 0 0 0 1 3 0 0 9 
0 9 0 4 5 0 2 0 8 
0 0 0 0 0 9 1 0 0 
0 0 3 0 4 0 6 0 7 
0 0 6 1 0 0 0 8 0 
8 0 0 0 0 2 5 0 0 
0 1 2 0 7 0 9 0 3 
6 3 0 0 2 1 8 0 0 
0 8 0 0 0 5 0 2 1 

Solving...
Solved Grid:
7 6 8 2 1 3 4 5 9 
3 9 1 4 5 6 2 7 8 
2 4 5 7 8 9 1 3 6 
1 2 3 5 4 8 6 9 7 
4 5 6 1 9 7 3 8 2 
8 7 9 3 6 2 5 1 4 
5 1 2 8 7 4 9 6 3 
6 3 7 9 2 1 8 4 5 
9 8 4 6 3 5 7 2 1 

==32534== 
==32534== HEAP SUMMARY:
==32534==     in use at exit: 0 bytes in 0 blocks
==32534==   total heap usage: 12 allocs, 12 frees, 10,026 bytes allocated
==32534== 
==32534== All heap blocks were freed -- no leaks are possible
==32534== 
==32534== For counts of detected and suppressed errors, rerun with: -v
==32534== ERROR SUMMARY: 0 errors from 0 contexts (suppressed: 0 from 0)

f004n4j@plank:~/cs50/labs/project-macaronis$  ./sudoku solve < ./testfiles/test4
Enter puzzle:
Original grid: 
0 8 4 9 0 5 0 0 0 
0 0 0 0 0 0 3 0 8 
0 0 0 0 2 0 0 7 0 
1 0 3 0 4 0 0 0 7 
0 0 0 0 0 0 0 0 3 
8 0 9 2 0 0 5 6 0 
0 0 1 6 7 0 0 0 0 
0 4 7 8 0 0 0 0 2 
0 9 0 0 0 0 0 4 1 

Solving...
Solved Grid:
7 8 4 9 3 5 1 2 6 
2 1 5 4 6 7 3 9 8 
9 3 6 1 2 8 4 7 5 
1 6 3 5 4 9 2 8 7 
4 5 2 7 8 6 9 1 3 
8 7 9 2 1 3 5 6 4 
3 2 1 6 7 4 8 5 9 
5 4 7 8 9 1 6 3 2 
6 9 8 3 5 2 7 4 1 

f004n4j@plank:~/cs50/labs/project-macaronis$  ./sudoku solve < ./testfiles/test5
Enter puzzle:
Original grid: 
0 0 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 0 0 
0 0 0 5 0 9 0 0 0 
0 0 0 3 5 0 8 9 7 
0 0 0 0 0 0 0 0 4 
0 0 0 0 0 2 0 0 0 
5 0 0 8 0 0 9 4 6 
6 0 0 0 2 5 0 3 0 
9 0 1 0 4 3 0 8 0 

Solving...
Solved Grid:
1 2 3 4 6 8 5 7 9 
4 5 9 2 1 7 3 6 8 
7 6 8 5 3 9 4 1 2 
2 1 6 3 5 4 8 9 7 
3 9 5 7 8 6 1 2 4 
8 4 7 1 9 2 6 5 3 
5 3 2 8 7 1 9 4 6 
6 8 4 9 2 5 7 3 1 
9 7 1 6 4 3 2 8 5 

f004n4j@plank:~/cs50/labs/project-macaronis$  ./sudoku solve < ./testfiles/test6
Enter puzzle:
Original grid: 
0 2 3 0 0 0 7 0 0 
0 0 5 0 0 0 0 0 0 
0 0 0 0 0 0 0 0 0 
0 0 0 0 5 7 0 0 8 
0 0 0 0 0 0 0 2 0 
0 0 0 0 0 2 0 0 0 
4 0 0 5 0 0 0 6 2 
0 0 2 9 0 0 0 0 0 
0 0 0 0 2 4 0 0 0 

Solving...
Solved Grid:
1 2 3 4 6 5 7 8 9 
6 4 5 7 8 9 2 1 3 
7 8 9 2 1 3 4 5 6 
2 1 4 3 5 7 6 9 8 
3 5 7 6 9 8 1 2 4 
8 9 6 1 4 2 3 7 5 
4 3 8 5 7 1 9 6 2 
5 7 2 9 3 6 8 4 1 
9 6 1 8 2 4 5 3 7 
```
## Testing outputs will vary for the random testing, one selection below
```
f004n4j@plank:~/cs50/labs/project-macaronis$ ./sudoku create easy | ./sudoku solve
Enter puzzle:
Original grid: 
0 2 3 4 0 7 6 8 0 
0 7 0 6 1 0 2 3 5 
0 6 9 0 0 8 1 4 0 
0 1 0 3 0 5 0 0 0 
3 0 7 0 0 0 0 0 0 
0 0 6 0 2 0 0 5 0 
6 0 0 5 8 0 9 0 0 
7 0 0 0 4 0 5 0 3 
9 4 5 1 0 0 8 0 0 

Solving...
Solved Grid:
1 2 3 4 5 7 6 8 9 
4 7 8 6 1 9 2 3 5 
5 6 9 2 3 8 1 4 7 
2 1 4 3 6 5 7 9 8 
3 5 7 8 9 1 4 2 6 
8 9 6 7 2 4 3 5 1 
6 3 1 5 8 2 9 7 4 
7 8 2 9 4 6 5 1 3 
9 4 5 1 7 3 8 6 2 

f004n4j@plank:~/cs50/labs/project-macaronis$ ./sudoku create medium | ./sudoku solve
Enter puzzle:
Original grid: 
0 2 3 0 0 6 0 0 9 
4 9 0 0 0 0 2 0 0 
0 0 0 0 8 0 0 0 0 
0 0 4 0 0 8 0 9 0 
0 5 6 0 9 1 0 0 8 
7 0 0 6 0 0 3 0 0 
0 0 1 8 0 0 9 7 2 
0 0 0 0 0 3 0 0 0 
0 0 0 1 6 0 0 0 0 

Solving...
Solved Grid:
1 2 3 4 5 6 7 8 9 
4 9 8 3 1 7 2 5 6 
5 6 7 2 8 9 1 3 4 
2 1 4 5 3 8 6 9 7 
3 5 6 7 9 1 4 2 8 
7 8 9 6 2 4 3 1 5 
6 3 1 8 4 5 9 7 2 
8 4 2 9 7 3 5 6 1 
9 7 5 1 6 2 8 4 3 

f004n4j@plank:~/cs50/labs/project-macaronis$ ./sudoku create hard | ./sudoku solve
Enter puzzle:
Original grid: 
0 6 0 0 0 3 0 0 0 
0 0 3 0 0 0 0 0 0 
0 0 0 4 0 0 0 0 3 
0 0 4 0 6 0 0 9 7 
0 0 6 0 9 7 0 1 0 
0 0 0 0 1 0 0 0 5 
0 0 0 6 0 2 0 0 0 
0 4 0 0 0 0 0 0 0 
0 0 8 0 3 1 0 0 0 

Solving...
Solved Grid:
1 6 2 5 7 3 4 8 9 
4 7 3 1 8 9 5 2 6 
8 9 5 4 2 6 1 7 3 
3 1 4 2 6 5 8 9 7 
5 2 6 8 9 7 3 1 4 
7 8 9 3 1 4 2 6 5 
9 3 1 6 4 2 7 5 8 
2 4 7 9 5 8 6 3 1 
6 5 8 7 3 1 9 4 2 
```

## Side Notes

For functionality purposes, the text is outputed to stdout in normal font. However, we have implemented a random color generator that will give any number a random color within the grid. This functionality has been commented out for the sake of testing and can be reimplemented by code in the print() function in sudoku.c

Fuzzy testing has also been implemented to test the functionality of sudoku 'create' and 'solve' in tandem. A user provided number of puzzles are created and outputted to a file, where the fuzzy test will then solve each puzzle and produce another file containing the output.
