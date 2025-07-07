#include <stdio.h>

void _strcat(char s[], char t[]);

main()
{
	char s[12];
	s[0] = '\0';
	_strcat(s, "hello");
	_strcat(s, " world");
	printf("%s\n", s);
}

void _strcat(char s[], char t[])
{
	int i, j;

	i = j = 0;
	while (s[i] != '\0')
		i++;

	while ((s[i++] = t[j++]) != '\0')
		;
}
