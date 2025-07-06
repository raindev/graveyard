#include <stdio.h>
#include <ctype.h>

#define LINECAP 10

int htoi(char s[]);

main()
{
	char *s = NULL;
	size_t cap = LINECAP;
	getline(&s, &cap, stdin);

	printf("%d\n", htoi(s));
}

int htoi(char s[])
{
	int i = 0, n = 0;
	if (s[0] == '0' && tolower(s[1]) == 'x')
		i = 2;
	for (; s[i] != '\0'; ++i)
		if (isdigit(s[i]))
			n = n * 16 + s[i] - '0';
		else if (tolower(s[i]) >= 'a' && tolower(s[i]) <= 'f')
			n = n * 16 + 10 + tolower(s[i]) - 'a';
		else
			return n;
	return n;
}
