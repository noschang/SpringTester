#!/usr/bin/env bash

set -e

if [ ! -d /extensions/vscode-server ]; then
    echo "VScode extensions folder doesn't exist. Creating now..."
    mkdir -p /extensions/vscode-server
fi

if [ ! -d /extensions/vscode-server-insiders ]; then
    echo "VScode insiders extensions folder doesn't exist. Creating now..."
    mkdir -p /extensions/vscode-server-insiders
fi

# Replace broken JAR file of the lombok extension if needed
bash /startup/lombok-fix/run-fix.sh

# This prevents docker compose from shuting down the container
# after the startup process. It also treats terminations signals
# comming from docker to stop the container

finish(){
    echo "Termination signal received. Exiting now..."
    exit 0
}

END=""

echo "Initialization complete. Waiting for vscode connections..."

while [ "$END" = "" ]; do
    sleep 1
    trap "END=1 && finish" INT TERM
done

finish