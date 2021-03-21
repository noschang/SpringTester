#!/usr/bin/env bash

set -e

echo "Installing graphic libraries..." &&
    apt-get update &&
    export DEBIAN_FRONTEND=noninteractive &&
    apt-get -y install --no-install-recommends \
        libxrender1 libxtst6 libxi6
