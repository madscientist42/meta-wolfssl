do_install:append() {
    install -m 0755 -d "${WOLFCRYPT_PY_TEST_TARGET_DIR}"
    cp -r ${WOLFCRYPT_PY_TEST_DIR}/* ${WOLFCRYPT_PY_TEST_TARGET_DIR}
}

FILES:wolfcrypt-py += "${WOLFCRYPT_PY_DIR}/*"
