#include "armstrong_numbers.h"
#include <math.h>

bool is_armstrong_number(int candidate)
{
   int digits = log10(candidate) + 1;

   int sum = 0;
   for (int i = candidate; i > 0; i /= 10)
   {
      sum += power(i % 10, digits);
   }
   return sum == candidate;
}

int power(int base, int exponent) {
   if (exponent == 0) {
      return 1;
   }
   if (exponent % 2 == 1) {
      return base * power(base * base, (exponent - 1) / 2);
   } else {
      return power(base * base, exponent / 2);
   }
}
