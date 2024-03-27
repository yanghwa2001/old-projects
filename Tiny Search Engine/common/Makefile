# Makefile for 'common' 
#
# CS50, Spring 2022

OBJS = pagedir.o index.o word.o 
LIB = common.a ../libcs50/libcs50.a

FLAGS = 
CFLAGS = -Wall -pedantic -std=c11 -ggdb $(FLAGS)
CC = gcc
MAKE = make

$(LIB): $(OBJS)
	ar cr $(LIB) $(OBJS)

pagedir.o: pagedir.h
index.o: index.h ../libcs50/counters.h ../libcs50/file.h ../libcs50/hashtable.h
word.o: word.h

.PHONY: clean

clean:
	rm -rf *.dSYM
	rm -f $(LIB) *~ *.o
	rm -f core