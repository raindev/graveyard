#include <stdio.h>

#define TS 4

main()
{
	int c, col;

	col = 0;
	while ((c = getchar()) != EOF) {
		if (c == '\t') {
			do {
				putchar(' ');
				++col;
			} while(col % TS != 0);
		} else {
			putchar(c);
			if (c == '\n')
				col = 0;
			else
				++col;
		}
	}
}
