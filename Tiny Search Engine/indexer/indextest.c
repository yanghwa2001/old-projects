/*
 * 
 * indextest.c 
 *
 */


#include <stdlib.h>
#include <stdio.h>
#include "../common/index.h"
#include "../libcs50/file.h"
#include "../common/word.h"

int main(const int argc, char* argv[]){
    //Check whether there are precisely two arguments


    if (argc != 3){
        fprintf(stderr, "Usage: ./indextest oldIndexFilename newIndexFilename\n");
        exit(EXIT_FAILURE);
    }

    char* oldIndexFileName; 
    oldIndexFileName = argv[1];
    char* newIndexFileName;
    newIndexFileName = argv[2];

    index_t* oldIndex = index_load(oldIndexFileName);

    index_save(oldIndex, newIndexFileName);

    index_delete(oldIndex);

    exit(EXIT_SUCCESS);
}