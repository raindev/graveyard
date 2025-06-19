#include <stdio.h>

#define TS 4

main()
{
	int c, col, ws;

	for (col = ws = 0; (c = getchar()) != EOF; ++col)
		if (c == ' ') {
			if ((col + 1) % TS == 0) {
				if (ws == 1)
					putchar(' ');
				else
					putchar('\t');
				ws = 0;
			} else
				++ws;
		} else {
			while (ws > 0) {
				putchar(' ');
				--ws;
			}
			if (c == '\n')
				col = -1;
			putchar(c);
		}
	while (ws-- > 0)
		putchar(' ');
}
