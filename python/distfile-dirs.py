#!/usr/bin/env python3

import sys

"""
Builds list of dictionary directories to split the list of input files
into evenly. Each directory has name of the first file that is located
in the directory. Takes number of directories as an argument and reads
list of files from stdin. The resulting list or directories is printed
to stdout.
"""

dir_num = int(sys.argv[1])
distfiles = sys.stdin.read().splitlines()
distfile_num = len(distfiles)
dir_size = distfile_num / dir_num
# allows adding files in the beginning without repartitioning
dirs = ["0"]
next_dir = dir_size
while next_dir < distfile_num:
    dirs.append(distfiles[round(next_dir)])
    next_dir += dir_size
print("/\n".join(dirs) + "/")
