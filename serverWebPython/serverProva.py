#!/usr/bin/env python

from http.server import BaseHTTPRequestHandler, HTTPServer
from pprint import pprint
import os
from os import curdir, sep
import cgi
import base64
import cv2
import sys
import json


# HTTPRequestHandler class
class testHTTPServer_RequestHandler(BaseHTTPRequestHandler):

    def do_POST(self):
        form = cgi.FieldStorage(
            fp=self.rfile,
            headers=self.headers,
            environ={
                'REQUEST_METHOD': 'POST',
                'CONTENT_TYPE': self.headers['Content-Type'],
            }
        )
        print("responding to client...")
        if form['photo'].value == "prova":
            print("ciao")
            import time
            time.sleep(5)
            self.send_response(200)
            self.end_headers()
            self.wfile.write(str(time.time()).encode('utf-8'))
        else:
            # l'applicazione dovrebbe passare in POST un immagine compressa e codificata in base64
            photoEnc = form['photo'].value

            fh = open("imageToMatch.jpg", "wb")
            fh.write(base64.b64decode(photoEnc))
            fh.close()

            # modifica dell'immagine per consentire al modello di processarla in modo corretto (identificare e ritagliare la scatola)
            
            #funzione che identifica il medicinale
            medName = {"name":match("imageToMatch.jpg")}

            # logica della funzione in cui il modello dovrebbe processare i dati e restituire un valore

            self.send_response(200)
            self.end_headers()
            pprint(form)
            # funzione per scrivere il risulatao da restituire
            response=json.dumps(medName)

            self.wfile.write(response.encode('utf-8'))
        
        print("response sended...")
        return

# Inizializzazione del rilevatore SIFT
sift = cv2.xfeatures2d.SIFT_create()

MIN_MATCH_COUNT = 75
PATH = "./img"
def match(img):

    maxMatch = MIN_MATCH_COUNT
    medName = "0"
    dirs = os.listdir(PATH)
    img1 = readImg(img)
    des1=detector(img1)
    for dir in dirs:
        count = 0
        files = os.listdir(os.path.join(PATH, dir))
        for file in files:
            img2= readImg(os.path.join(PATH, dir, file))
            des2=detector(img2)
            count = matcher(des1, des2)
            if count > maxMatch:
                maxMatch = count
                medName = dir
    return medName


def readImg(path):
    img = cv2.imread(path)
    img = cv2.resize(img, None, fx=1 / 3, fy=1 / 3)
    return img


def detector(img):

    # utilizzando SIFT si trovano i keypoint e i descriptors
    _, des = sift.detectAndCompute(img, None)
    return des


def matcher(des1, des2):


    FLANN_INDEX_KDTREE = 0
    index_params = dict(algorithm=FLANN_INDEX_KDTREE, trees=5)
    search_params = dict(checks=50)

    flann = cv2.FlannBasedMatcher(index_params, search_params)

    matches = flann.knnMatch(des1, des2, k=2)

    good = []
    for m, n in matches:
        if m.distance < 0.7 * n.distance:
            good.append(m)
    return len(good)


# funzione per avviare il server e metterlo in ascoldo per rischieste
def run():
    print('starting server...')

    # Server settings
    server_address = ('10.10.21.29', 8081)
    httpd = HTTPServer(server_address, testHTTPServer_RequestHandler)
    print('running server...')
    httpd.serve_forever()



if __name__ == '__main__':
    run()
