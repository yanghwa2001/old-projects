# Makefile for 'crawler' 
#
# CS50, Spring 2022

L = ../libcs50

PROG = crawler
OBJS = crawler.o 
LIBS = ../common/common.a ../libcs50/libcs50.a

# uncomment the following to turn on verbose memory logging
#TESTING=-DMEMTEST

CFLAGS = -Wall -pedantic -std=c11 -ggdb 
CC = gcc
MAKE = make

# This are values that will be used for testing
URL = http://cs50tse.cs.dartmouth.edu/tse/letters/index.html
DIR = data
DEPTH = 2

$(PROG): $(OBJS) $(LIBS)
	$(CC) $(CFLAGS) $^ -o $@

crawler.o: crawler.c 
crawler.o: $L/memory.h $L/bag.h $L/hashtable.h

.PHONY: valgrind test clean

valgrind: $(PROG)
	valgrind ./$(PROG) $(URL) $(DIR) $(DEPTH)

test: $(PROG)
	./testing.sh &> testing.out

clean: 
	rm -rf *.dSYM  # MacOS debugger info
	rm -f *~ *.o
	rm -f $(PROG)
	rm -f core
	rm -f $(DIR)