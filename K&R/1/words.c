#include <ctype.h>
#include <stdio.h>

#define IN 1
#define OUT 0

main()
{
	int c, state;

	state = IN;
	while ((c = getchar()) != EOF)
		if (!isspace(c)) {
			putchar(c);
			state = IN;
		} else if (state == IN) {
			putchar('\n');
			state = OUT;
		}
}
