import requests
import re

class taobaoCrawler:
     
     def getHtmlText(self,url):
          try:
               r = requests.get(url)
               r.raise_for_status()
               r.encoding = r.apparent_encoding
               return r.text
          except:
               return ""

     def parsePage(self,glist,html):
          try:
               plt=re.findall(r'\"view_price\":\"[\d.]*\"',html)
               tlt=re.findall(r'\"raw_title\":\".*?\"',html)
               for i in range(len(plt)):
                    price=eval(plt[i].split(":")[1])
                    title=eval(tlt[i].split(":")[1])
                    glist.append([title,price])
          except:
               print("!")

     def printGoodInfo(self,glist):
          tplt = "{:4}\t{:8}\t{:16}"
          print(tplt.format("序号","价格","商品名称"))
          count=0
          for g in glist:
               count+=1
               print(tplt.format(str(count),g[1],g[0]))

     def start(self):
          goods = "零食"
          depth = 3
          start_url = 'https://s.taobao.com/search?q='+goods

          glist=[]

          for i in range(depth):
               try:
                    url = start_url+"&s="+str(i*44)
                    html = self.getHtmlText(url)
                    self.parsePage(glist,html)
               except:
                    continue
          
          self.printGoodInfo(glist)
          
crawler=taobaoCrawler()
crawler.start()
     
     
