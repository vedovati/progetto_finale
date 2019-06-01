#!/usr/bin/env python
 
from http.server import BaseHTTPRequestHandler, HTTPServer
from os import curdir, sep
import cgi
 
# HTTPRequestHandler class
class testHTTPServer_RequestHandler(BaseHTTPRequestHandler):

    def do_POST(self):
        ctype, pdict = cgi.parse_header(self.headers.getheader('content-type'))
        postvars = cgi.parse_multipart(self.rfile, pdict)
        
        # form è una lista delle variabili POST
        form = cgi.FieldStorage(
            fp=self.rfile, 
            headers=self.headers,
            environ={'REQUEST_METHOD':'POST',
                        'CONTENT_TYPE':self.headers['Content-Type'],
        })
        
        # l'applicazione dovrebbe passare in POST un immagine compressa e codificata in base64

        # modifica dell'immagine per consentire al modello di processarla

        # logica della funzione in cui il modello dovrebbe processare i dati e restituire un valore

        self.send_response(200)
        self.end_headers()

        # funzione per scrivere il risulatao da restituire
        self.wfile.write()
        return	


#funzione per avviare il server e metterlo in ascoldo per rischieste
def run():
  print('starting server...')
 
  # Server settings
  # indirizzo ip = 127.0.0.1 (locale), porta = 8081
  server_address = ('127.0.0.1', 8081)
  httpd = HTTPServer(server_address, testHTTPServer_RequestHandler)
  print('running server...')
  httpd.serve_forever()
 

run()