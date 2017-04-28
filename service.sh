#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
JARFILE="$DIR/target/n26-java-0.0.1-SNAPSHOT.jar"
RUN="java -jar $JARFILE"

start() {
    if is_running; then
        echo 'Service already running' >&2
        return 1
    fi
    local CMD="$RUN & echo \$!"
    sh -c "$CMD" >/dev/null
}

stop() {
    if ! is_running; then
        echo 'Service not running' >&2
        return 1
    fi
    kill -15 $(pgrep -f $JARFILE)
}

status() {
    if is_running; then
        echo 'Service is running' >&2
    else
        echo 'Service is not running' >&2
    fi 
}

is_running() {
    if pgrep -f $JARFILE > /dev/null; then
        return 0
    else
        return 1
    fi
}

display_help() {
    echo "Usage: $0 {start|stop|status}"
}

case $1 in
    start)
        start 
        ;;
    stop)
        stop
        ;;
    status)
        status
        ;;
    *)
        display_help
        ;;
esac
