#!/bin/bash

echo 'script demo.txt' | socat EXEC:'docker attach cli',pty STDIN

sleep 10

if docker logs cli | grep 'error'; then
    echo "0"
else
    echo "1"
fi
