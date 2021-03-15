#!/usr/bin/env bash

echo "Installing graphic libraries..." &&
    apt-get update &&
    export DEBIAN_FRONTEND=noninteractive &&
    apt-get -y install --no-install-recommends \
        libxrender1 libxtst6 libxi6 &&
    apt-get clean -y &&
    rm -rf /var/lib/apt/lists/* /tmp/library-scripts
