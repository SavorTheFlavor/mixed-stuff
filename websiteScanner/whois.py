# find the owner of a website..
import os


def get_owner_info(domain):
    command = 'whois '+domain
    process = os.popen(command)
    info = str(process.read())
    return info

