#!/usr/bin/env bash

# local version: 1.2.0.1

@test "no name given" {

  # The above line controls whether to skip the test.
  # Normally, we skip every test except for the first one
  # (the first one is always commented out).  This allows for
  # a person to focus on solving a test at a time: you can
  # comment out or delete the
  # line to run the test when you are ready.
  #
  # You can also run all the tests by setting the
  #

  run bash two_fer.sh
  (( status == 0 ))
  [[ $output == "One for you, one for me." ]]
}

@test "a name given" {
  run bash two_fer.sh Alice
  (( status == 0 ))
  [[ $output == "One for Alice, one for me." ]]
}

@test "another name given" {
  run bash two_fer.sh Bob
  (( status == 0 ))
  [[ $output == "One for Bob, one for me." ]]
}

# bash-specific test: Focus the student's attention on the effects of
# word splitting and filename expansion:
# https://www.gnu.org/software/bash/manual/bash.html#Shell-Expansions

@test "handle arg with spaces" {
  run bash two_fer.sh "John Smith" "Mary Ann"
  (( status == 0 ))
  [[ $output == "One for John Smith, one for me." ]]
}

@test "handle arg with glob char" {
  run bash two_fer.sh "*"
  (( status == 0 ))
  [[ $output == "One for *, one for me." ]]
}
