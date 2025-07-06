#include <assert.h>
#include <stdio.h>

// illegal in C23, assumes int in ANSI C and allows to pass invalid type
double id();

main()
{
	assert(-1L < 1U);
	assert(2.0 == (double)2);
	assert(2.0 != id(2));
}

double id(double x)
{
	return x;
}
