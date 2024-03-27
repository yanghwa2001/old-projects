/*
 *
 * index.h 
 *
 *
 */

#include <stdio.h>
#include <stdlib.h>
#include "../libcs50/file.h"
#include "../libcs50/hashtable.h"
#include "../libcs50/counters.h"
#include "../libcs50/memory.h"

/**************** global types ****************/
typedef hashtable_t index_t;

/**************** index_new ****************/
/* Create a new (empty) index.
 *
 * We return:
 *   pointer to a new index, or NULL if error.
 * 
 */
index_t* index_new(const int numSlots);

/**************** index_insert ****************/
/* Add new counter to the index.
 *
 *   Caller provides:
 *   valid pointer to an index, valid string for key, valid integer for the counter key.
 * 
 * We return: 
 *  true if a new counter was inserted, false if any of the parameters was invalid.
 * 
 */
bool index_insert(index_t* index, const char* key, const int counterKey);

/**************** index_save ****************/
/* print the index to file name
 *
 *   Caller provides:
 *   valid pointer to an index, valid string for the indexFileName
 * 
 * We return: 
 *  true if successfully printed the index to file name
 *  false if error or invalid parameters
 * 
 */
bool index_save(index_t* index, char* indexFileName); 


/**************** index_delete ****************/
/* delete the index and its counters
 *
 *   Caller provides:
 *   valid pointer to an index
 * 
 * We return: 
 *  true if successfully deleted the index
 *  false if error or index is null
 * 
 */
bool index_delete(index_t* index);

/**************** index_load ****************/
/* Load the oldIndexFileName to a new IndexFileName
 *
 *   Caller provides:
 *   valid pointer to an old indexfilename
 * 
 * We return: 
 *  a valid pointer to a newIndexFileName, Null if error
 * 
 */
index_t* index_load(char* oldIndexFileName);