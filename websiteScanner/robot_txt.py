import urllib.request
import io


def get_robots_txt(url):
    if not url.endswith('/'):
        url = url + '/'
    url = url + 'robots.txt'
    try:
        req = urllib.request.urlopen(url)
    except:
        return 'no robots.txt in this website!'
    data = io.TextIOWrapper(req,'utf-8')
    return data.read()
