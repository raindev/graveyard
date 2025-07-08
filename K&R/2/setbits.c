#include <assert.h>
#include <stdio.h>

int setbits(int x, p, n, y);

main()
{
	assert(setbits(99, 3, 3, 7) == 111);
	assert(setbits(1913, 11, 5, 18) == 2425);
}


int setbits(int x, p, n, y)
{
	return (x & ~(~(~0 << n) << (p - n + 1))) | (y & ~(~0 << n)) << (p - n + 1);
}
