#include <stdio.h>
#include <stdlib.h>

int is_vovel(char c);

char *disemvowel(const char *str)
{
	int consonant_count = 0;
	for (const char *c = str; *c != '\0'; ++c)
		if (!is_vovel(*c))
				consonant_count++;
	char *res = calloc(consonant_count + 1, 1);
	int ri = 0;
	for (int si = 0; str[si] != '\0'; ++si) {
		if (is_vovel(str[si]))
				continue;
		res[ri++] = str[si];
	}
	res[ri] = '\0';
	return res;
}

int is_vovel(char c) {
		switch(c)
			case 'a':
			case 'e':
			case 'i':
			case 'o':
			case 'u':
			case 'A':
			case 'E':
			case 'I':
			case 'O':
			case 'U':
				return 1;
		return 0;
}

void main() {
	printf("'%s'\n", disemvowel("HelloU!"));
}
