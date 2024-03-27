/*
 * indexer.c
 * 
 * 
 * 
 * Ryan Lee, CS50, Spring 2022
 */ 

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <math.h>
#include "../libcs50/hashtable.h"
#include "../libcs50/counters.h"
#include "../libcs50/webpage.h"
#include "../common/pagedir.h"
#include "../common/index.h"
#include "../common/word.h"

///thayerfs/courses/22spring/cosc050/cs50tse/tse-output/

/******** global ********/
static void parseArgs(const int argc, char* argv[], char** pageDirectory, char** indexFileName);
static index_t* indexBuild(char* pageDirectory);
static void indexPage(index_t* index, webpage_t* page, const int id);


int main(const int argc, char* argv[]){
    //initialize variables
    char* pageDirectory = NULL;
    char* indexFileName = NULL;

    //parse arguments, build an index, save the index, and delete after done
    parseArgs(argc, argv, &pageDirectory, &indexFileName);
    index_t* index = indexBuild(pageDirectory);
    index_save(index, indexFileName);
    index_delete(index);
}

static void parseArgs(const int argc, char* argv[], char** pageDirectory, char** indexFileName){
    //Checking for invalid number of args
    if ( argc != 3){
        fprintf(stderr, "usage: ./indexer pageDirectory indexFilename\n");
        exit(EXIT_FAILURE);
    }

    if (argv[1] != NULL && argv[2] != NULL){

        //Check whether the directory is one created by crawler
        if (valPageDirectory(argv[1]) == false){
            fprintf(stderr, "invalid pageDirectory!\n");
            exit(EXIT_FAILURE);
        }
        *pageDirectory = argv[1];
        
        //Check whether indexFileName is a writable file
        FILE *fp;
        if ((fp = fopen(argv[2], "w")) == NULL){
            fprintf(stderr, "file cannot be opened for writing!\n");
            fclose(fp);
            exit(EXIT_FAILURE);
        }
        fclose(fp);
        *indexFileName = argv[2];
    }
}

static index_t* indexBuild(char* pageDirectory){

    if (pageDirectory != NULL){

        //initialize variables
        index_t* index = index_new(450);
        FILE *fp; 
        int id = 1; 
        
        //build indexfilename
        int fileNameLen = strlen(pageDirectory) + floor(log10(id)) + 2;
        char fileName[fileNameLen];
        sprintf(fileName, "%s/%d", pageDirectory, id);

        while( (fp = fopen(fileName, "r") ) != NULL ){

            webpage_t* currPage;
            

            //convert the file to page and index the page
            if ( (currPage = fileToPage(fp)) != NULL){ 
                indexPage(index, currPage, id);
            }
            else {
                printf("Failed to convert page %d\n", id);
            }
            //Delete current page, incremenet id, make the next file name
            webpage_delete(currPage);
            id++;
            sprintf(fileName, "%s/%d", pageDirectory, id);

            fclose(fp);
        }
        return index;
    }
    return NULL;
}

static void indexPage(index_t* index, webpage_t* page, int id){
    int pos = 0;
    char* word; 
    char* normalWord;
    // if (page != NULL){
    //     printf("page not null\n");
    // }
    // if (webpage_getHTML(page) != NULL){
    //     printf("html not null\n");
    // }

    while (( word = webpage_getNextWord(page, &pos) ) != NULL) {
        if (strlen(word) > 2){
            normalWord = normalizeWord(word);

            index_insert(index, normalWord, id);
        }
        free(word);
    }
}