# Conditionally configure wolfssl with wolfcrypttest support
# This bbappend checks the WOLFSSL_FEATURES and IMAGE_INSTALL variables
#
# Do this in something resembling an idiomatic manner with Override syntax applied.
# (Bitbake was designed for this and it works cleaner than doing the original thing!)
WOLFCRYPTTEST_REQUIRE = ""
WOLFCRYPTTEST_REQUIRE_wolfcrypttest = "inc/wolfcrypttest/wolfssl-enable-wolfcrypttest.inc"
require ${WOLFCRYPTTEST_REQUIRE}

WOLFCRYPTTEST_DELTASK = ""
WOLFCRYPTTEST_DELTASK_wolfcrypttest = "${@bb.utils.contains('DO_DELTASK', '1', 'do_wolfssl_check_package', '', d)}"
deltask ${WOLFCRYPTTEST}