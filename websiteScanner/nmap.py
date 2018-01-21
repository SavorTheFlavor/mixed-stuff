# scan the opened port on the given host ip
import os


def get_opened_ports(ip, option='-F'):
    command = 'nmap '+option+" "+ip
    print(command)
    process = os.popen(command)
    res = process.read()
    return str(res)


