do_install_append() {
    bbnote "Installing Certs Directory for wolf-py Tests"
    install -m 0755 -d "${WOLFSSL_PY_CERTS_INSTALL_DIR}"
    cp -r ${WOLFSSL_PY_CERTS_SOURCE_DIR}/*.der ${WOLFSSL_PY_CERTS_INSTALL_DIR}
    cp -r ${WOLFSSL_PY_CERTS_SOURCE_DIR}/*.pem ${WOLFSSL_PY_CERTS_INSTALL_DIR}
}

FILES_${PN} += "${WOLFSSL_PY_TEST_CERTS_DIR}/*"
