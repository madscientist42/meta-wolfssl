#  Handle automagic package grouping off of the WOLFSSL_PACKAGES global selector variable
DESCRIPTION = "Metapackage for including the specified packages declared in WOLFSSL_PACKAGES"

inherit packagegroup

# Set aside our group list out of the control flags from our layer config...
PACKAGE_LIST = "${WOLFSSL_PACKAGES}"

# Remove any virtual packages that're just a knob turn on for WolfSSL itself.
PACKAGE_LIST:remove = "wolfcrypttest"

# Add the implicit for everyone to actually have in the image...namely whatever 
# virtual-wolfssl the user specified.  This commpletes the behavior for our
# RDEPENDS list for the group.  Use the packagegroup if you want/need wolfssl
# in your image on *install*.
PACKAGE_LIST += "virtual-wolfssl"

# Now declare the RDEPENDS for this package so it forces installs properly.
# There will at least be virtual-wolfssl to install as a package out of this
# metapackage for the image.
require wolfssl-packagegroup-${WOLFSSL_OVERRIDE_MODE}.inc
