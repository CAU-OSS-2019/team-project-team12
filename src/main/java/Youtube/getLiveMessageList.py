#!/usr/bin/env python

from time import sleep
import sys
import json
import re

from youtubechat import YoutubeLiveChat, get_live_chat_id_for_broadcast_id, get_live_chat_id_for_stream_now

getStringWithDecodedUnicode = lambda str : re.sub( '\\\\u([\da-f]{4})', (lambda x : chr( int( x.group(1), 16 ) )), str )

#Input FilePath of "oauth_creds", "broadcastid"
FILE_PATH = 'D:/@University/team-12-final/'

f = open(FILE_PATH+"broadcastid","r")

broadcast_id = f.read()
print(broadcast_id)

livechat_id = get_live_chat_id_for_broadcast_id(broadcast_id,FILE_PATH+"oauth_creds")
print(livechat_id)

chat_obj = YoutubeLiveChat(FILE_PATH+"oauth_creds", [livechat_id])
f = open(FILE_PATH+"chatdata.json","w")

dataaa = chat_obj.livechat_api.get_all_messages(livechat_id)
dataaa = json.dumps(dataaa)
f.write(dataaa)
f.close()