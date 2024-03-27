/*
 * 
 * 
 * word.h: header file for word.c
 * 
 */

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <math.h>
#include <stdbool.h>
#include <ctype.h>

/******** normalizeString *********/
/* input a char, and this will return normalized version of it
 *
 * 
 */
char* normalizeWord(char* str);