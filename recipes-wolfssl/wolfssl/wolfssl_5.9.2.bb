SUMMARY = "wolfSSL Lightweight Embedded SSL/TLS Library"
DESCRIPTION = "wolfSSL is a lightweight SSL/TLS library written in C and optimized for embedded and RTOS environments. It supports a full TLS client and server, up to TLS 1.3."
HOMEPAGE = "https://www.wolfssl.com/products/wolfssl/"
BUGTRACKER = "https://github.com/wolfssl/wolfssl/issues"
SECTION = "libs"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"
DEPENDS += "util-linux-native"

PROVIDES += "virtual/wolfssl"
RPROVIDES:${PN} += "virtual-wolfssl"

SRC_URI = "git://github.com/wolfssl/wolfssl.git;nobranch=1;protocol=https;rev=ac01707f552c611fbd135cc723b2682b3e7f80f2"

python () {
    if d.getVar('WOLFSSL_TYPE', False):
        return
    if d.getVar('UNPACKDIR', False):
        d.setVar('S', '${UNPACKDIR}/${BP}')
    else:
        d.setVar('S', '${WORKDIR}/git')
}

inherit autotools pkgconfig wolfssl-helper wolfssl-compatibility

# Handle the overrides for RPROVIDES on this recipe so it's consistent with everything
python __anonymous() {
    wolfssl_varSet(d, 'RPROVIDES', '${PN}', 'virtual-wolfssl')
}

# Grab the knobs settings for our common configs for wolfclu, etc.  This is opposed to
# the convoluted contortions that was done for the varying packages modes that we used
# do.
require wolfssl-packageconfigs.inc
require wolfssl-install-helpers.inc


# Skip the package check for wolfssl itself (it's the base library)
deltask do_wolfssl_check_package

BBCLASSEXTEND = "native nativesdk"
EXTRA_OECONF += "--enable-reproducible-build"
