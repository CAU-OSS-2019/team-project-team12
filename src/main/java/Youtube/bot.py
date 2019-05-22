#!/usr/bin/env python

from time import sleep
import sys
from youtubechat import YoutubeLiveChat, get_live_chat_id_for_broadcast_id, get_live_chat_id_for_stream_now

argv = sys.argv

#Input FilePath of "oauth_creds", "broadcastid"
FILE_PATH = 'D:/@University/4-1/OSS/team-project-team12/'

f = open(FILE_PATH+"broadcastid","r")

broadcast_id = f.read()

livechat_id = get_live_chat_id_for_broadcast_id(broadcast_id,FILE_PATH+"oauth_creds")
print(livechat_id)

chat_obj = YoutubeLiveChat(FILE_PATH+"oauth_creds", [livechat_id])

def respond(msgs, chatid):
    for msg in msgs:
        print(msg)
        if msg.message_text[0] is '!':
            chat_obj.send_message("BOT: " +msg.message_text, chatid)
            msg.delete()

try:
    chat_obj.start()
    chat_obj.subscribe_chat_message(respond)
    chat_obj.join()

finally:
    chat_obj.stop()
