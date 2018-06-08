#!/bin/bash
for filename in net/*.java; do
    javac $filename
    echo $filename compiled
done
for filename in np/*.java; do
    javac $filename
    echo $filename compiled
done
for filename in py/*.java; do
    javac $filename
    echo $filename compiled
done
echo done