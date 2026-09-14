do_install_append() {
    install -m 0755 -d "${WOLFSSL_PY_TEST_TARGET_DIR}"
    cp -r ${WOLFSSL_PY_TEST_DIR}/* ${WOLFSSL_PY_TEST_TARGET_DIR}
}

FILES_${PN} += "${WOLFSSL_PY_DIR}/*"
