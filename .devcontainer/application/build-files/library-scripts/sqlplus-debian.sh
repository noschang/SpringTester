#!/usr/bin/env bash

set -e

echo "Installing sqlplus dependencies" &&
    apt-get update &&
    export DEBIAN_FRONTEND=noninteractive &&
    apt-get -y install --no-install-recommends \
        glibc-doc libaio1

echo "Installing sqlplus"

CURR_DIR="$(pwd)"
TMP_DIR="/tmp/sqlplus-setup"
INSTALL_DIR="/opt/oracle"

sudo mkdir -p "${TMP_DIR}"
sudo chmod 777 "${TMP_DIR}"

cd "${TMP_DIR}"

wget https://download.oracle.com/otn_software/linux/instantclient/211000/instantclient-basic-linux.x64-21.1.0.0.0.zip
wget https://download.oracle.com/otn_software/linux/instantclient/211000/instantclient-sqlplus-linux.x64-21.1.0.0.0.zip

sudo mkdir -p "${INSTALL_DIR}"

sudo unzip instantclient-basic-linux.x64-21.1.0.0.0.zip -d "${INSTALL_DIR}"
sudo unzip instantclient-sqlplus-linux.x64-21.1.0.0.0.zip -d "${INSTALL_DIR}"

INSTALL_DIR="${INSTALL_DIR}/instantclient_21_1"

cat << EOF > /usr/bin/sqlplus
#!/bin/bash
export LD_LIBRARY_PATH="${INSTALL_DIR}"
${INSTALL_DIR}/sqlplus "\$@"
EOF

chmod 755 /usr/bin/sqlplus

rm -Rf "${TMP_DIR}"

cd "${CURR_DIR}"
