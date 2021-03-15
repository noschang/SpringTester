#!/bin/sh

set -e

EXTENSIONS_FOLDER="/extensions/vscode-server/extensions"

LOMBOK_EXTENSION_BASENAME="gabrielbb.vscode-lombok"
LOMBOK_EXTENSION_VERSION="1.0.1"

LOMBOK_EXTENSION_NAME="${LOMBOK_EXTENSION_BASENAME}-${LOMBOK_EXTENSION_VERSION}"
LOMBOK_EXTENSION_FOLDER="${EXTENSIONS_FOLDER}/${LOMBOK_EXTENSION_NAME}"

PATCHED_EXTENSION_FOLDER="/startup/lombok-fix"
PATCHED_EXTENSION_TAR="${PATCHED_EXTENSION_FOLDER}/${LOMBOK_EXTENSION_NAME}.tar.gz"

if [ ! -d "${LOMBOK_EXTENSION_FOLDER}" ]; then
    echo "Lombok extension not found, installing patched version now..."

    mkdir -p "${LOMBOK_EXTENSION_FOLDER}"
    chmod 755 "${LOMBOK_EXTENSION_FOLDER}"

    tar -vxzf "${PATCHED_EXTENSION_TAR}" -C "${LOMBOK_EXTENSION_FOLDER}"
else
    echo "Lombok extension found, skipping install..."
fi
