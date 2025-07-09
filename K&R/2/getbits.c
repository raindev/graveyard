#include <assert.h>

unsigned getbits(unsigned x, int p, int n);

main()
{
	assert(getbits(77, 3, 4) == 13);
}

unsigned getbits(unsigned x, int p, int n)
{
	return (x >> (p - n + 1)) & ~(~0 << n);
}
