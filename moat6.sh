#!/usr/bin/env bash

# find all python files
find . -name '*.py' -print0 | xargs -0 wc -l
# count lines in each and print total
