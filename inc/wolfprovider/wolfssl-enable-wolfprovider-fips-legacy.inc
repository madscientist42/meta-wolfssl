# commercial bundle missing stamp-h.in required by automake with 5.2.1
do_configure_prepend() {
    if [ ! -f ${S}/stamp-h.in ]; then
        touch ${S}/stamp-h.in
    fi
}

do_install_append() {
    install -d ${D}${sysconfdir}/wolfssl
    echo "1" > ${D}${sysconfdir}/wolfssl/fips-enabled
}
