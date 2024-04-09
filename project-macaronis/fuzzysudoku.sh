#!/bin/bash
# fuzzysudoku.sh - fuzz testing program for sudoku.c
# 
# input: number of puzzles the user wants to be created and subsequently solved
# output: solved examples of puzzles
# 
# Usage: ./fuzzysudoku numPuzzles
# 
# Chelsea Joe, Jordan Jones, Abigail Kayser, Ryan Lee - 
# CS50 Final Project - May 31,2022

ret=0

if [ $# != 1 ]; then
	echo >&2 "Incorrect number of arguments. Usage: ./fuzzysudoku numPuzzles"
	((ret+=1))
	exit $ret
fi

numPuzzles=$1
var=1

rm -rf ./fuzzytest
mkdir fuzzytest

while [ $numPuzzles != 0 ]
do
./sudoku create > ./fuzzytest/$var.out
sleep 1
(./sudoku solve < ./fuzzytest/$var.out) > ./fuzzytest/solved$var.out 
((numPuzzles-=1))
((var+=1))
done
