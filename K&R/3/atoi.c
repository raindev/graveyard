#include <assert.h>
#include <ctype.h>
#include <stdio.h>

int atoi(char s[]);

main()
{
	assert(atoi("-91") == -91);
	assert(atoi("+13") == 13);
	assert(atoi("42xx") == 42);
	assert(atoi("z7") == 0);
}

int atoi(char s[])
{
	int i, n, sign;

	for (i = 0; isspace(s[i]); i++)
		;
	sign = (s[i] == '-') ? - 1 : 1;
	if (s[i] == '+' || s[i] == '-')
		i++;
	for (n = 0; isdigit(s[i]); i++)
		n = 10 * n + (s[i] - '0');
	return sign * n;
}
