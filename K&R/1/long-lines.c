#include <stdio.h>
#include <assert.h>

#define MAXLINE 1000
#define THRESHOLD 80

int _getline(char line[], int limit);

main()
{
	// otherwise len has to be accumulated across line chunks
	assert(MAXLINE > THRESHOLD);

	char line[MAXLINE];
	int len, is_eol, continues;

	continues = 0;
	while ((len = _getline(line, MAXLINE)) != 0) {
		assert(len < MAXLINE); // len excludes \0

		//printf("chunk: '%s'\n", line);
		is_eol = line[len - 1] == '\n';
		continues = continues || !is_eol;
		if (continues || len > THRESHOLD)
			printf("%s", line);
		if (is_eol)
			continues = 0;
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
	assert(i < limit);
	return i;
}
