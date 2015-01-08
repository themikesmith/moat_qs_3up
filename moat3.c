#include<stdio.h>
int numBlanks = 0;
int numTabs = 0;
int numOpenBraces = 0;
int numCloseBraces = 0;
int numOpenBrackets = 0;
int numCloseBrackets = 0;
int numNewlines = 0;

void compute(FILE* file) {
    if(file == NULL) return;
    char c;
    while((c = fgetc(file)) != EOF) {
        switch(c) {
            case ' ':
                numBlanks++;
                break;
            case '\t':
                numTabs++;
                break;
            case '{':
                numOpenBraces++;
                break;
            case '}':
                numCloseBraces++;
                break;
            case '[':
                numOpenBrackets++;
                break;
            case ']':
                numCloseBrackets++;
                break;
            case '\n':
                numNewlines++;
                break;
            default:
                break;
        }
    }
}

int main (int argc, char *argv[]) {
    // check args
    if(argc != 2) {
        fprintf(stderr, "usage: %s [filename]\n", argv[0]);
        return 1;
    }
    FILE* file = fopen(argv[1], "r");
    compute(file);
    printf("Blanks:%d\nTabs:%d\nOpen Braces:%d\nClose Braces:%d\nOpen Brackets:%d\nCloseBrackets:%d\nNewlines:%d\n",
        numBlanks,
        numTabs,
        numOpenBraces,
        numCloseBraces,
        numOpenBrackets,
        numCloseBrackets,
        numNewlines
    );
}
