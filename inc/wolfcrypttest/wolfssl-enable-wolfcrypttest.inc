# Inherit if we've not already.  Doesn't hurt anything other than the parse time...
inherit wolfssl-helper

fuckme

# Configuration to enable wolfcrypttest support in wolfssl
EXTRA_OECONF += "--enable-crypttests"

install_wolfcrypt_binary() {
    bbnote "Installing wolfCrypt Tests"
    if [ ! -x "${B}/wolfcrypt/test/.libs/testwolfcrypt" ]; then
        bbwarn "wolfCrypt test binary missing at ${B}/wolfcrypt/test/.libs/testwolfcrypt"
        return
    fi

    install -Dm0755 "${B}/wolfcrypt/test/.libs/testwolfcrypt" "${D}${bindir}/wolfcrypttest"
}
do_install[postfuncs] += "install_wolfcrypt_binary"

# Set compiler flags accordingly...
TARGET_CFLAGS += "-DUSE_CERT_BUFFERS_2048 -DUSE_CERT_BUFFERS_256 -DWOLFSSL_RSA_KEY_CHECK -DNO_WRITE_TEMP_FILES"
