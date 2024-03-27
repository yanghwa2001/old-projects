# TSE Indexer Design Spec

Recall the [Crawler Requirements Spec](REQUIREMENTS.md); the **indexer** indexes a crawler's output and prints it out in with its frequencies.
It reads the document files produced by the TSE crawler, builds an index, and writes that index to a file. Its companion, the index tester, loads an index file produced by the indexer and saves it to another file.


The indexing of the collected documents leads to queries.

A Design Spec should contain several sections:

* User interface
* Inputs and Outputs
* Functional decomposition into modules
* Pseudo code for logic/algorithmic flow
* Dataflow through modules
* Major data structures
* Testing plan

Let's look through each.

### User interface

The Indexer's only interface with the user is on the command-line; it must always have two arguments.

```
indexer pageDirectory indexFilename
```

For example:

``` bash
$ ./indexer ../crawler/data index-letter
```

### Inputs and outputs

Input: the only inputs are command-line parameters; see the User Interface above.

Output: We save each explored webpage to a file, one file per page.
We use a unique document ID as the file name, for document IDs 1, 2, 3, 4, and so forth.
Within a file, we write

 * the word on the first column,
 * the depth at which it was found on the second column,
 * the frequency on the third column.

### Functional decomposition into modules

We anticipate the following modules or functions:

 1. *main*,  initializes other modules
 2. *parseArgs*, which parses the argument
 3. *indexBuild*, which builds the index
 4. *indexPage*, runs the index and prints things out

And some helper modules that provide data structures:

 1. *index* which uses hashtables
 4. *word* that normalizes words read from indexer

### Pseudo code for logic/algorithmic flow

The indexer shall:

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

**normalize the URL** means to convert it into a clean, canonical form.
As one simple example, 
`Http://CS50TSE.cs.dartmouth.edu/tse/letters/index.html`
becomes
`http://cs50tse.cs.dartmouth.edu/tse/letters/index.html`.

**internal** means the URL stays within the CS50 playground `http://cs50tse.cs.dartmouth.edu/`.

A good implementation will not necessarily encode all the above code in a single, deeply-nested function; part of the Implementation Spec is to break the pseudocode down into a cleanly arranged set of functions.

Notice that our pseudocode says nothing about the order in which it crawls webpages; since it presumably pulls them out of a *bag*, and a *bag* abstract data structure explicitly denies any promise about the order of items removed from a bag, we can't expect any particular crawl order.
That's ok.
The result may or may not be a Breadth-First Search, but for the crawler we don't care about the order as long as we explore everything within the `maxDepth` neighborhood.

The crawler completes and exits when it has nothing left in its bag - no more pages to be crawled.
The maxDepth parameter indirectly determines the number of pages that the crawler will retrieve.


### Testing plan

*Unit testing*.  A small test program to test each module to make sure it does what it's supposed to do.

*Integration testing*.  Assemble the crawler and test it as a whole.
In each case, examine the output files carefully to be sure they have the contents of the correct page, with the correct URL, and the correct depth.
Ensure that no pages are missing or duplicated.
Print "progress" indicators from the crawler as it proceeds (e.g., print each URL explored, and each URL found in the pages it explores) so you can watch its progress as it runs.

0. Test the program with various forms of incorrect command-line arguments to ensure that its command-line parsing, and validation of those parameters, works correctly.

1. Crawl a simple, closed set of cross-linked web pages.
Ensure that some page(s) are mentioned multiple times within a page, and multiple times across the set of pages.
Ensure there is a loop (a cycle in the graph of pages).
In such a little site, you know exactly what set of pages should be crawled, at what depths, and you know where your program might trip up.

2. Point the crawler at a page in that site, and explore at depths 0, 1, 2, 3, 4, 5.
Verify that the files created match expectations.

2. Repeat with a different seed page in that same site.
If the site is indeed a graph, with cycles, there should be several interesting starting points.

3. Point the crawler at our Wikipedia playground.
Explore at depths 0, 1, 2.
(It takes a long time to run at depth 2 or higher!) Verify that the files created match expectations.

5. When you are confident that your crawler runs well, test it on a part of our playground or with a greater depth - but be ready to kill it if it seems to be running amok.
