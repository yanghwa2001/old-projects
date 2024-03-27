# CS50 Lab 6
## CS50 Spring 2022

## querier

## Files 

 - `Makefile` - compilation
 - `querier.c`- the implementation of the querier


### Usage
querier.c has the following function:

```c 
void counters_intersect(counters_t* ct1, counters_t* ct2); 
void intersect_helper(void *arg, const int key, const int count);
static inline int min(const int a, const int b);
void counters_union(counters_t* ct1, counters_t* ct2);
void union_helper(void *arg, const int key, const int count);
void countResults(void *arg, const int key, int count);
void rankResults(void *arg, int key, int count);
int compareDoc(const void *a, const void *b);
static inline int min(const int a, const int b);
static void parseArgs(const int argc, char* argv[], char** pageDirectory, char** indexFileName);
bool parseFormat(char* line);
int countWords(char* line);
bool parseLogic(char** words, int wordCount);
void formatWords(char* line, char **words, int wordCount);
counters_t* documentScores(char **words, int wordCount, index_t *index);
```

### Compilation
Simply use `make` while in the common directory to compile the files.

### Additional notes:
Since the querier does not allow 'echo' from bash to be printed out (for testing), all testing documentations were made via comment.
