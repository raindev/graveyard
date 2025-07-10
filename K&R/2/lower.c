#include <assert.h>

int lower(int c);

main()
{
	assert(lower('A') == 'a');
	assert(lower('Z') == 'z');
	assert(lower('x') == 'x');
	assert(lower('$') == '$');
}

int lower(int c)
{
	return (c >= 'A' && c <= 'Z') ? c + 'a' - 'A' : c;
}
