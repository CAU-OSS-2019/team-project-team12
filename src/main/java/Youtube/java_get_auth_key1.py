#!/usr/bin/env python
import webbrowser
import os

import httplib2
from oauth2client import client
from oauth2client.file import Storage
from time import sleep

if not hasattr(__builtins__,'raw_input'):
    # Python 3
    raw_input = input

FILE_PATH = 'D:/@University/team-12-final/'

#Please Input Your client_secrets.json file in below line
flow = client.flow_from_clientsecrets(
    #Please Input Your Client Secrets JSON File in HERE(Absolute path for windows)
    FILE_PATH + 'client_secrets.json',

    scope=['https://www.googleapis.com/auth/youtube', 'https://www.googleapis.com/auth/youtube.force-ssl'],
    redirect_uri='urn:ietf:wg:oauth:2.0:oob')
auth_uri = flow.step1_get_authorize_url()
webbrowser.open(auth_uri)

while(os.path.exists(FILE_PATH + "authcode") is False):
    sleep(1)

f = open(FILE_PATH+'authcode','r')
auth_code = f.read()
f.close()

credentials = flow.step2_exchange(auth_code)
http_auth = credentials.authorize(httplib2.Http())
storage = Storage(FILE_PATH+"oauth_creds")
storage.put(credentials)