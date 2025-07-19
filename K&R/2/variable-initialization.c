#include <assert.h>

int x = 0;

int main()
{
	extern int x; // optional
	assert(x == 0);
}
