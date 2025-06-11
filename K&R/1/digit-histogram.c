#include <stdio.h>

main()
{
	int c, i, j;
	int ndigit[10];

	for (i = 0; i < 10; ++i)
		ndigit[i] = 0;

	while ((c = getchar()) != EOF)
		if (c >= '0' && c <= '9')
			++ndigit[c-'0'];

	int max = 0;
	for (i = 0; i < 10; ++i)
		if (ndigit[i] > max)
			max = ndigit[i];

	for (j = max; j > 0; --j) {
		for (i = 0; i < 10; ++i)
			if (ndigit[i] >= j)
				printf("▒ ");
			else
				printf("  ");
		putchar('\n');
	}
	for (i = 0; i < 10; ++i)
		printf("%d ", i);
	putchar('\n');
}
