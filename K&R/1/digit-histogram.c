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

	for (i = 0; i < 10; ++i) {
		printf("%d ", i);
		for (j = 0; j < ndigit[i]; ++j)
			putchar('*');
		putchar('\n');
	}
}
