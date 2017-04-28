#!/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
JARFILE="$DIR/target/n26-java-0.0.1-SNAPSHOT.jar"
java -jar $JARFILE
