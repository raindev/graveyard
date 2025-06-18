#include <stdio.h>

#define MAXLINE 1000

void reverse(char s[], int len);
int _getline(char line[], int limit);

main()
{
	int len;
	char line[MAXLINE];

	while ((len = _getline(line, MAXLINE)) != 0) {
		reverse(line, len);
		printf("%s\n", line);
	}
}

void reverse(char s[], int len)
{
	int i;
	char c;

	for (i = 0; i < len / 2; ++i) {
		c = s[i];
		s[i] = s[len - 1 - i];
		s[len - 1 - i] = c;
	}
}

int _getline(char line[], int limit)
{
	int c, i;

	i = 0;
	while (i < limit - 1 && (c = getchar()) != EOF && c != '\n')
		line[i++] = c;
	if (c == '\n')
		line[i++] = '\n';
	line[i] = '\0';
	return i;
}
