#include <stdio.h>

void squeeze(char s[], int c);

main()
{
	char s[] = "he!llo!!!";
	squeeze(s, '!');
	printf("%s\n", s);
}

void squeeze(char s[], int c)
{
	int i, j;
	for (i = j = 0; s[i] != '\0'; ++i)
		if (s[i] != c)
			s[j++] = s[i];
	s[j] = '\0';
}
