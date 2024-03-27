# TSE Querier Design Spec

The TSE Querier is a standalone program that reads the index file produced by the TSE Indexer, and page files produced by the TSE Querier, and answers search queries submitted via stdin.

The Design Spec contains several sections:

* User interface
* Inputs and Outputs
* Functional decomposition into modules
* Pseudo code for logic/algorithmic flow
* Dataflow through modules
* Major data structures
* Testing plan

### User interface

The Querier's only interface with the user is on the command-line; it must always have two arguments.

```
./querier pageDirectory indexFilename
```

For example:

``` bash
$ ./querier ../crawler/data index-letter
```

### Inputs and outputs
Input: the only inputs are command-line parameters; see the User Interface above.

Output: We output the scores of documents that best match the query.
We use a unique document ID as the file name, for document IDs 1, 2, 3, 4, and so forth.
On stdout, we write

 * Score of the document
 * ID of the document
 * URL of the document

 ### Functional decomposition into modules
We anticipate the following modules or functions:

 1. *main*,  initializes other modules
 2. *parseArgs*, which parses the argument
 3. *parseFormat*, which format 
 4. *documentScore*, runs the index and prints things out

 ### Testing plan

*Unit testing*.  A small test program to test each module to make sure it does what it's supposed to do.

*Integration testing*.  Assemble the crawler and test it as a whole.
In each case, examine the output files carefully to be sure they have the contents of the correct page, with the correct URL, and the correct depth.
Ensure that no pages are missing or duplicated.
Print "progress" indicators from the crawler as it proceeds (e.g., print each URL explored, and each URL found in the pages it explores) so you can watch its progress as it runs.

0. Test the program with various forms of incorrect command-line arguments to ensure that its command-line parsing, and validation of those parameters, works correctly.

0. Test the crawler with a  that points to a non-existent server.

0. Test the crawler with a that points to a non-internal server.

0. Test the crawler with a that points to a valid server but non-existent page.

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
