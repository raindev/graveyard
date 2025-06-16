#include <stdio.h>

#define MAXLINE 1000

int _getline(char line[], int maxline);
void copy(char to[], char from[]);

main()
{
	int llen, clen, mlen;
	int is_prefix, is_eol;
	char chunk[MAXLINE];
	char prefix[MAXLINE];
	char longest[MAXLINE];

	mlen = llen = 0;
	is_prefix = 1;
	while ((clen = _getline(chunk, MAXLINE)) > 0) {
		is_eol = chunk[clen - 1] == '\n';
		if (llen + clen > mlen) {
			if (is_prefix)
				copy(longest, chunk);
			else
				copy(longest, prefix);
			mlen = llen + clen;
		} else if (is_prefix && !is_eol && clen == MAXLINE - 1)
			copy(prefix, chunk);
		if (is_eol) {
			llen = 0;
			is_prefix = 1;
		} else {
			llen = llen + clen;
			is_prefix = 0;
		}
	}
	if (mlen > 0)
		printf("%s", longest);
	return 0;
}

int _getline(char s[], int lim)
{
	int c, i;

	for (i = 0; i < lim - 1 && (c = getchar()) != EOF && c != '\n'; ++i)
		s[i] = c;
	if (c == '\n') {
		s[i] = c;
		++i;
	}
	s[i] = '\0';
	return i;
}

void copy(char to[], char from[])
{
	int i;

	i = 0;
	while ((to[i] = from[i]) != '\0')
		++i;
}
