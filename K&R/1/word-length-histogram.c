#include <assert.h>
#include <ctype.h>
#include <stdio.h>

#define MAX_LENGTH 30
#define IN 1
#define OUT 0

main()
{
	int c, i, j, start, state, max;

	int lengths[MAX_LENGTH];
	for (i = 0; i < MAX_LENGTH; ++i)
		lengths[i] = 0;

	max = 0;
	state = OUT;
	for (i = 0; (c = getchar()) != EOF; ++i) {
		if (isalpha(c)) {
			if (state == OUT) {
				state = IN;
				start = i;
			}
		} else if (state == IN) {
			state = OUT;
			int len = (i - start) % MAX_LENGTH;
			assert(len > 0);
			++lengths[len-1];
			if (lengths[len-1] > max)
				max = lengths[len-1];
		}
	}

	for (j = max; j > 0; --j) {
		for (i = 0; i < MAX_LENGTH; ++i)
			if (lengths[i] >= j)
				printf("▒  ");
			else
				printf("   ");
		putchar('\n');
	}
	for (i = 0; i < MAX_LENGTH-1; ++i)
		printf("%-3d", i + 1);
	printf(">%d", MAX_LENGTH + 1);
	putchar('\n');
}
