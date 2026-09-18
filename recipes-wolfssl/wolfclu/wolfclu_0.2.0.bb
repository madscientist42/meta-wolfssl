SUMMARY = "wolfCLU is a command line utility with wolfSSL"
DESCRIPTION = "wolfCLU is a lightweight command line utility written in C and \
               optimized for embedded and RTOS environments."
HOMEPAGE = "https://www.wolfssl.com/products/wolfclu"
BUGTRACKER = "https://github.com/wolfssl/wolfclu/issues"
SECTION = "bin"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263"

DEPENDS += "virtual/wolfssl"
PROVIDES += "wolfclu"
python __anonymous() {
    wolfssl_varSet(d, 'RPROVIDES', '${PN}', 'wolfclu')
    wolfssl_varAppend(d, 'RDEPENDS', '${PN}', ' virtual-wolfssl')
}

# Handle, more simply, what needs to be done here.  We need to run autogen.sh here
# and the original recipe work was doing a convoluted thing here that misunderstood
# that there was other ways at doing this that work as well or better (Depending
# on your view there...prepend/append only work if you match the scripting environment,
# (Python or BASH...)) than anything fancy they did there.
run_autogen() {
    cd ${S}
    ./autogen.sh
}
do_configure[prepend] += "run_autogen"

SRC_URI = "git://github.com/wolfssl/wolfclu.git;nobranch=1;protocol=https;rev=ceefc9953aec4ccce6f921df67903101de28a3a0"

python () {
    if d.getVar('WOLFCLU_TYPE', False):
        return
    if d.getVar('UNPACKDIR', False):
        d.setVar('S', '${UNPACKDIR}/${BP}')
    else:
        d.setVar('S', '${WORKDIR}/git')
}

inherit autotools pkgconfig wolfssl-helper wolfssl-compatibility

EXTRA_OECONF = "--with-wolfssl=${STAGING_EXECPREFIXDIR}"

BBCLASSEXTEND += "native nativesdk"

# Add reproducible build flags
export CFLAGS += ' -g0 -O2 -ffile-prefix-map=${WORKDIR}=.'
export CXXFLAGS += ' -g0 -O2 -ffile-prefix-map=${WORKDIR}=.'
export LDFLAGS += ' -Wl,--build-id=none'

# Ensure consistent locale
export LC_ALL = "C"

# Now add an install helper...we're only building out a command line tool even though it's all the same sources.
do_install() {
    mkdir -p ${D}/usr/bin
    install -m 0755 wolfssl ${D}/usr/bin
}

