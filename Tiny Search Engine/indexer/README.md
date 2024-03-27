# CS50 Lab 5
## CS50 Spring 2022

## Indexer

The TSE *indexer* is a standalone program that reads the document files produced by the TSE crawler, builds an index, and writes that index to a file.
Its companion, the *index tester,* loads an index file produced by the indexer and saves it to another file.

### Compilation

To compile, `make`.
Command for run: `./indexer` `pageDirectory`  `indexFileName`

### Usage

`indexer.c` utilizes the following modules or functions:

```c
static void parseArgs(const int argc, char* argv[], char** pageDirectory, char** indexFileName);
static index_t* indexBuild(char* pageDirectory);
static void indexPage(index_t* index, webpage_t* page, const int id);
```

### Assumptions
The assumptions for the program are provided by the requirements and the design specs


### Testing
To test, simply `make test`.
