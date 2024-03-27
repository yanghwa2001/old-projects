#!bin/bash


echo "testing with invalid arugments"
echo "testing with less than 3 arguments"
./indexer
./indexer djsjsk
./indexer sjsjsj ssskk sskskks
echo "testing with invalid directory"
./indexer djdjdj old_file
echo "testing with valid directory but not crawler"
rm -rf invaliddirectory
mkdir invaliddirectory
./indexer ./invaliddirectory
echo "testing with a file you can't write on"
rm -rf data
mkdir data
cd ../crawler
./crawler http://cs50tse.cs.dartmouth.edu/tse/wikipedia/index.html ./data 0
cd ../indexer
touch invalidfile
chmod -w invalidfile
./indexer ./data invalidfile
echo "indexing an output from crawler"
touch test1
./indexer /thayerfs/courses/22spring/cosc050/cs50tse/tse-output/letters-depth-1 test1
rm test1
echo "indexing an output from crawler with more depth"
touch test1
./indexer /thayerfs/courses/22spring/cosc050/cs50tse/tse-output/letters-depth-4 test1
rm test1
touch test1
echo "indexing wiki output from crawler"
./indexer /thayerfs/courses/22spring/cosc050/cs50tse/tse-output/wikipedia-depth-0 test1

echo "Testing indextest"
echo "testing invalid cases of indextest: number of arguments"
./indextest waha
./indextest wa sfs sadsad sad

echo "Testing indextest"
touch test2
./indextest test1 test2

exit 0;