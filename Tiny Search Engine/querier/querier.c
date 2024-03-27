/*
 * querier.c
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

/******** local data types *******/
struct twocts {
	counters_t *result;
	counters_t *another;
};

//Stores docID and score for satisfying documents
typedef struct document {
    int docID;
    int score;
} document_t;

typedef struct tracker {
    document_t** docList;
    int index;
} tracker_t;

/******** local function prototypes *******/
void counters_intersect(counters_t* ct1, counters_t* ct2); 
void intersect_helper(void *arg, const int key, const int count);
static inline int min(const int a, const int b);
void counters_union(counters_t* ct1, counters_t* ct2);
void union_helper(void *arg, const int key, const int count);
void countResults(void *arg, const int key, int count);
void rankResults(void *arg, int key, int count);
int compareDoc(const void *a, const void *b);

static inline int min(const int a, const int b) {
  return (a < b ? a : b);
}

/******** functions ********/
static void parseArgs(const int argc, char* argv[], char** pageDirectory, char** indexFileName);
bool parseFormat(char* line);
int countWords(char* line);
bool parseLogic(char** words, int wordCount);
void formatWords(char* line, char **words, int wordCount);
counters_t* documentScores(char **words, int wordCount, index_t *index);


int main(const int argc, char* argv[]){

    char* pageDirectory = NULL;
    char* indexFileName = NULL;

    parseArgs(argc, argv, &pageDirectory, &indexFileName);

    //internalize the data structure index from the indexFile
    index_t* index = index_load(indexFileName);

    char* line;
    printf("Query? \n");
    //Start the query
    while ((line = readlinep())) {
        //if the query consists of just spaces or is empty, bring it back to the beginning of the loop
        if (!parseFormat(line)){
            free(line);
            continue;
        }

        //Count how many words there are
        int wordCount = countWords(line);
        char *words[wordCount];

        //Format words before checking if/and placements
        formatWords(line, words, wordCount);

        //If and/or placement is invalid, start the querier from beginning
        if (!parseLogic(words, wordCount)){
            free(line);
            continue;
        }

        //Now print the 'clean' query for the user to see
        printf("Query: ");
        for (int i = 0; i < wordCount; i++) {
            printf("%s ", words[i]);
        }
        printf("\n");

        //Start scoring the documents based on words
        counters_t* scoreCounter = documentScores(words, wordCount, index); //free later
        if (scoreCounter == NULL){
            free(line);
        }

        //Count the number of results
        //int* resultCount = malloc(sizeof(int));

        int resultCount = 0;
        counters_iterate(scoreCounter, &resultCount, countResults);

        //Check if there are no resulting documents
        if (resultCount == 0){
            printf("No documents match.\n");
            counters_delete(scoreCounter);
            free(line);
            continue;
        }

        //Convert all the results into an document
        //Put the resulting documents into an array
        document_t** docList = malloc(sizeof(document_t*) * resultCount);
        tracker_t tracker;
        tracker.docList = docList;
        int index = 0;
        tracker.index = index;
        

        counters_iterate(scoreCounter, &tracker, rankResults); 
        // define a helper struct that keeps an index variable, an reference to docList
        // call counters iterate on scoreCounter
        //      for each id, score
        //           initialize both the id and score for docList[index]
        //           incremenent index
        qsort(tracker.docList, resultCount, sizeof(document_t), compareDoc);

        printf("Matches %d documents (ranked):\n", resultCount);

        for (int i = 0; i < resultCount; i++) {
            // Make filename to get URL
            int docID = tracker.docList[i]->docID;

            int fileNameLen = strlen(pageDirectory) + floor(log10(docID)) + 2;
            char fileName[fileNameLen];
            sprintf(fileName, "%s/%d", pageDirectory, docID);

            // Get URL
            FILE *fp = fopen(fileName, "r");
            char *url = freadlinep(fp);


            printf("Score: %1d docID: %1d | %s\n", tracker.docList[i]->score, docList[i]->docID, url);


            fclose(fp);
            free(url);
        }
        counters_delete(scoreCounter);

        for (int i=0; i < resultCount; i++){
            free(tracker.docList[i]);
        }
        free(tracker.docList);
        
    }
    index_delete(index);
    free(line);
    exit(EXIT_SUCCESS);
}

/**
 * Parses the parameters of the command
 */
static void parseArgs(const int argc, char* argv[], char** pageDirectory, char** indexFileName){
    //Checking for invalid number of args
    if ( argc != 3){
        fprintf(stderr, "usage: ./querier pageDirectory indexFilename\n");
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
        if ((fp = fopen(argv[2], "r")) == NULL){
            fprintf(stderr, "the indexFile cannot be opened for writing!\n");
            fclose(fp);
            exit(EXIT_FAILURE);
        }
        fclose(fp);
        *indexFileName = argv[2];
    }
}

//Helper function to check whether the words are formatted correctly. 
bool parseFormat(char* line){

    //iterating pointer for input
    char *wordIterator = line;
    //counts the number of str that is not an alphabet
    int notAlphCount = 0;
    //counts the number of spaces
    int spaceCount = 0;

    while (*wordIterator){
        //increment space count if it is space
        if (isspace(*wordIterator)){
            spaceCount +=1;
        }
        else if (!isalpha(*wordIterator)){
            //not an alphabet; 
            notAlphCount +=1;
            printf("Error: bad character %s in query.\n", wordIterator);
            break;
        }
        //move onto the next word
        wordIterator++;
    }
    
    //If stdin consists solely of spaces or contains a non-alphabet, return 0
    if (notAlphCount > 0){
        return false;
    }
    //otherwise return true
    return true;
}

//Helper Function: Counts how many words are in the line for later use
int countWords(char* line){
    char *wordIterator = line; 
    int wordCount = 0;

    while (*wordIterator != '\0'){

        //In case the stdin does not start with the word
        while (!isalpha(*wordIterator)){
            //keep iterating until we see a word
            wordIterator++;
        }

        if (isalpha(*wordIterator)){
            //If a word is found, increment the counter
            wordCount +=1;
        }

        while (isalpha(*wordIterator) != 0 && *wordIterator != '\0') {
            //loop until end
            wordIterator++;
        }
    } 
    return wordCount; 
}

void formatWords(char* line, char **words, int wordCount){
    //pointer for line
    char *word = line;
    char *rest = line;

    for (int i = 0; i < wordCount; i++){
        while (!isalpha(*word)) {
            word++;
        }

        //start rest from where the word was
        rest = word;

        //Move onto the next word
        while (isalpha(*rest) != 0) {;
            rest++;
        }
        *rest = '\0';
        //normalize the word
        words[i] = normalizeWord(word);
        
        //Now word can start from where rest left off
        word = rest;
    }
}

bool parseLogic(char **words, int wordCount){
    //Check if first word is conjunction
    if ((strcmp(words[0], "and") == 0) || (strcmp(words[0], "or") == 0 )) {
        fprintf(stderr, "Error: '%s' cannot be first\n", words[0]);
        return false;
    }

    //Check if last word is conjunction
    if ((strcmp(words[wordCount-1], "and") == 0) || ((strcmp(words[wordCount-1], "or")) == 0 )) {
        fprintf(stderr, "Error: '%s' cannot be last\n", words[wordCount-1]);
        return false;
    }

    //Check for adjacent conjunctions
    for (int i = 1; i < wordCount - 1; i++) {
        if ( ( strcmp(words[i], "and") == 0 )|| (strcmp(words[i], "or") == 0) ) {
            //checks for and/or before the current and/or
            if (strcmp(words[i - 1], "and") == 0 || strcmp(words[i - 1], "or") == 0) {
                fprintf(stderr, "Error: '%s' and %s cannot be adjacent\n", words[i-1], words[i]);
                return false;
            }
            //checks for and/or after the current and/or
            if (strcmp(words[i + 1], "and") == 0|| strcmp(words[i + 1], "or") == 0) {
                fprintf(stderr, "Error: '%s' and %s cannot be adjacent\n", words[i], words[i+1]);
                return false;
            }
        }
    }
    return true;
}

/**
 * Creates a counter to store the scores of wrds
 * 
 * returns a pointer to the resulting counter that keeps track of scores and id
 */
counters_t* documentScores(char **words, int wordCount, index_t *index){

    counters_t* result = counters_new();
    counters_t* tmp = NULL;

    for (int i = 0; i < wordCount; i++){
        //skip 'and'
        if (strcmp(words[i], "and") == 0) {
            continue;
        }
        //if 'or' is seen
        else if (strcmp(words[i], "or") == 0){
            //increment i to check next word
            //temporary counter to keep score
            counters_union(result, tmp);
            counters_delete(tmp);
            tmp = NULL;
        }
        //Not 'and'/'or' which means a regular word
        else {
            if (tmp == NULL){
                tmp = counters_new();
                counters_union(tmp, hashtable_find(index, words[i]));
            }
            else{
                counters_intersect(tmp, hashtable_find(index, words[i]));
            }
        }
    }
    counters_union(result, tmp);
    counters_delete(tmp);
    tmp = NULL;
    return result;

}

//Function that intersects counters
void counters_intersect(counters_t* ct1, counters_t* ct2) {
	struct twocts args = {ct1, ct2}; 
	counters_iterate(ct1, &args, intersect_helper);
}

//Helper for counters_intersect
void intersect_helper(void *arg, const int key, const int count)
{
	struct twocts *two = arg; 

	counters_set(two->result, key, min(count, counters_get(two->another, key)));
}


//Function that finds the union of two counters
void counters_union(counters_t* ct1, counters_t* ct2){

	struct twocts args = {ct1, ct2}; 
    counters_iterate(ct2, &args, union_helper);
}

//Helper for counters_union
void union_helper(void *arg, const int key, const int count){
    struct twocts *two = arg; 

    counters_set( two->result, key, (count + counters_get(two->result, key)));
}

//Counts the number of results of documents
void countResults(void *arg, const int key, int count) {
    int *total = arg;
    (*total)++;
}

void rankResults(void *arg, int key, int count){
    tracker_t* tracker = arg;
    tracker->docList[tracker->index]->docID = key;
    tracker->docList[tracker->index]->score = count;
    tracker->index++;
}

int compareDoc(const void *a, const void *b){
    document_t* doc1 = (document_t*) a;
    document_t* doc2 = (document_t*) b;
    return doc2->score - doc1->score;
}