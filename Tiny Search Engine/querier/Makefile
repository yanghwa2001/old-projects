#Makefile for Querier

L = ../libcs50
LL = ../common

PROG1 = querier

OBJS1 = querier.o 

LIB = $(LL)/common.a $(L)/libcs50.a

FLAGS = 
CFLAGS = -Wall -pedantic -std=c11 -ggdb -I$(L) -I$(LL)
CC = gcc
MAKE = make

$(PROG1): $(OBJS1) $(LIB)
	$(CC) $(CFLAGS) $^ $(LIB) -lm -o $@

querier.o: querier.c

.PHONY: test clean

test: $(PROG1) testing.sh
	bash -v testing.sh &> testing.out

clean: 
	rm -rf *.dSYM
	rm -f *~ *.o 
	rm -f core
	rm -r *.sorted
	rm -f $(PROG1)