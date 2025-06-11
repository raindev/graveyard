#include <stdio.h>

#define LOWER 0
#define UPPER 300
#define STEP -20

float celsius(float f);

main()
{
	printf("%3c %6c\n", 'F', 'C');

	int fahr;
	for (fahr = UPPER; fahr >= LOWER; fahr = fahr + STEP)
		printf("%3d %6.1f\n", fahr, celsius(fahr));
}

float celsius(float f)
{
	return 5.0/9.0 * (f - 32);
}
