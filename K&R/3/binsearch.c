#include <assert.h>
#include <stdio.h>

#define INPUT_SIZE 1000000

int binsearch(int x, int v[], int n);
int _binsearch(int x, int v[], int n);

main()
{
	int sorted[INPUT_SIZE];
	int i;

	for (i = 0; i < INPUT_SIZE; ++i)
		sorted[i] = i;
	const int target = 257233;

	// TODO time the calls

	assert(binsearch(target, sorted, INPUT_SIZE) == 257233);
	assert(_binsearch(target, sorted, INPUT_SIZE) == 257233);

	int input[] = {0, 2, 4, 6, 8};
	for (i = 0; i < 5; ++i) {
		assert(_binsearch(2 * i, input, 5) == i);
		assert(_binsearch(2 * i + 1, input, 5) == -1);
	}
}

int _binsearch(int x, int v[], int n)
{
	int low, high, mid;

	low = 0;
	high = n - 1;
	while (low < high) {
		mid = (low + high) / 2;
		if (v[mid] < x) {
			assert(low != mid + 1);
			low = mid + 1;
		} else {
			assert(high != mid);
			high = mid;
		}
	}
	assert(low == high);
	return v[low] == x ? low : -1;
}

int binsearch(int x, int v[], int n)
{
	int low, high, mid;

	low = 0;
	high = n - 1;
	while (low <= high) {
		mid = (low + high) / 2;
		if (x < v[mid])
			high = mid - 1;
		else if (x > v[mid])
			low = mid + 1;
		else
			return mid;
	}
	return -1;
}
