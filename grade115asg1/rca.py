# brew install pkg-config poppler
import pdftotext
import re
import sys

def getWords(text):
    return re.compile('\w+').findall(text)


myDict = {
    'published': False,
    'ieee': False,
    'motivated': False,
    'extensive': False,
    'waterfall': False,
    'analysis': False,
    'preliminary': False,
    'agile': False,
    'apollo': False,

}

if len(sys.argv) < 2:
    print "no file given. \n Type python rca filename"
    sys.exit(0)
# Load your PDF
with open(sys.argv[1], "rb") as f:
    pdf = pdftotext.PDF(f)


# How many pages?
print("Number of Pages: " + str(len(pdf)))

# Iterate over all the pages
for page in pdf:
    for word in getWords(page):
        if word.lower() in myDict:
            myDict[word.lower()] = True


# print myDict


score = 0
for d in myDict:
    if myDict[d] == True:
        score += 1
    else:
        print "Can not find " + d

if myDict["agile"] == True:
    score += 1

# print myDict

print "\n\nThe score for answering all the questions: " + str(score) + "\n\n"
