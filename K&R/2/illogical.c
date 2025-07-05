#include <assert.h>
#include <stdio.h>
#include <string.h>

#define LIMIT 5

main()
{
	int lim = LIMIT;
	char s[LIMIT];
	int c, i;
	// assignment has to be extracted from the logical expression as otherwise
	// modification and access to c are not ordered (-Wunsequenced)
	c = getchar();
	for (i = 0; (i < lim - 1) * (c != '\n') * (c != EOF) != 0; ++i) {
		s[i] = c;
		c = getchar();
	}
	int len = strlen(s);
	assert(len <= LIMIT);
	assert(s[len - 1] != 0);
	assert(s[len - 1] != EOF);
	printf("%s\n", s);
}
