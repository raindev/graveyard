#include <assert.h>
#include <stdio.h>
#include <string.h>

#define LIMIT 5

main()
{
	int lim = LIMIT;
	char s[LIMIT];
	int c, i;
	for (i = 0; ((i < lim - 1) + ((c = getchar()) != '\n')) + (c != EOF) == 3; ++i)
		s[i] = c;
	int len = strlen(s);
	assert(len <= LIMIT);
	assert(s[len - 1] != 0);
	assert(s[len - 1] != EOF);
	printf("%s\n", s);
}
