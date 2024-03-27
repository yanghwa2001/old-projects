#!/bin/bash
#testing.sh

#Invalid syntax in argument
./querier
./querier djsjsk
./querier sjsjsj ssskk sskskks

#testing with valid directory but not crawler
rm -rf invaliddirectory
mkdir invaliddirectory
./querier invaliddirectory /thayerfs/courses/22spring/cosc050/cs50tse/tse-output/letters-index-4
rm -rf invaliddirectory

#Testing with invalid characters
echo "I love apples!" | ./querier /thayerfs/courses/22spring/cosc050/cs50tse/tse-output/letters-depth-4 /thayerfs/courses/22spring/cosc050/cs50tse/tse-output/letters-index-4

#Testing with invalid conjunction placements
echo "and for playground" | ./querier /thayerfs/courses/22spring/cosc050/cs50tse/tse-output/letters-depth-4 /thayerfs/courses/22spring/cosc050/cs50tse/tse-output/letters-index-4
echo "playground and and" | ./querier /thayerfs/courses/22spring/cosc050/cs50tse/tse-output/letters-depth-4 /thayerfs/courses/22spring/cosc050/cs50tse/tse-output/letters-index-4
echo "biology playground or" | ./querier /thayerfs/courses/22spring/cosc050/cs50tse/tse-output/letters-depth-4 /thayerfs/courses/22spring/cosc050/cs50tse/tse-output/letters-index-4

#Testing with words not in the index
echo "platypus photosynthesis poop" | ./querier /thayerfs/courses/22spring/cosc050/cs50tse/tse-output/letters-depth-4 /thayerfs/courses/22spring/cosc050/cs50tse/tse-output/letters-index-4

#Testing with correct words
echo "playground and biology" | ./querier /thayerfs/courses/22spring/cosc050/cs50tse/tse-output/letters-depth-4 /thayerfs/courses/22spring/cosc050/cs50tse/tse-output/letters-index-4

exit 0