#include <assert.h>
#include <stdio.h>

#define COLUMNS 4

int spaces(int ws, int col, int limit);

// todo deal with tabs
main()
{
	int c, col, ws;

	ws = 0;
	col = 0;
	while ((c = getchar()) != EOF) {
		assert(col >= 0 && col <= COLUMNS);
		assert(ws >= 0);
		if (c == ' ') {
			++ws;
		} else {
			col = spaces(ws, col, COLUMNS);
			ws = 0;
			if (c == '\n' || col == COLUMNS) {
				putchar('\n');
				col = 0;
			} else if (c != '\n') {
				putchar(c);
				col = col + 1;
			}
		}
	}
	spaces(ws, col, COLUMNS);
}

int spaces(int ws, int col, int limit)
{
	while (ws > 0) {
		if (col == limit) {
			putchar('\n');
			col = 0;
		}
		putchar(' ');
		--ws;
		++col;
	}
	return col;
}
