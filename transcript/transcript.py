import pdftotext
#https://github.com/jalan/pdftotext

import re
import sys

def getWords(text):
    return re.compile('\w+').findall(text)



with open("unofficial.pdf", "rb") as f:
    pdf = pdftotext.PDF(f)


# How many pages?
print("Number of Pages: " + str(len(pdf)))

# Iterate over all the pages
for page in pdf:
    print page
    # for word in getWords(page):
    #     if word.lower() in myDict:
    #         myDict[word.lower()] = True


# print myDict


# score = 0
# for d in myDict:
#     if myDict[d] == True:
#         score += 1
#     else:
#         print "Can not find " + d

# if myDict["agile"] == True:
#     score += 1

# # print myDict

# print "\n\nThe score for answering all the questions: " + str(score) + "\n\n"