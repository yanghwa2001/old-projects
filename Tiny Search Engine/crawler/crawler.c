/*
 * crawler.c 
 * The TSE crawler is a standalone program that crawls the web 
 * and retrieves webpages starting from a "seed" URL. It parses the 
 * seed webpage, extracts any embedded URLs, then retrieves each of those pages, 
 * recursively, but limiting its exploration to a given "depth".
 * 
 * 
 * Ryan Lee, CS50, Spring 2022
 */ 

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <math.h>
#include "../libcs50/hashtable.h"
#include "../libcs50/bag.h"
#include "memory.h"
#include "../libcs50/webpage.h"
#include "../common/pagedir.h"

void crawler(char* seedURL, char* pageDirectory, int maxDepth);
static void pageScanner(webpage_t* page, bag_t* toCrawl, hashtable_t* explored);
void pageSaver(webpage_t* page, const char* pageDirectory, const int id);

int main(int argc, const char* argv[]){

    //initialize parameters
    char *seedURL = malloc(sizeof(char) * (strlen(argv[1])+1));
    char *pageDirectory = malloc(sizeof(char) * (strlen(argv[2])+1)); 

    //Parse Arguments

    //Checks if there are right number of arguments
    if (argc != 4){
        fprintf(stderr, "invalid syntax! Usage: ./crawler seedURL pageDirectory maxDepth\n");
        exit(EXIT_FAILURE);
    }

    //Checks whether the seedURL is internal
    strcpy(seedURL, argv[1]);
    if (IsInternalURL(seedURL) == false){
        fprintf(stderr, "Not an internal URL!\n");
        free(seedURL);
        free(pageDirectory);
        exit(EXIT_FAILURE);
    }

    //Checks whether page directory is valid
    if (pageDirectoryCheck(argv[2]) == false){
        fprintf(stderr, "Not a valid pageDirectory!\n");
        free(seedURL);
        free(pageDirectory);
        exit(EXIT_FAILURE);
    }
    //page directory is valid, therefore we take in the parameter
    strcpy(pageDirectory, argv[2]);

    //maxDepth check
    //Check whether the passed parameter is an integer
    int maxDepth; 
    if (sscanf(argv[3], "%i", &maxDepth)==0){
        fprintf(stderr, "Inputed maxDepth value is invalid!\n");
        free(seedURL);
        free(pageDirectory);
        exit(EXIT_FAILURE);
    }
    //Checks whether maxDepth is in range
    else if (maxDepth < 0 || maxDepth > 10){
        fprintf(stderr, "maxDepth must be within 0 and 10!\n");
        free(seedURL);
        free(pageDirectory);
        exit(EXIT_FAILURE);
    }

    //Call crawler
    crawler(seedURL, pageDirectory, maxDepth);

    free(pageDirectory);
    //If everything worked out, exit with 0
    exit(EXIT_SUCCESS);
}

void crawler(char* seedURL, char* pageDirectory, int maxDepth){

    //Initialize bag and hashtable for later use
    bag_t *toCrawl = bag_new();
    hashtable_t *explored = hashtable_new(20);
    
    //Initialize initial depth other materials for the "explored" hashtable
    int initialDepth = 0;
    //char *key = "i";

    //Copy the URL for use

    //Make webpage for the seedURL, add that toCrawl 
    webpage_t *seedPage = webpage_new(seedURL, initialDepth, NULL);
    printf("Created bag of toCrawl pages\n");
    bag_insert(toCrawl, seedPage);

    //add that URL to the hashtable of URLs seen
    printf("Created hashtable for explored pages\n");
    hashtable_insert(explored, seedURL, "");

    webpage_t *currPage;
    int id = 1;
	while ((currPage = bag_extract(toCrawl)) != NULL){
        printf("Crawling...\n");
        //Error handling fetch
        if (!webpage_fetch(currPage)){ 
			fprintf(stderr, "Error fetching %s!\n", webpage_getURL(currPage));
            webpage_delete(currPage);
            continue;
        }
        //If no error with fetching webpage,
        else {
            //Save the file
            pageSaver(currPage, pageDirectory, id);
            
            //If we haven't gotten to max depth
            if(webpage_getDepth(currPage) < maxDepth){
                //Call pagescanner
                pageScanner(currPage, toCrawl, explored);
            }
            //Delete webpage and increment id
            //webpage_delete(currPage);
            id += 1;
        }
        webpage_delete(currPage);
    }
    //get rid of the bag
    bag_delete(toCrawl, webpage_delete);
    //get rid of the hashtable
    hashtable_delete(explored, NULL);
}

//PagerSaver
void pageSaver(webpage_t *page, const char* pageDirectory, const int id)
{
    if (pageDirectory != NULL && page != NULL){
        //Allocate memory for the name of the file with contents
        //malloc character size muliplied by however long the filename is + length of id
        char* fName = calloc((strlen(pageDirectory))+3, sizeof(char));
        sprintf(fName, "%s/%d", pageDirectory, id);
        
        FILE *fp;
        if ((fp = fopen(fName, "w")) != NULL){
            //print URL
            fprintf(fp, "%s\n", webpage_getURL(page));
            //print Depth
            fprintf(fp, "%d\n", webpage_getDepth(page));
            //print content
            fprintf(fp, "%s\n", webpage_getHTML(page));

            //close after printing everything out
            fclose(fp);
            free(fName);
            
        }
        else{
            // maybe not
            fclose(fp);
            free(fName);
        }
    }
}

//PagerScanner
static void pageScanner(webpage_t *page, bag_t * toCrawl, hashtable_t *explored){
    int pos = 0;
    char *result;
    //char *key = "i";
    int depth = webpage_getDepth(page) + 1;

    //Call getNextURL for scanning
    while ((result = webpage_getNextURL(page, &pos)) != NULL){
        //make sure url is internal
        if (IsInternalURL(result)){
            //try to insert that URL into the *hashtable* of URLs seen
            if (hashtable_insert(explored, result, "")){
                //create new webpage if inserted
                webpage_t* newPage = webpage_new(result, depth, NULL);

                //also put it in the bag
                bag_insert(toCrawl, newPage);
            }
            else {
                free(result);
            }
        }
        else {
            free(result);
        }
    }

}