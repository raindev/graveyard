#include <stdio.h>

#define LINECAP 10

int atoi(char[]);

main()
{
	char *s = NULL;
	size_t cap = LINECAP;
	getline(&s, &cap, stdin);

	printf("%d\n", atoi(s));
}

int atoi(char s[])
{
	int i, n;

	n = 0;
	for (i = 0; s[i] >= '0' && s[i] <= '9'; ++i)
		n = n * 10 + (s[i] - '0');
	return n;
}

