#!/bin/bash
#
# testing.sh - performs sudoku testing
#
# usage: testing.sh
#
# input: none
# output: to terminal
#
# Chelsea Joe, Jordan Jones, Abigail Kayser, Ryan Lee - 
# CS50 Final Project - May 31,2022

make clean
make

#check invalid inputs
echo invalid number of parameters check
./sudoku
echo -e '\n'

./sudoku this is too many input arguments
echo -e '\n'

echo invalid parameter check
./sudoku not_a_function
echo -e '\n'

./sudoku create not_a_difficulty
echo -e '\n'

echo invalid tables check
#two adjacent numbers
./sudoku solve < ./testfiles/unsolvable
echo -e '\n'
#too many of the same number in the puzzle itself (there's 10 1's)
./sudoku solve < ./testfiles/unsolvable1
echo -e '\n'
#unable to place a number in a spot because of interference
./sudoku solve < ./testfiles/unsolvable2
echo -e '\n'
./sudoku solve < ./testfiles/incorrect_lines
echo -e '\n'
./sudoku solve < ./testfiles/incorrect_lines2
echo -e '\n'
./sudoku solve < ./testfiles/invalid1
echo -e '\n'
./sudoku solve < ./testfiles/invalid2
echo -e '\n'
./sudoku solve < ./testfiles/invalid3
echo -e '\n'

echo testing create
./sudoku create
echo -e '\n'

./sudoku create easy
echo -e '\n'

./sudoku create medium
echo -e '\n'

./sudoku create hard
echo -e '\n'

echo testing solve on six different created puzzles
#tests 1&2 easy, tests 3&4 medium, tests 5&6 hard
./sudoku solve < ./testfiles/test1
echo -e '\n'
./sudoku solve < ./testfiles/test2
echo -e '\n'
./sudoku solve < ./testfiles/test3
echo -e '\n'
./sudoku solve < ./testfiles/test4
echo -e '\n'
./sudoku solve < ./testfiles/test5
echo -e '\n'
./sudoku solve < ./testfiles/test6
echo -e '\n'

echo random testing!!!
echo easy!
./sudoku create easy | ./sudoku solve
echo -e '\n'
echo medium!
./sudoku create medium | ./sudoku solve
echo -e '\n'
echo hard!
./sudoku create hard | ./sudoku solve
echo -e '\n'

echo end of testing