# domain name: google.com
from tld import get_tld


def get_domain_name(url):
    return get_tld(url)

