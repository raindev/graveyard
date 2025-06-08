#include <stdio.h>

main()
{
	printf("%3c %6c\n", 'C', 'F');

	int low = -40, high = 60, step = 5;
	int c = low;
	while (c <= high) {
		const float f = c * 9.0/5.0 + 32;
		printf("%3d %6.1f\n", c, f);
		c = c + step;
	}
}
