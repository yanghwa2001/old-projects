#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include <stdlib.h>
#include <time.h>

void red () {
  printf("\033[1;31m");
}
void yellow () {
  printf("\033[1;33m");
}
void reset () {
  printf("\033[0m");
}
void blue () {
    printf("\033[0;34m");
}
void green() {
    printf("\033[0;32m");
}
void cyan () {
    printf("\033[0;36m");
}
void purple() {
    printf("\033[0;35m");
}
void white() {
    printf("\033[0;37m");
}

void random_color(int random) {

    if (random == 1){
        red();
    }
    if (random == 2){
        yellow();
    }
    if (random == 3){
        green();
    }
    if (random == 4) {
        blue();
    }
    if (random == 5){ 
        cyan();
    }
    if (random == 6){ 
        white();
    }
    if (random == 7){ 
        purple();
    }
}