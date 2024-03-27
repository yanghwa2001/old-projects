# TSE Indexer

### Pseudocode for functions

The Indexer runs as follows:

execute from a command line with usage syntax

./indexer pageDirectory indexFilename
where pageDirectory is the pathname of a directory produced by the Crawler, and
where indexFilename is the pathname of a file into which the index should be written; the indexer creates the file (if needed) and overwrites the file (if it already exists).
validate its command-line arguments:

pageDirectory is the pathname for an existing directory; if it cannot be verified to be a directory produced by the Crawler, the indexer shall print an error message and exit non-zero.
indexFilename is the pathname of a writeable file; it need not already exist, but if it is not possible to open it for writing, the indexer shall print an error message and exit non-zero.
read documents from the pageDirectory, each of which has a unique document ID, wherein

the document id starts at 1 and increments by 1 for each new page,
and the filename is of form pageDirectory/id,
and the first line of the file is the URL,
and the second line of the file is the depth,
and the rest of the file is the page content (the HTML, unchanged).
build an inverted-index data structure mapping from words to (documentID, count) pairs, wherein each count represents the number of occurrences of the given word in the given document. Ignore words with fewer than three characters, and "normalize" the word before indexing. (Here, "normalize" means to convert all letters to lower-case.)

create a file indexFilename and write the index to that file, in the format described below.


### Definition of detailed APIs, interfaces, function prototypes and their parameters

- Crawler utilizes two other modules: `common` and `libcs50`.
- `common` allows the use of `pagedir.h`
- `libcs50` allows the implementation of hashtable, which are used to keep track of words. It also has other useful things such as file.h or memory.h
- `pageDirectoryCheck` takes the name of the given pageDirectory as a parameter and returns a boolean

### Error Handling and Recovery

Your modules must implement exactly the above interface. Do not modify those function prototypes.
If you need helper functions or data types (likely struct types), those should be defined within your module.c and marked static so they are not visible to users of the module.
Your modules must print nothing (except, of course, in the xxx_print() method). If you want to add debugging printfs, they must be protected by something like #ifdef DEBUG or #ifdef TEST. (You can see some examples in bag.c where I’ve protected some debugging code with #ifdef MEMTEST, and a spot in the bag/Makefile that controls that flag from the compiler command line.)
Your modules must have no global variables.
Your modules must have no main() function; as modules, they are meant to be used by other programs. Recall how the module defined by bag.c and bag.h is used by a test program bagtest.c.