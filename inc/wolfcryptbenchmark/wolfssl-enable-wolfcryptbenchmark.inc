# Configuration to enable wolfcryptbenchmark support in wolfssl
EXTRA_OECONF += "--enable-crypttests"

include inc/wolfcryptbenchmark/wolfssl-enable-wolfcryptbenchmark-${WOLFSSL_OVERIDE_MODE}.inc

TARGET_CFLAGS += "-DUSE_CERT_BUFFERS_2048 -DUSE_CERT_BUFFERS_256 -DBENCH_EMBEDDED"
