# Conditionally configure wolfssl with wolfcrypttest support
# This bbappend checks the WOLFSSL_FEATURES and IMAGE_INSTALL variables
#
# Do this in something resembling an idiomatic manner with Override syntax applied.
# (Bitbake was designed for this and it works cleaner than doing the original thing!)
WOLFCRYPTBENCH_REQUIRE = ""
WOLFCRYPTBENCH_REQUIRE_wolfcrypttest = "inc/wolfcrypttest/wolfssl-enable-wolfcryptbenchmark.inc"
require ${WOLFCRYPTBENCH_REQUIRE}

WOLFCRYPTBENCH_DELTASK = ""
WOLFCRYPTBENCH_DELTASK_wolfcryptbenchmark = "${@bb.utils.contains('DO_DELTASK', '1', 'do_wolfssl_check_package', '', d)}"
deltask ${WOLFCRYPTBENCH_DELTASK}