#include <assert.h>
#include <stdio.h>

void shellsort(int v[], int n);
void assert_eq(int a1[], int a2[], int n);
void print(int a[], int n);

int main()
{
	int sample[] = {8, 9, 3, 5, -2, 7};
	int n = sizeof(sample) / sizeof(int);
	printf("n = %d\n", n);

	printf("sample:\n");
	print(sample, n);

	shellsort(sample, n);

	int sorted[] = {-2, 3, 5, 7, 8, 9};
	printf("sorted:\n");
	print(sorted, n);

	printf("sorted sample:\n");
	print(sample, n);
	assert_eq(sample, sorted, n);
}

void shellsort(int v[], int n)
{
	int gap, i, j, temp;

	for (gap = n/2; gap > 0; gap /= 2)
		for (i = gap; i < n; i++)
			for (j = i - gap; j >= 0 && v[j] > v [j + gap]; j -= gap) {
				temp = v[j];
				v[j] = v[j + gap];
				v[j + gap] = temp;
			}
}

void assert_eq(int a1[], int a2[], int n)
{
	int i;
	for (i = 0; i < n; i++)
		assert(a1[i] == a2[i]);
}

void print(int a[], int n)
{
	printf("[");
	int i;
	for (i = 0; i < n; i++)
		printf("%d,", a[i]);
	printf("]\n");
}
