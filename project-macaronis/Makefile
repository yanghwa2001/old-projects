L = ./libcs50
P = 

CC = gcc
CFLAGS = -Wall -pedantic -std=c11 -ggdb -I$L -I$P -lm
OBJS = sudoku.o userinterface.o
LLIBS = $L/libcs50-given.a

.PHONY: all clean

all: sudoku 

sudoku: sudoku.o userinterface.o $(LLIBS)
	 $(CC) $(CFLAGS) $^ -o $@

sudoku.o: $L/file.h
userinterface.o: userinterface.h

test: 
	bash testing.sh &> testing.out

clean:
	rm -f sudoku
	rm -f *- *.o
	rm -rf *.dSYM
	rm -f vgcore.*