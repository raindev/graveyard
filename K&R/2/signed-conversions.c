#include <assert.h>

int main()
{
	assert(-1L < 1U); // (unsigned int)1 is promoted to (long)1
	// comparisons of signed and unsigned integers are machine
	// dependent
	assert(-1L > 1UL); // (signed long)-1 is promoted to ~(unsigned long)0

	// this contracdicts to
	// > Longer integers are converted to shorter ones or to
	// chars by dropping the excess high-order bits.
	int i = 32768;
	short s = i;
	assert(s == -32768);
	i = 65536;
	s = i;
	assert(s == 0);
}
