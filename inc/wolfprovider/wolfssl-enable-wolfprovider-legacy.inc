do_install_append() {
    install -d ${D}${sysconfdir}/wolfssl
    echo "0" > ${D}${sysconfdir}/wolfssl/fips-enabled
}
