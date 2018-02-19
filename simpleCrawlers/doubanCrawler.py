
import re
import urllib
import urllib.request
import json


keyword = "惊悚"
nums = 120
movies = []
headers = {
	'User-Agent':'Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36',
}
url = "https://movie.douban.com/j/new_search_subjects?sort=T&range=0,10&tags=%E6%81%90%E6%80%96&start=0"


def replaceUrlStr(pat,str1,orig):
     p = re.compile(pat)
     global url
     url = p.sub(str1,orig)

def replaceKeyWord(kw):
     kw = urllib.parse.quote(kw)
     replaceUrlStr("tags=.*&start","tags="+kw+"&start",url)
     

def dataFromUrl(url):
     request = urllib.request.Request(url=url,headers=headers)
     resp = urllib.request.urlopen(request,timeout = 40)
     result = resp.read()
     return result

def moreMovie():
     for i in range(1,round(nums/10)):
               print("Crawling........URL: "+url)
               data = dataFromUrl(url).decode("utf-8")
               tempMovies = json.loads(data)["data"]
               global movies  
               movies += tempMovies
               replaceUrlStr("start=.*","start="+str(i*10),url)

def writeToHtml():
     mf = open("horribleMovies.html","w")
     print("writing.............")
     head = """
     <!DOCTYPE html>
     <html>
     <head>
     <title>horrible movies....</title>
     <style>
     div{
     margin-left:35%;
     }
     </style>
     </head>
     <body>
     """
     mf.write(head)
     
     for item in movies:
          mf.write("<div>")
          mf.write("<img src=\""+item["cover"]+"\"/> <br/>")
          mf.write("<strong>"+item["title"]+"</strong> <br/>")
          mf.write("<h2>rate:"+item["rate"]+"</h2>")
          mf.write("</div>")
          mf.write("<hr/>")
     foot = """
     </body>
     </html>
     """
     mf.write(foot)
     mf.close()
     
     
              
def start():
     replaceKeyWord(keyword)
     moreMovie()
     movies.sort(key=lambda m:m["rate"],reverse=True)
     writeToHtml()

start()



