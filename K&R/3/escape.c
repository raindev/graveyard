#include <assert.h>

void escape(char s[], char t[]);
void unescape(char s[], char t[]);
void assert_eq(char s1[], char s2[]);

main()
{
	char unescaped[] = "\tthen\n";
	char escaped[] = "\\tthen\\n";
	char s[128];

	escape(unescaped, s);
	assert_eq(s, escaped);

	unescape(escaped, s);
	assert_eq(s,unescaped);
}

void escape(char s[], char t[]) {
	int si, ti;
	for (ti = si = 0; s[si] != '\0'; ++si)
		switch (s[si]) {
			case '\t':
				t[ti++] = '\\';
				t[ti++] = 't';
				break;
			case '\n':
				t[ti++] = '\\';
				t[ti++] = 'n';
				break;
			default:
				t[ti++] = s[si];
				break;
		}
	t[ti] = '\0';
}

void unescape(char s[], char t[]) {
	int escaped = 0, si, ti;

	for (si = ti = 0; s[si] != '\0'; ++si)
		switch (s[si]) {
			case '\\':
				escaped = 1;
				break;
			case 'n':
			case 't':
				if (escaped) {
					if (s[si] == 't')
						t[ti++] = '\t';
					else
						t[ti++] = '\n';
				} else
					t[ti++] = s[si];
				escaped = 0;
				break;
			default:
				t[ti++] = s[si];
				escaped = 0;
				break;
		}
	t[ti++] = '\0';
}

void assert_eq(char s1[], char s2[]) {
	int i;
	for (i = 0; s1[i] != '\0' && s2[i] != '\0'; ++i)
		assert(s1[i] == s2[i]);
	assert(s1[i] == s2[i]);
}
