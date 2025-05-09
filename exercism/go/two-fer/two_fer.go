// Package twofer provides a solution to Two Fer problem.
package twofer

import "fmt"

// ShareWith returns a message about sharing with a given person. If the name
// is empty the second person pronoun will be used.
func ShareWith(name string) string {
	if name == "" {
		name = "you"
	}
	return fmt.Sprintf("One for %s, one for me.", name)
}
