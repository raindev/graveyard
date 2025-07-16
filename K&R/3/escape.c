#include <assert.h>

void escape(char s[], char t[]);
void unescape(char s[], char t[]);
void assert_eq(char s1[], char s2[]);

main()
{
	char unescaped[] = "\tt\\hen\n";
	char escaped[] = "\\tt\\hen\\n";
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
			default:
				t[ti++] = s[si];
				break;
			case '\t':
				t[ti++] = '\\';
				t[ti++] = 't';
				break;
			case '\n':
				t[ti++] = '\\';
				t[ti++] = 'n';
				break;
		}
	t[ti] = '\0';
}

void unescape(char s[], char t[]) {
	int escaped = 0, si, ti;

	for (si = ti = 0; s[si] != '\0'; ++si)
		if (!escaped)
			if (s[si] == '\\')
				escaped = 1;
			else
				t[ti++] = s[si];
		else {
			escaped = 0;
			switch (s[si]) {
				case 'n':
					t[ti++] = '\n';
					break;
				case 't':
					t[ti++] = '\t';
					break;
				default:
					t[ti++] = '\\';
					t[ti++] = s[si];
					break;
			}
		}
	t[ti++] = '\0';
}

void assert_eq(char s1[], char s2[]) {
	int i;
	for (i = 0; s1[i] != '\0' && s2[i] != '\0'; ++i)
		assert(s1[i] == s2[i]);
	assert(s1[i] == s2[i]);
}
