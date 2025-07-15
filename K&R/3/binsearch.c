#include <assert.h>
#include <stdio.h>
#include <time.h>

#define INPUT_SIZE 1000000
#define ITERATIONS 10

int binsearch(int x, int v[], int n);
int _binsearch(int x, int v[], int n);

main()
{
	int i;
	int input[] = {0, 2, 4, 6, 8};

	for (i = 0; i < 5; ++i) {
		assert(_binsearch(2 * i, input, 5) == i);
		assert(_binsearch(2 * i + 1, input, 5) == -1);
	}

	int sorted[INPUT_SIZE];

	for (i = 0; i < INPUT_SIZE; ++i)
		sorted[i] = i;

	struct timespec start, end;
	int total_nsec, target;

	total_nsec = 0;
	for (i = 0; i < ITERATIONS; ++i) {
		target = 257233 + i * 100;
		clock_gettime(CLOCK_MONOTONIC_RAW, &start);
		assert(binsearch(target, sorted, INPUT_SIZE) == target);
		clock_gettime(CLOCK_MONOTONIC_RAW, &end);
		total_nsec += end.tv_nsec - start.tv_nsec;
	}
	printf("%dns/iteration\n", total_nsec / ITERATIONS);

	total_nsec = 0;
	for (i = 0; i < ITERATIONS; ++i) {
		target = 257233 + i * 100;
		clock_gettime(CLOCK_MONOTONIC_RAW, &start);
		assert(_binsearch(target, sorted, INPUT_SIZE) == target);
		clock_gettime(CLOCK_MONOTONIC_RAW, &end);
		total_nsec += end.tv_nsec - start.tv_nsec;
	}
	printf("%dns/iteration\n", total_nsec / ITERATIONS);
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
