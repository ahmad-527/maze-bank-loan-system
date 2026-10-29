#!/bin/sh
set -eu
cd -- "$(dirname -- "$0")"
if [ -n "${JAVA_HOME:-}" ] && [ -x "$JAVA_HOME/bin/java" ]; then
  MAZEBANK_JAVA="$JAVA_HOME/bin/java"
else
  MAZEBANK_JAVA=java
fi
if ! "$MAZEBANK_JAVA" -version >/dev/null 2>&1; then
  echo 'Java 17 or newer is required. Install a desktop runtime from https://adoptium.net/' >&2
  exit 1
fi
if [ ! -f maze-bank-demo-1.0.0-preview.jar ]; then
  echo 'Extract the complete release ZIP before running this script.' >&2
  exit 1
fi
exec "$MAZEBANK_JAVA" -jar maze-bank-demo-1.0.0-preview.jar
