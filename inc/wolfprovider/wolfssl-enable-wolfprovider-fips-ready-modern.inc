# bundle missing stamp-h.in required by automake
do_configure:prepend() {
    if [ ! -f ${S}/stamp-h.in ]; then
        touch ${S}/stamp-h.in
    fi
}

do_install:append() {
    install -d ${D}${sysconfdir}/wolfssl
    echo "1" > ${D}${sysconfdir}/wolfssl/fips-enabled
}
