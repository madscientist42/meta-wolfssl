# Configuration to enable wolfprovider FIPS support in wolfssl
# To enable debug add `--enable-debug --enable-keylog-export` to EXTRA_OECONF

EXTRA_OECONF += " --enable-fips=v5 --enable-opensslcoexist"
TARGET_CFLAGS += " -DWOLFSSL_OLD_OID_SUM -DWOLFSSL_DH_EXTRA"

# Use a marker file to signal we are a FIPS build
WOLFSSL_ISFIPS = "1"

include inc/wolfprovider/wolfssl-enable-wolfprovider-fips-${WOLFSSL_OVERRIDE_MODE}.inc
