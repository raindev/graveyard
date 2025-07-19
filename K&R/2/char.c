#include <assert.h>
#include <limits.h>

int main()
{
	assert(SCHAR_MIN == -128);
	int x = SCHAR_MIN;
	assert(x == -128);
}
