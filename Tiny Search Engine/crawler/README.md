# Crawler

## Program Description
As described in the requirement spec, the TSE crawler is a standalone program that crawls the web and retrieves webpages starting from a "seed" URL. It parses the seed webpage, extracts any embedded URLs, then retrieves each of those pages, recursively, but limiting its exploration to a given "depth".

### Compilation

To compile, `make`.
Command for run: `./crawler`  `url`  `pageDirectory` `depth`

### Usage

`crawler.c` utilizes the following modules or functions:

```c
void crawler(char* seedURL, char* pageDirectory, int maxDepth);
static void pageScanner(webpage_t* page, bag_t* toCrawl, hashtable_t* explored);
void pageSaver(webpage_t* page, const char* pageDirectory, const int id);
```

### Assumptions
The assumptions for the program are provided by the requirements and the design specs


### Testing
To test, simply `make test`.

To test with valgrind, `make valgrind`.
