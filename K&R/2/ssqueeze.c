#include <stdio.h>

void ssqueeze(char s[], char chars[]);

main()
{
	char s[] = "_he!llo.!!!";
	ssqueeze(s, "!_.");
	printf("%s\n", s);
}

void ssqueeze(char s[], char chars[])
{
	int i, j, k;
	int skip;

	for (i = j = 0; s[i] != '\0'; ++i) {
		skip = 0;
		for (k = 0; chars[k] != '\0'; ++k)
			if (s[i] == chars[k]) {
				skip = 1;
				break;
			}
		if (!skip)
			s[j++] = s[i];
		s[j] = '\0';
	}
}
