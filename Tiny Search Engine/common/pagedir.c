/*
 *
 * pagedir.c 
 *
 * Ryan Lee, CS50, Spring 2022
 *
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include "../libcs50/webpage.h"
#include "../libcs50/file.h" 
#include "../common/index.h" 


static const char crawlerFile[] = ".crawler";

bool pageDirectoryCheck(const char* pageDirectory){
  //First false condition: directory is null
  if(pageDirectory == NULL){
    return false;
  }

  //initialize str, which the pathname will be built on
  char* fileName = calloc((strlen(pageDirectory) + strlen("/.crawler") ) + 1, sizeof(char)); 

  //construct the name of the path to pageDirectory
  strcpy(fileName, pageDirectory);
	strcat(fileName, "/.crawler");
  
  FILE *fp =fopen(fileName, "w");

  //fp is null means return false
  if( fp == NULL){
    printf("file is null\n");
    free(fileName);
    return false; 

  } 
  
  //otherwise, directory is accessible
  free(fileName);
  fclose(fp);
  return true;
}

bool valPageDirectory(const char* pageDirectory){
  if (pageDirectory == NULL){
    return false;
  }

  FILE* fp;

  //construct the name .crawler, which should exist if crawler made this file
  int fileNameLen = strlen(pageDirectory) + strlen(crawlerFile) + 2;
  char fileName[fileNameLen];
  sprintf(fileName, "%s/%s", pageDirectory, crawlerFile);

  //validate the file
  if ( ( fp = fopen(fileName, "r") ) != NULL){
    fclose(fp);
    return true; 
  } 
  return false;
}


//Helper function to convert files from crawler to pages
webpage_t* fileToPage(FILE* fp){
  if (fp != NULL) {
    char* URL = freadlinep(fp);
    int pageDepth; 
    char* depth = freadlinep(fp);
    sscanf(depth, "%d", &pageDepth);
    char* HTML = freadfilep(fp); 

    //This allows it to be used for webPage
    webpage_t* webpage = webpage_new(URL, pageDepth, HTML);
    free(depth);
    return webpage; 
  }

  else {
    return NULL;
  }
}