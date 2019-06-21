#!/usr/bin/env python

from time import sleep
import sys
from youtubechat import YoutubeLiveChat, get_live_chat_id_for_broadcast_id, get_live_chat_id_for_stream_now


#Input FilePath of "oauth_creds", "broadcastid"
FILE_PATH = 'D:/@University/team-12-final/'

f = open(FILE_PATH+"broadcastid","r")

broadcast_id = f.read()
print(broadcast_id)

livechat_id = get_live_chat_id_for_broadcast_id(broadcast_id,FILE_PATH+"oauth_creds")
print(livechat_id)

chat_obj = YoutubeLiveChat(FILE_PATH+"oauth_creds", [livechat_id])
f = open(FILE_PATH+"chatdata.json","w")
f.write(chat_obj.livechat_api.get_all_messages(livechat_id))
f.close()