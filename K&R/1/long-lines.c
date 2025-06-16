#include <stdio.h>

#define MAXLINE 1000

int _getline(char line[], int limit);

main()
{
	char line[MAXLINE];
	int len;

	while ((len = _getline(line, MAXLINE)) != 0)
		if (len > 80)
			printf("%s", line);
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
