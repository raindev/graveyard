#include <assert.h>
#include <float.h>
#include <stdio.h>
#include <limits.h>

main()
{
	// two's complement negative number representation
	assert(~0 == -1);

	char c = ~0;
	if (c < 0)
		printf("char is signed\n");
	else
		printf("char is unsigned\n");

	printf("char: %d - %d\n", CHAR_MIN, CHAR_MAX);
	printf("signed char: %d - %d\n", SCHAR_MIN, SCHAR_MAX);
	printf("unsigned char: %d - %d\n", 0, UCHAR_MAX);

	printf("signed short: %d - %d\n", SHRT_MIN, SHRT_MAX);
	printf("unsigned short: %d - %d\n", 0, USHRT_MAX);

	printf("signed int: %d - %d\n", INT_MIN, INT_MAX);
	printf("unsigned int: %u - %u\n", 0, UINT_MAX);

	printf("signed long: %ld - %ld\n", LONG_MIN, LONG_MAX);
	printf("unsigned long: %d - %lu\n", 0, ULONG_MAX);

	long max_long = 1;
	while (max_long * 2 > 0)
		max_long = max_long * 2;
	max_long = max_long * 2 - 1;
	assert(max_long == LONG_MAX);

	long min_long = ~0;
	while(min_long << 1 < 0)
		min_long = min_long << 1;
	assert(min_long == LONG_MIN);

	unsigned long max_ulong = ~0;
	assert(max_ulong == ULONG_MAX);

	float max_float = 1.0;
	while (max_float * 2 > max_float && max_float * 2 - max_float == max_float)
		max_float = max_float * 2;
	printf("max_float: %f\n", max_float);

	printf("float: %f - %f\n", FLT_MIN, FLT_MAX);
	printf("double: %f - %f\n", DBL_MIN, DBL_MAX);
	printf("long double: %Lf - %Lf\n", LDBL_MIN, LDBL_MAX);
}
