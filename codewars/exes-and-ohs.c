#include <assert.h>
#include <stdbool.h>

bool xo (const char* str)
{
	int xcnt, ocnt;
	xcnt = ocnt = 0;
	for (const char* c = str; *c != '\0'; ++c)
		if (*c == 'x' || *c == 'X')
			++xcnt;
		else if (*c == 'o' || *c == 'O')
			++ocnt;
	return xcnt == ocnt;
}

int main()
{
	assert(xo("xX-oO") == true);
}
