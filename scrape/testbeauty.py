import requests
from bs4 import BeautifulSoup
import re
# Will return /ShowRatings.jsp?tid=2056294
def getRateMyProfessorLink(name):
    url = "http://www.ratemyprofessors.com/search.jsp?query=" + name
    page = requests.get(url)
    soup = BeautifulSoup(page.text,'html.parser')
    for container in soup.find_all('li', attrs={'class': 'listing PROFESSOR'}):
        if container.findAll("span",{'class': 'sub'})[0].text.startswith("University of California Santa Cruz"):
            #To print out the name of the professor -> print container.findAll("span",{'class': 'main'})[0].text
            return container.a["href"]
    page.close()

# Will return a dictionary {'rating': '2.1', 'take_again': '60%'}
def getRateMyProfessorInfo(name):
    infoDict = {}
    url = "http://www.ratemyprofessors.com" + getRateMyProfessorLink(name)
    page = requests.get(url)
    soup = BeautifulSoup(page.text,'html.parser')
    container = soup.find('div', attrs={'class': 'left-breakdown'})
    infoDict["rating"] = container.findAll("div",{'class': 'breakdown-container quality'})[0].div.div.text.encode('ascii', 'ignore')
    containerTakeAgain = container.findAll("div",{'class': re.compile('breakdown-section takeAgain *')})[0]
    infoDict["take_again"] = containerTakeAgain.findAll("div",{'class': 'grade'})[0].text.strip().encode('ascii', 'ignore')   
    return infoDict

# Will return a dictionary. The key is a the class name ex. CMPS 115. Value is a list of tuples. [(id, grade),(id,grade) .....]
# cmps115 of the dictionary will return [(30441532, u'A'), (30245678, u'N/A'), (29105384, u'N/A'), (29104060, u'Not sure yet'), (28476067, u'A'), (27815808, u'A+'), (27639590, u'A'), (26803217, u'B-'), (25477632, u'N/A'), (25452777, u'Not sure yet')]
# Otherwise for everyclass defaultdict(<type 'list'>, {u'CMPS183': [(29679413, u'N/A'), (29584187, u'Not sure yet'), (26192106, u'A+')], u'CMPS101': [(26638402, u'WD'), (25733126, u'N/A'), (25480303, u'N/A'), (25472772, u'N/A'),(25381229, u'N/A')], u'CS101': [(25378308, u'N/A')], u'CMPS115': [(30441532, u'A'), (30245678, u'N/A'), (29105384, u'N/A'), (29104060, u'Not sure yet'), (28476067, u'A'), (27815808, u'A+'), (27639590, u'A'), (26803217, u'B-'), (25477632, u'N/A'), (25452777, u'Not sure yet')], u'CMPS5P': [(26126365, u'Not sure yet')]})
def getRateMyProfessorComments(name):
    #2056294
    #get
    from collections import defaultdict
    dataSet = defaultdict(list)
    url = "http://www.ratemyprofessors.com/paginate/professors/ratings?tid=" + getRateMyProfessorLink(name)[21:]
    r = requests.get(url)
    tempId = -1
    tempGrade = "l"
    tempClass = "CRAFI"
    for a in r.json()["ratings"]:
        tempId = a["id"]
        tempClass = a["rClass"]
        tempGrade = a["teacherGrade"]
        dataSet[tempClass].append((tempId,tempGrade))
    print dataSet


# print getRateMyProfessorLink("Jullig Richard")
print getRateMyProfessorInfo("Patrick Tantalo")


# getRateMyProfessorComments("Jullig Richard")

# tup = {"CMPS115" : [(43443,"A"), (33322,"B")]}
# tup["CMPS115"].append((4443,"C+"))

# for d in tup["CMPS115"]:
#     print d[1]