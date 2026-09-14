FILES:${PN} += "${bindir}/wolfcryptbenchmark"

do_install:append() {
    bbnote "Installing wolfCrypt Benchmarks"
    if [ ! -x "${B}/wolfcrypt/benchmark/.libs/benchmark" ]; then
        bbwarn "wolfCrypt benchmark binary missing at ${B}/wolfcrypt/benchmark/.libs/benchmark"
        return
    fi

    install -Dm0755 "${B}/wolfcrypt/benchmark/.libs/benchmark" "${D}${bindir}/wolfcryptbenchmark"
}
