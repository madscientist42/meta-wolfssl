SUMMARY = "wolfSSL FIPS 140-3 Validated Cryptography"
DESCRIPTION = "wolfSSL is a lightweight SSL/TLS library with FIPS 140-3 validated cryptography module. This recipe provides the FIPS-validated version of wolfSSL."
HOMEPAGE = "https://www.wolfssl.com/products/wolfssl-fips/"
BUGTRACKER = "https://github.com/wolfssl/wolfssl/issues"
SECTION = "libs"

# Commercial/FIPS license - Update when using commercial bundle
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://${WOLFSSL_LICENSE};md5=${WOLFSSL_LICENSE_MD5}"

DEPENDS += "util-linux-native"

# We can provide for any of them.  Right now we're going to default to this being
# always so and then handle it as an .inc that does the right things based off
# of some WOLFSSL_COMPAT settings in the layer so it does the right things ALWAYS.
PROVIDES += "virtual/wolfssl virtual/libssl virtual/openssl"

# Handle the overrides for RPROVIDES on this recipe so it's consistent with everything
python __anonymous() {
    wolfssl_varSet(d, 'RPROVIDES', '${PN}', 'virtual-wolfssl virtual-libssl virtual-openssl')
}

inherit autotools pkgconfig wolfssl-helper wolfssl-commercial wolfssl-fips-helper wolfssl-compatibility

# Lower preference so regular wolfssl is default
# Users must explicitly set PREFERRED_PROVIDER_virtual/wolfssl = "wolfssl-fips"
DEFAULT_PREFERENCE = "-1"

# FIPS bundle source - expects commercial bundle in files/ directory
# User must set these in local.conf:
#   WOLFSSL_VERSION = "x.x.x"
#   WOLFSSL_SRC = "wolfssl-x.x.x-commercial-fips-linux"
#   WOLFSSL_SRC_SHA = "sha256sum of bundle"
#   WOLFSSL_SRC_PASS = "password for bundle"
#   WOLFSSL_LICENSE = "${S}/LICENSING"  (or path to license file relative to source code)
#   WOLFSSL_LICENSE_MD5 = "md5sum of license"
#   FIPS_HASH = "hash value after first build" (for FIPS validation)

# Commercial bundle configuration
# Users can set WOLFSSL_SRC_DIR in local.conf to specify bundle location
# Users can set WOLFSSL_SRC_DIRECTORY in local.conf to point directly to extracted source
WOLFSSL_SRC_DIR ?= "${@os.path.dirname(d.getVar('FILE', True))}/commercial/files"
WOLFSSL_SRC_DIRECTORY ?= ""
WOLFSSL_BUNDLE_FILE ?= ""
WOLFSSL_BUNDLE_GCS_URI ?= ""
WOLFSSL_BUNDLE_GCS_TOOL ?= ""

# Map to commercial class variables
COMMERCIAL_BUNDLE_DIR = "${WOLFSSL_SRC_DIR}"
COMMERCIAL_BUNDLE_NAME = "${WOLFSSL_SRC}"
COMMERCIAL_BUNDLE_FILE = "${WOLFSSL_BUNDLE_FILE}"
COMMERCIAL_BUNDLE_PASS = "${WOLFSSL_SRC_PASS}"
COMMERCIAL_BUNDLE_SHA = "${WOLFSSL_SRC_SHA}"
COMMERCIAL_BUNDLE_TARGET = "${WORKDIR}"
COMMERCIAL_BUNDLE_GCS_URI = "${WOLFSSL_BUNDLE_GCS_URI}"
COMMERCIAL_BUNDLE_GCS_TOOL = "${@d.getVar('WOLFSSL_BUNDLE_GCS_TOOL') or 'auto'}"
COMMERCIAL_BUNDLE_SRC_DIR = "${WOLFSSL_SRC_DIRECTORY}"

# Use helper functions from wolfssl-commercial.bbclass for conditional configuration
SRC_URI = "${@get_commercial_src_uri(d)}"
S = "${@get_commercial_source_dir(d)}"

# Grab the knobs settings for our common configs for wolfclu, etc.  This is opposed to
# the convoluted contortions that was done for the varying packages modes that we used
# do.
require wolfssl-packageconfigs.inc
require wolfssl-install-helpers.inc


# Optional: switch to GCS/tarball flow (gs:// URI) when set
require inc/wolfssl-fips/wolfssl-commercial-gcs.inc

# Skip the package check for wolfssl-fips itself (it's the base library)
deltask do_wolfssl_check_package

# Enable native/nativesdk variants when FIPS is configured
BBCLASSEXTEND = "${@'native nativesdk' if (d.getVar('WOLFSSL_SRC') or '').strip() else ''}"

# FIPS-specific configuration
# Note: FIPS hash is handled by wolfssl-fips-helper.bbclass
TARGET_CFLAGS += "-DFP_MAX_BITS=16384"
EXTRA_OECONF += " \
    --enable-fips=v6 \
    --enable-reproducible-build \
    --enable-smallstack \
    --enable-sp-math-all \
    --disable-sp \
"
