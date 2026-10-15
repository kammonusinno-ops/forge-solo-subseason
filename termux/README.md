# Termux test-server layout

This project is a Paper plugin, not a standalone Android app. Termux should run the Paper test server with Java 21+ and load only the built plugin JAR.

## On Termux

```sh
pkg update
pkg install openjdk-21 git unzip
mkdir -p "$HOME/mc-test/plugins"
cp forge-solo-subseason.jar "$HOME/mc-test/plugins/"
cd "$HOME/mc-test"
java -Xms1G -Xmx2G -jar paper.jar --nogui
```

Accept the EULA in `eula.txt` only after reviewing it. Keep `online-mode=true` for a connected test server. Do not put database passwords in this directory.

## From this repository

On a Linux build machine run `./termux/build-plugin.sh`. It runs the Java 21 tests, builds the self-contained Paper JAR, and copies only the plugin to `termux/out/forge-solo-subseason.jar`. Transfer that one file to Termux with `adb`, `scp`, or a private file transfer, then restart Paper.

Example transfer:

```sh
adb push termux/out/forge-solo-subseason.jar /data/data/com.termux/files/home/mc-test/plugins/
```

The first server boot creates `plugins/ForgeSoloSubseason/config.yml`, `class_change.yml`, `ledger.properties`, `classes.properties`, and `base-defenses.properties`.

## Test checklist

Use a private test world. Verify `/wallet`, `/pay`, `/class hall`, `/class choose`, `/skills`, `/baseguard set 8`, `/baseguard status`, and `/baseguard off`. Log out the owner, attempt a break/place inside the radius, then log the owner back in and verify normal access resumes.
