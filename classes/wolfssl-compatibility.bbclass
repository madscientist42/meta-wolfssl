# wolfSSL Yocto Compatibility Helper Class
# Provides functions to work with both old (underscore) and new (colon) Yocto syntax

def wolfssl_uses_colon_syntax(d):
    """
        Report back if this is a version that needs '_' or ":" separators for override syntax.
        (Original code tries, and fails, to detect this state off of version when it should
        just check the running tab of which we support in either mode or not which is CLEANLY
        and in a Yocto Idiomatic manner, done in the layer config file where policy should live.
    """
    override_mode = d.getVar('WOLFSSL_OVERRIDE_MODE', True)
    if (override_mode and (override_mode == 'modern')):
        return True
    else:
        return False
    

def wolfssl_varAppend(d, base_var, package_name, value):
    """
    Appends a value to a package-specific variable, handling both old and new Yocto syntax.

    Args:
        d: BitBake data store
        base_var: Base variable name (e.g., 'RDEPENDS', 'FILES', 'RRECOMMENDS')
        package_name: Package name (e.g., '${PN}')
        value: Value to append
    """
    import bb

    package_name_expanded = d.expand(package_name)

    if wolfssl_uses_colon_syntax(d):
        var_name = base_var + ':' + package_name_expanded
    else:
        var_name = base_var + '_' + package_name_expanded

    d.appendVar(var_name, value)

def wolfssl_varSet(d, base_var, package_name, value):
    """
    Sets a package-specific variable, handling both old and new Yocto syntax.

    Args:
        d: BitBake data store
        base_var: Base variable name (e.g., 'RDEPENDS', 'FILES', 'RRECOMMENDS')
        package_name: Package name (e.g., '${PN}')
        value: Value to set
    """
    import bb

    package_name_expanded = d.expand(package_name)

    if wolfssl_uses_colon_syntax(d):
        var_name = base_var + ':' + package_name_expanded
    else:
        var_name = base_var + '_' + package_name_expanded

    d.setVar(var_name, value)

def wolfssl_varGet(d, base_var, package_name):
    """
    Gets a package-specific variable, handling both old and new Yocto syntax.

    Args:
        d: BitBake data store
        base_var: Base variable name (e.g., 'RDEPENDS', 'FILES', 'RRECOMMENDS')
        package_name: Package name (e.g., '${PN}')

    Returns:
        Variable value or None
    """
    import bb

    package_name_expanded = d.expand(package_name)

    if wolfssl_uses_colon_syntax(d):
        var_name = base_var + ':' + package_name_expanded
    else:
        var_name = base_var + '_' + package_name_expanded

    return d.getVar(var_name) or d.getVar(var_name, True)

def wolfssl_varPrepend(d, var_name, value):
    """
    Prepends a value to a variable (for things like FILESEXTRAPATHS, PACKAGECONFIG).

    Args:
        d: BitBake data store
        var_name: Variable name (e.g., 'FILESEXTRAPATHS', 'PACKAGECONFIG')
        value: Value to prepend
    """
    d.prependVar(var_name, value)

def wolfssl_varAppendNonOverride(d, var_name, value):
    """
    Appends a value to a variable (for things like PACKAGECONFIG, EXTRA_OECONF).

    Args:
        d: BitBake data store
        var_name: Variable name (e.g., 'PACKAGECONFIG', 'EXTRA_OECONF')
        value: Value to append
    """
    d.appendVar(var_name, value)

def wolfssl_varRemoveNonOverride(d, var_name, value):
    """
    Removes one or more whitespace-delimited tokens from a list-style variable
    (e.g. ERROR_QA, WARN_QA). Edits the datastore directly, so it is version-agnostic
    and needs no colon/underscore override syntax (unlike ERROR_QA:remove / _remove).
    No-op if a token is not present.

    Args:
        d: BitBake data store
        var_name: Variable name (e.g., 'ERROR_QA', 'WARN_QA')
        value: Whitespace-delimited token(s) to remove (e.g., 'patch-status')
    """
    tokens = (d.getVar(var_name) or '').split()
    remove = set(value.split())
    filtered = [t for t in tokens if t not in remove]
    if filtered != tokens:
        d.setVar(var_name, ' '.join(filtered))
