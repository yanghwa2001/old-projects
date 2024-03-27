/*
 *
 * pagedir.h 
 * 
 * Ryan Lee, CS50, Spring 2022
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include "../libcs50/webpage.h"
#include "../libcs50/file.h"
#include "../common/index.h"


/******** pageDirectoryCheck ********/
/* Construct the file name for .crawler files
 * 
 * 
 * Return:
 * Opens the file for writing ; return false on error 
 * else close file and return true
 */
bool pageDirectoryCheck(const char* pageDirectory);


/******** valPageDirectory ********/
/* Validate whether the pageDirectory is created by crawler
 * 
 * 
 * Return:
 * Opens the file for writing ; return false on error 
 * else close file and return true
 */
bool valPageDirectory(const char* pageDirectory);


/******** indexLoad ********/
/* Load the indexfile to a new one
 * 
 * 
 * Return:
 * Opens the file for writing ; return false on error 
 * else close file and return true
 */
index_t* indexLoad(char* oldIndexFile);


/******** fileToPage ********/
/* Converts file to a page
 * 
 * 
 * Return:
 * a pointer to a page converted from the file
 * Null if error
 */
webpage_t* fileToPage(FILE* fp);