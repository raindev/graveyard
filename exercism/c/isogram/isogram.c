#include "isogram.h"

#include <stdio.h>

bool is_isogram(const char phrase[]) {
  if (!phrase)
    return false;

  unsigned int letters = 0; // a bitmap
  for (const char *c = phrase; *c; c++) {
    if (*c == ' ' || *c == '-')
      continue;
    const int letterMask = 1 << (*c - 'a');
    if (letters & letterMask)
      return false;
    letters |= letterMask;
  }
  return true;
}
