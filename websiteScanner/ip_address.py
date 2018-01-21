import os
import re


def get_ip_address(domain):
    command = "nslookup "+domain
    process = os.popen(command)
    res = str(process.read())
    ipaddr = re.search(r'(\d{1,3}\.){3}\d{1,3}', res).group(0)
    return ipaddr
