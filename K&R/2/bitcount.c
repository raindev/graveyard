#include <assert.h>

int bitcount(unsigned x);
int fbitcount(unsigned x);

main()
{
	assert(bitcount(173) == 5);
	assert(fbitcount(173) == 5);
}

int fbitcount(unsigned x)
{
	int b;

	for (b = 0; x != 0; x &= (x-1), ++b)
		;
	return b;
}

int bitcount(unsigned x)
{
	int b;

	for (b = 0; x != 0; x >>= 1)
		if (x & 1)
			b++;
	return b;
}
