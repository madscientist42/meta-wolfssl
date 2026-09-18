# Handle cleanup.  At least one tool that is part of the process gets into ${bindir} that needs 
# explicit removal as it does NOTHING for the target except eat storage space...
remove_superfluous_apps() {
    rm -f ${D}${bindir}/wolfssl-config
}
do_install[postfuncs] += "remove_superfluous_apps"

# Handle the install step for the wolfcrypttest stuff - It keys off of a virtual package within wolfssl.
install_wolfcrypttest_extras() {
    bbplain "Installing wolfCrypt Tests..."
    if [ ! -x "${B}/wolfcrypt/test/.libs/testwolfcrypt" ]; then
        bbwarn "wolfCrypt test binary missing at ${B}/wolfcrypt/test/.libs/testwolfcrypt"
    else
        install -Dm0755 "${B}/wolfcrypt/test/.libs/testwolfcrypt" "${D}${bindir}/wolfcryptest"
        bbplain "Installed."
    fi

    bbplain "Installing wolfCrypt Benchmarks..."
    if [ ! -x "${B}/wolfcrypt/benchmark/.libs/benchmark" ]; then
        bbwarn "wolfCrypt benchmark binary missing at ${B}/wolfcrypt/benchmark/.libs/benchmark"
    else
        install -Dm0755 "${B}/wolfcrypt/benchmark/.libs/benchmark" "${D}${bindir}/wolfcryptbenchmark"
        bbplain "Installed."
    fi
}
do_install[postfuncs] += "${@bb.utils.contains('WOLFSSL_PACKAGES', 'wolfcrypttest', 'install_wolfcrypttest_extras', '', d)}"
