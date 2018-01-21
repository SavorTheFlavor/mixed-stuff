from domain_name import *
from general import *
from ip_address import *
from nmap import *
from robot_txt import *
from whois import *

ROOT_DIR = 'websitesInfo'
create_dir(ROOT_DIR)


def create_report(name, url):
    domain_name = get_domain_name(url)
    ipaddr = get_ip_address(domain_name)
    ports_info = get_opened_ports(ipaddr)
    robots_txt = get_robots_txt(url)
    # whois = get_owner_info(domain_name) # don't have this command yet

    project_dir = ROOT_DIR + '/' + name
    create_dir(project_dir)

    write_file(project_dir+'/domain_name.txt', domain_name)
    write_file(project_dir + '/ipaddr.txt', ipaddr)
    write_file(project_dir + '/ports_info.txt', ports_info)
    write_file(project_dir + '/robots_txt.txt', robots_txt)
    # write_file(project_dir + '/whois.txt', whois)

create_report('baidu', 'http://www.baidu.com')