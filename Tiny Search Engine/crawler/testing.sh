# !/bin/bash
#testing.sh
#Usage: ./testing.sh
#Output: performs testing for crawler
#Author: Ryan Lee, CS50, Spring 2022

ret=0

#Incorrect command line arguments testing
#Incorrect number of args
./crawler https://youtube.com data 2 and qdij

#Non-existent server
./crawler http://csfasshfoiah.sadaosdh data 2

#Not internal link
./crawler https://youtube.com data 2

#Non-existent page
./crawler http://cs50tse.cs.dartmouth.edu/tse/letters/inadffffff.html data 1

#Invalid page directory
./crawler http://cs50tse.cs.dartmouth.edu/tse/letters/index.html NULL 2

#Invalid maxDepth
./crawler http://cs50tse.cs.dartmouth.edu/tse/letters/index.html data 50

#Crawling a simple page
echo "Crawling a simple page"
./crawler http://cs50tse.cs.dartmouth.edu/tse/letters/index.html data 2
echo "done!"

#explore at depths 0, 1, 2, 3, 4, 5
echo "explore at depths 0, 1, 2, 3, 4, 5"
./crawler http://cs50tse.cs.dartmouth.edu/tse/letters/index.html data 0
./crawler http://cs50tse.cs.dartmouth.edu/tse/letters/index.html data 1
./crawler http://cs50tse.cs.dartmouth.edu/tse/letters/index.html data 2
./crawler http://cs50tse.cs.dartmouth.edu/tse/letters/index.html data 3
./crawler http://cs50tse.cs.dartmouth.edu/tse/letters/index.html data 4
./crawler http://cs50tse.cs.dartmouth.edu/tse/letters/index.html data 5
echo "finished with all depths!"

#Repeat with different seed
echo "Repeating with different seed..."
./crawler http://old-www.cs.dartmouth.edu/~cs50/data/tse/letters/A.html data 0
echo "finished 0"
./crawler http://old-www.cs.dartmouth.edu/~cs50/data/tse/letters/A.html data 1
echo "finished 1"
./crawler http://old-www.cs.dartmouth.edu/~cs50/data/tse/letters/A.html data 2
echo "finished 2"
./crawler http://old-www.cs.dartmouth.edu/~cs50/data/tse/letters/A.html data 3
echo "finished 3"
./crawler http://old-www.cs.dartmouth.edu/~cs50/data/tse/letters/A.html data 4
echo "finished 4"
./crawler http://old-www.cs.dartmouth.edu/~cs50/data/tse/letters/A.html data 5
echo "finished 5"
echo "done!"

#Wikepedia testing
echo "testing with the wikepedia pages..."
./crawler http://cs50tse.cs.dartmouth.edu/tse/wikipedia/index.html data 0
echo "done with depth 0"
./crawler http://cs50tse.cs.dartmouth.edu/tse/wikipedia/index.html data 1
echo "done with depth 1"
