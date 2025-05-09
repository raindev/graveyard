#!/usr/bin/env bash

if (( "$#" == 0)); then
  name=you
else
  name="$1"
fi
echo "One for $name, one for me."
