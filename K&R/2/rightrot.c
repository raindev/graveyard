#include <assert.h>
#include <stdio.h>

unsigned rightrot(unsigned x, int n);
int unsigned_bits();

main()
{
	assert(unsigned_bits() == 32);
	assert(rightrot(1, 1) == 2147483648U);
	assert(rightrot(77, 4) == 3489660932);
	assert(rightrot(30, 1) == 15);
}

int unsigned_bits()
{
	int i;
	unsigned tmp;
	for (i = 0, tmp = ~0; tmp != 0; tmp = tmp >> 1, ++i)
		;
	return i;
}

unsigned rightrot(unsigned x, int n)
{
	return (x & ~(~0 << n)) << (unsigned_bits() - n) | x >> n;
}
