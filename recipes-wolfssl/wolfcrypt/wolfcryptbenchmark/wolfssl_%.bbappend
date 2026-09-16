# Extend the search path to here first...we're intercepting a few config items.
FILESEXTRAPATHS:prepend := "${THISDIR}:"

# Don't need the package check function on a pure GPL build...
DO_DELTASK = "1"

require wolfcryptbenchmark.inc