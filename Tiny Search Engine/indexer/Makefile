#Makefile for 'Indexer' and 'Indextest'

L = ../libcs50
LL = ../common

PROG1 = indexer 
PROG2 = indextest

OBJS1 = indexer.o 
OBJS2 = indextest.o

LIB = $(LL)/common.a $(L)/libcs50.a

FLAGS = 
CFLAGS = -Wall -pedantic -std=c11 -ggdb -I$(L) -I$(LL)
CC = gcc
MAKE = make

all: indexer indextest 

$(PROG1): $(OBJS1) $(LIB)
	$(CC) $(CFLAGS) $^ $(LIB) -lm -o $@


$(PROG2): $(OBJS2) $(LIB)
	$(CC) $(CFLAGS) $^ $(LIB) -o $@


indexer.o: indexer.c 

indextest.o: indextest.c

.PHONY: test clean

test: $(PROG1) $(PROG2) testing.sh
	bash -v ./testing.sh &> testing.out

clean: 
	rm -rf *.dSYM
	rm -f *~ *.o 
	rm -f indexer
	rm -f indextest
	rm -f core
	rm -rf test1
	rm -rf test2
	rm -rf data/
	rm -r *.sorted