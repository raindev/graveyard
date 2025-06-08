#include <stdio.h>
#include <assert.h>

main()
{
	int c;

	assert((getchar() != EOF) == 1);
	while ((c = getchar()) != EOF)
		putchar(c);
	assert((getchar() != EOF) == 0);

	printf("EOF == %d\n", EOF);
}
