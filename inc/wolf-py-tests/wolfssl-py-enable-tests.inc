# Enable test directory installation for wolfssl-py
#
# Declarative on purpose: this inc is included by wolfssl_conditional_require()
# from an anonymous function, and python () blocks registered that late never
# run. FILES uses the literal package name because expandKeys() has already run.

WOLFSSL_PY_TEST_DIR = "${S}"
WOLFSSL_PY_DIR = "/home/root/wolf-py-tests/wolfssl-py-test"
WOLFSSL_PY_TEST_TARGET_DIR = "${D}${WOLFSSL_PY_DIR}"

include inc/wolf-py-tests/wolfssl-py-enable-tests-${WOLFSSL_OVERIDE_MODE}.inc

# Python Specific option
export PYTHONDONTWRITEBYTECODE = "1"
