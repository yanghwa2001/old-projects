# TSE Crawler

### Pseudocode for functions

The crawler runs as follows:

1. execute from a command line as shown in the User Interface
2. parse the command line, validate parameters, initialize other modules in `int main()`
3. start the function `crawler`
4. make a webpage for the seedURL, marked with depth=0
5. add that page to the bag of webpages to crawl
6. add that URL to the hashtable of URLs seen
7. while there are more webpages to crawl: extract a webpage (URL,depth) item from the bag of webpages to be crawled
    8. use `webpage_fetch` to retrieve the HTML for the page at that URL. 
    9. use `pageSaver` to write the webpage to the pageDirectory with a unique document ID, as described in the Requirements. 
    10. if the webpage depth is < maxDepth, explore the webpage to find links: 
    11. use `pageScanner` to parse the webpage to extract all its embedded URLs; 
    12. for each extracted URL, 'normalize' the URL. 
    13. if that URL is not 'internal' (see below), ignore it; 
    14. try to insert that URL into the hashtable of URLs seen 
    15. if it was already in the table, do nothing; 
    16. if it was added to the table, make a new webpage for that URL, at depth+1 
    17. add the new webpage to the bag of webpages to be crawled


### Definition of detailed APIs, interfaces, function prototypes and their parameters

- Crawler utilizes two other modules: `common` and `libcs50`.
- `common` allows the use of `pagedir.h`, which has one function: `pageDirectoryCheck`, which checks whether the directory exists
- `libcs50` allows the implementation of hashtable and bag, which are used to keep track of explored and unexplored pages. It also has other useful things such as file.h or memory.h
- `pageDirectoryCheck` takes the name of the given pageDirectory as a parameter and returns a boolean

### Error Handling and Recovery

Your modules must implement exactly the above interface. Do not modify those function prototypes.
If you need helper functions or data types (likely struct types), those should be defined within your module.c and marked static so they are not visible to users of the module.
Your modules must print nothing (except, of course, in the xxx_print() method). If you want to add debugging printfs, they must be protected by something like #ifdef DEBUG or #ifdef TEST. (You can see some examples in bag.c where I’ve protected some debugging code with #ifdef MEMTEST, and a spot in the bag/Makefile that controls that flag from the compiler command line.)
Your modules must have no global variables.
Your modules must have no main() function; as modules, they are meant to be used by other programs. Recall how the module defined by bag.c and bag.h is used by a test program bagtest.c.