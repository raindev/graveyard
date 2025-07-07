#include <stdio.h>

int any(char s[], char chars[]);

main()
{
	char s[] = "_he!llo";
	printf("s[%d]='!'\n", any(s, "x!1"));
}

int any(char s[], char chars[])
{
	int i, j;
	for (i = 0; s[i] != '\0'; ++i)
		for (j = 0; chars[j] != '\0'; ++j)
			if (s[i] == chars[j])
				return i;
	return -1;
}
