#include <assert.h>
#include <stdio.h>
#include <stdlib.h>

#define BUFSIZE 64
#define OUT 0
#define CHAR 1
#define STRING 2
#define COMMENT 3

void check(char stack[], int count, char c);

main()
{
	// TODO track line/column
	char stack[BUFSIZE];
	int count, p, c, match, state;

	count = state = 0;
	p = -1;
	while ((c = getchar()) != EOF) {
		if (state == OUT) {
			if (p != '\\' && c == '\'')
				state = CHAR;
			else if (p != '\\' && c == '"')
				state = STRING;
			else if (p == '/' && c == '*')
				state = COMMENT;
		} else if (state == CHAR && p != '\\' && c == '\''
				|| state == STRING && p != '\\' && c == '"'
				|| state == COMMENT && p == '*' && c == '/')
			state = OUT;

		match = -1;
		if (state != OUT)
			;
		// TODO track quotes and comments too
		else if (c == '(' || c == '[' || c == '{') {
			// TODO exit gracefully
			assert(count < BUFSIZE);
			stack[count++] = c;
		} else if (c == ')')
			match = '(';
		else if (c == ']')
			match = '[';
		else if (c == '}')
			match = '{';
		if (match != -1)
			check(stack, count--, match);
		p = c;
	}
	if (count != 0)
		// TODO report next expected character
		return 1;
}

void check(char stack[], int count, char c)
{
	if (count == 0 || stack[count-1] != c)
		// TODO report offending character and its location
		exit(1);
}
