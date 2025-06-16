#include <stdio.h>
#include <ctype.h>

#define MAXLINE 1000

int _getline(char line[], int limit);
int rtrim(char line[], int len);

main()
{
	char line[MAXLINE];
	int len, trim_pos, i;

	while ((len = _getline(line, MAXLINE)) != 0) {
		trim_pos = rtrim(line, len);
		if (trim_pos >= 0) {
			for (i = 0; i <= trim_pos; ++i)
				putchar(line[i]);
			putchar('\n');
		}
	}
}

int rtrim(char line[], int len)
{
	int i;

	for (i = len - 1; i >= 0 && isspace(line[i]); --i)
		;

	return i;
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
