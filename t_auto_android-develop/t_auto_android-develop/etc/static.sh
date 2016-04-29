#!/bin/sh
ifconfig | grep netmask
curl "https://api.ipify.org/?format=txt"
echo
php -S 0:9999 -t $(dirname "$0")/../app/src/debug/assets/selftest