/*
   1. detection
	  - if inside of string, skip forward
		- keep track of state in-string
	  - string start with " not preceeded by \ or '
		- keep track of previous character
	  - string ends with " not preceeded by \
	  - comment starts from / * pair and end with * / pair
   2. keep whitespace surrounding the comment, ensure at least one blank in place of the comment
	  0. ensure there's at least one space between the surrounding non-whitespace characters to preserve separation of expressions
		- keep track of the in-whitespace state and insert a space if the next non-whitespace after comment occurs while not in-whitespace
		- in-whitespace is not affected by comments
	  1. [dropped] if whole comment line is whitespace, omit it
		 - keep track of non-blank line prefixes and omit newline if prefix was empty
	  2. [dropped] if only whitespace after comment, trim right
		 - [dropped] place newline after the last non-whitespace character
			 - track last seen non-whitespace
			 - stripping whitespace left of the comment requires unbounded buffering: when a whitespace is read there's no information if there'll be a comment or a non-whitespace character afterwards
		 - place next non-whitespace (including newline) at the beginning of comment
			- skip whitespaces after comment end
      3. [dropped] if only whitespace before comment, trim left
		 - place next following non-whitespace at the position of beginning of the comment
	  4. strip surrounding whitespace or keep it?
		 - to trim trailing whitespace but not whitespace after comment before non-whitespace, whitespace needs to be buffered
		 - to buffer both spaces and tabs it's insufficient to keep counts, but an (unbounded) buffer is needed (potentially compressed using frequency encoding)
		 - to avoid placing an arbitrary upper limit on the amount of whitespace before the next non-whitespace, while still trimming trailing whitespace, it's easiest to skip the whitespace until the next significant character in all cases, avoiding dynamic memory allocation
*/

#include <assert.h>
#include <ctype.h>
#include <stdio.h>

#define IN 1
#define OUT 0

main()
{
	int string_state, comment_state, space_state;
	int c, prev, pending_slash, pending_space;

	string_state = comment_state = space_state = OUT;
	pending_slash = 0;
	pending_space = 0;
	prev = -1;
	while ((c = getchar()) != EOF) {
		if (string_state == IN) {
			if (c == '"' && prev != '\\')
				string_state = OUT;
			putchar(c);
		} else {
			if (comment_state == IN) {
				if (prev == '*' && c == '/')
					comment_state = OUT;
			} else {
				if (prev == '/' && c == '*') {
					comment_state = IN;
					pending_slash = 0;
					if (space_state == OUT)
						pending_space = 1;
				} else {
					// hold on slash until it's known it's not a start of comment
					if (c != '/') {
						if (pending_space && !isspace(c))
							putchar(' ');
						if (pending_slash)
							putchar('/');
						putchar(c);
						pending_slash = 0;
						pending_space = 0;
					} else {
						if (pending_slash)
							putchar('/');
						pending_slash = 1;
					}
					if (c == '"' && prev != '\\' && prev != '\'')
						string_state = IN;
					else if (isspace(c)) {
						space_state = IN;
						pending_space = 0;
					} else {
						if (!pending_slash)
							space_state = OUT;
					}
				}
			}
		}
		prev = c;
	}
}
