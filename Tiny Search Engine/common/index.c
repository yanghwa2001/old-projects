/*
 *
 * index.c 
 *
 *
 */

#include <stdio.h>
#include <stdlib.h>
#include "../libcs50/file.h"
#include "../libcs50/hashtable.h"
#include "../libcs50/counters.h"
#include "../libcs50/memory.h"
#include "index.h"

/******** local ********/
void save_item(void *arg, const char* key, void* item);
void save_count(void *arg, const int key, const int count);
static void ct_delete(void* item);

/******** global ********/
index_t* index_new(const int numSlots);
bool index_insert(index_t* index, const char* key, const int counterKey);
bool index_save(index_t* index, char* indexFileName); 
bool index_delete(index_t* index);
index_t* index_load(char* oldIndexFileName);


void save_item(void *arg, const char* key, void* item) {
    //iterates through counters 
    FILE* fp = arg;
    counters_t* counters = item;

    fprintf(fp, "%s", key);
    counters_iterate(counters, fp, save_count);
    fprintf(fp, "\n");
}

void save_count(void *arg, const int key, const int count) {
  FILE* fp = arg;
  fprintf(fp, " %d %d", key, count);
}

static void ct_delete(void *item)
{
	counters_t* ct = item;

	if (ct != NULL) {
		counters_delete(item);
	}
}

typedef hashtable_t index_t;

index_t* index_new(const int numSlots){
    index_t* index = hashtable_new(numSlots);
    //Error allocating memory
    if (index == NULL){
        return NULL;
    }
    return index;
}

bool index_insert(index_t* index, const char* key, int counterKey){
    //check parameters
    if (index != NULL && key != NULL && counterKey >= 0){
        counters_t* wordCounter = hashtable_find(index, key);
        //If the counter for a word does not exist already
        if (wordCounter == NULL){
            //add new counter to the hashtable

            counters_t* wordCounter = counters_new();
            counters_add(wordCounter, counterKey); 
            hashtable_insert(index, key, wordCounter);
            return true;
        }
        //this means it does exist already
        else {
            counters_add(wordCounter, counterKey);
            return true;
        }
    }
    return false;
}

bool index_delete(index_t* index){
    if (index != NULL){
        //delete the counters first then free hashtable
        hashtable_delete(index, ct_delete);
        return true;
    }
    //false if items were not deleted or index is null
    return false;
}

bool index_save(index_t* index, char* indexFileName){
    //check parameters
    if (index != NULL && indexFileName != NULL){
        FILE* fp;
        //check whether file is writable
        if ((fp = fopen(indexFileName, "w")) != NULL){
            hashtable_iterate((hashtable_t*)index, fp, save_item);
        }
        else {
            fprintf(stderr, "invalid file\n");
            return false;
        }
        fclose(fp);
        return true;
    }
    return false;
}

index_t* index_load(char* oldIndexFileName) {

    if (oldIndexFileName == NULL){
        return NULL;
    }

    //initialize file opening
    FILE *fp; 
    if ( (fp = fopen(oldIndexFileName, "r")) != NULL){
        //initialize variables
        int numLines = lines_in_file(fp);
        index_t* indexNew = index_new(numLines);
        int id;
        int count;
        char* word;
        while ((word = freadwordp(fp)) != NULL){
            while (fscanf(fp, "%d %d", &id, &count) == 2){
                counters_t* counter;
                //The word doesn't exist in the index
                if ( ( counter = hashtable_find(indexNew, word) ) == NULL){
                    counters_t* countersNew = counters_new();
                    counters_set(countersNew, id, count);
                    hashtable_insert(indexNew, word, countersNew);

                }
                //word does exist
                else {
                    counters_set(counter, id, count);
                }
            }
            free(word);
        }
        fclose(fp);
        return indexNew;
        
    }
    //return null if fp was null
    return NULL;
}