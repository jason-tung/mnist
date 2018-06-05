#!/bin/bash
for filename in *; do
    echo -e "\e[45m" $filename "\e[0m"
    cat $filename
    echo -e "\n";
done
