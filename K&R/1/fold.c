#include <assert.h>
#include <stdio.h>

#define COLUMNS 4
#define TS 4

int spaces(char ws[], int cws, int col, int limit);

main()
{
	assert(TS <= COLUMNS);

	int c, col, cws;
	char ws[COLUMNS];

	cws = 0;
	col = 0;
	while ((c = getchar()) != EOF) {
		assert(col >= 0 && col <= COLUMNS);
		assert(cws >= 0);
		if (c == ' ' || c == '\t') {
			ws[cws++] = c;
		}
		if (c != ' ' && c != '\t' || col == COLUMNS || cws == COLUMNS) {
			col = spaces(ws, cws, col, COLUMNS);
			cws = 0;
			if (c == '\n' || col == COLUMNS) {
				putchar('\n');
				col = 0;
			}
			if (c != '\n' && c != ' ' && c != '\t') {
				putchar(c);
				col = col + 1;
			}
		}
	}
	spaces(ws, cws, col, COLUMNS);
}

int spaces(char ws[], int cws, int col, int limit)
{
	int i;

	for (i = 0; i < cws; ++i) {
		if (col == limit) {
			putchar('\n');
			col = 0;
		}
		if (ws[i] == ' ') {
			putchar(' ');
			++col;
		} else {
			assert(ws[i] == '\t');
			if (limit - col < TS) {
				putchar('\n');
				col = 0;
			}
			putchar('\t');
			col = col + TS;
		}
	}
	return col;
}
