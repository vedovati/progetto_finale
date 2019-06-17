#!/usr/bin/env python

from http.server import BaseHTTPRequestHandler, HTTPServer
from pprint import pprint
from os import curdir, sep
import cgi
import base64

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

        # l'applicazione dovrebbe passare in POST un immagine compressa e codificata in base64
        photoEnc = form['photo'].value
        
        fh = open("imageToSave.png", "wb")
        fh.write(base64.b64decode(photoEnc))
        fh.close()

        # modifica dell'immagine per consentire al modello di processarla in modo corretto (identificare e ritagliare la scatola)
        
        #richiamare il knn per identificare il tipo di medicinale

        # logica della funzione in cui il modello dovrebbe processare i dati e restituire un valore

        self.send_response(200)
        self.end_headers()
        pprint(form)
        # funzione per scrivere il risulatao da restituire
        self.wfile.write("wella".encode('utf-8'))
        return


# funzione per avviare il server e metterlo in ascoldo per rischieste
def run():
    print('starting server...')

    # Server settings
    # indirizzo ip = 127.0.0.1 (locale), porta = 8081
    server_address = ('127.0.0.1', 8081)
    httpd = HTTPServer(server_address, testHTTPServer_RequestHandler)
    print('running server...')
    httpd.serve_forever()


run()
