# TSE Querier

### Pseudocode for functions

The querier runs as follows:

1. execute from a command line

2. parse arguments

3. turn the given indexFile into an internal data structure

4. read search queries from stdin, one per line, until EOF., clean and parse each query according to the syntax described below

5. split query into individual words, parse for validity

6. search the index for documents that satisfy the query

7. rank the resulting set of documents according to its score, as described below, and print the set of documents in decreasing rank order; for each, list the score, document ID and URL

8. Show results

### How the algorithm was implemented
I utilized various 'structs', including those that allowed me to store two counters at the same time, an array with the index for easier iteration, and a struct called "document" that stored an id and a score. I created various helper functions that eased the length of the main function. DocumentScore allowed me to score documents, but it had four helper functions that would intersect or merge scores from counters.