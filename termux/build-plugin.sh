#!/data/data/com.termux/files/usr/bin/bash
set -euo pipefail
ROOT="$(CDPATH= cd -- "$(dirname -- "$0")/.." && pwd)"
cd "$ROOT"
JAVA_HOME="${JAVA_HOME:-$(dirname "$(dirname "$(readlink -f "$(command -v java)")")")}" ./gradlew clean test --no-daemon
JAVA_HOME="${JAVA_HOME:-$(dirname "$(dirname "$(readlink -f "$(command -v java)")")")}" ./gradlew :platform-paper:jar --no-daemon
mkdir -p termux/out
cp platform-paper/build/libs/platform-paper-*.jar termux/out/forge-solo-subseason.jar
printf 'Termux artifact: %s\n' "$ROOT/termux/out/forge-solo-subseason.jar"
