# Define out special cases for config controlled by the PR stored in it.
EXTRA_OECONF:append - " \
    ${@bb.utils.contains_any('PR', 'commercial.fips fipsReady', '${@bb.utils.contains('PR', 'commercial.fips', '--enable-engine=fips-v5', '--enable-engine=fips-ready', d)}', '--enable-engine=no-fips', d)}
    "

