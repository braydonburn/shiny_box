import glob
import os
import string
import operator
from stemming.porter2 import stem
from collections import OrderedDict


def parse_coll(inputpath, stopWordsList):
    coll = {}
    os.chdir(inputpath)
    for file_ in glob.glob("*.xml"):
        curr_doc = {}
        start_end = False
        for line in open(file_):
            line = line.strip()
            if(start_end == False):
                if line.startswith("<newsitem "):
                    for part in line.split():
                        # Get the itemid/document id and set it to docid
                        if part.startswith("itemid="):
                            docid = part.split("=")[1].split("\"")[1]
                            break
                # End check when the line starts with text tag
                if line.startswith("<text>"):
                    start_end = True
            # Break when the text tag is closed
            elif line.startswith("</text>"):
                break
            else:
                line = line.replace("<p>", "").replace("</p>", "").translate(None, string.digits).translate(
                    string.maketrans(string.punctuation, ' '*len(string.punctuation))).replace("\\s+", " ")
                # Increment the term value
                addTerm(line, curr_doc, stopWordsList)
        # If there is no currdoc id, set one
        if curr_doc is not None:
            coll[docid] = curr_doc
    return coll

#Function to pull common words from a text file and return them as an array
def stopWords(inputpath):
    text_file = open(inputpath, "r")
    lines = text_file.read().split(',')
    text_file.close()
    return lines

#Create, change to lowercase, stem, remove <3 length words and increment terms
def addTerm(line, curr_doc, stopWordsList):
    for term in line.split():
        term = term.lower()
        term = stem(term)
        if(term not in stopWordsList and len(term) > 3):
            try:
                curr_doc[term] += 1
            except KeyError:
                curr_doc[term] = 1

#Print found data in descending order.
def print_ID(any):
    for doc in coll.items():
        print "Document itemid: %s contains %d terms and %s total words" % (doc[0], len(doc[1]), sum(doc[1].values()))
        doc_sorted = OrderedDict(sorted(doc[1].items(), key=lambda x: x[1], reverse=True))
        for k, v in doc_sorted.items():

            print "%s: %s" % (k, v)
        print "\n"


if __name__ == '__main__':
    import sys

    if len(sys.argv) != 3:
        # Use: python Solution.py RCv1/ common-english-words.txt
        sys.stderr.write("USAGE: %s <coll-file> <stop-words>\n" % sys.argv[0])
        sys.exit()
    stopWordsList = stopWords(sys.argv[2])
    coll = parse_coll(sys.argv[1], stopWordsList)
    # Coll is a list of all documents that have been read in
    # Doc is the individual document inside the coll as an array with 0 being the id and 1 being the words with a count
    print_ID(coll)
