#!/usr/bin/env python3

"""
Picks the directory for a given file name. Takes a distfile name as an
argument. Reads sorted list of directories from stdin, name of each
directory is assumed to be the name of first file that's located inside.
"""

import sys

distfile = sys.argv[1]
dirs = sys.stdin.read().splitlines()
left = 0
right = len(dirs) - 1
while left < right:
    pivot = round((left + right) / 2)
    if (dirs[pivot] <= distfile):
        left = pivot + 1
    else:
        right = pivot - 1

if distfile < dirs[right]:
    print(dirs[right-1])
else:
    print(dirs[right])
