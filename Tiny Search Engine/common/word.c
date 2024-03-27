/*
 * 
 * 
 * word.c
 * 
 */

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <math.h>
#include <stdbool.h>
#include <ctype.h>


char* normalizeWord(char* str){
    if (str != NULL ){
        for(int i = 0; i < strlen(str); i++){
            str[i] = tolower(str[i]);
        }
        return str;
    }
    return NULL;
}
