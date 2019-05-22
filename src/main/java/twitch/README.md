# DDOKDDOK
## Chat Control
 * DDOKDDOK Team의 Twitch IRC 연결 관리를 위한 package입니다.

# Main Features
## DDokDDokTwitch.java
 * DDokDDok을 Twitch IRC서버에 접근 시키게 해주는 코드입니다.
 * Listner Inner Class를 이용해 메세지 서버에서 특정 메세지 혹은 event가 발생할 시 관련 작업을 처리해줍니다.

### Method Samples
```bash
boolean connect();
void banUser();
ObservableList<ChatDataProperty> getChatDataObservableList();
String oauthString();
```
### Must Do
* [Twitch OAUTH 키 발급](https://twitchapps.com/tmi/)을 받아 src/main/resources/json 폴더에 키를 저장해야 합니다.
* Maven 설정을 하여 [IRC연결을 위한 Kitteh](https://kitteh.org/) 와 [JSON 오브젝트 설정을 위한 org.json](https://mvnrepository.com/artifact/org.json/json) 라이브러리 설정을 완료해야 합니다.

### Event Listener
* onUserJoinChannel : 비동기 방식으로 채널에 새로운 사용자가 들어올 경우 발생하는 이벤트 입니다.
* onMsgFired : 채널에 메세지가 보내질 경우 발생하는 이벤트 입니다. 
### Main Logic
* 1. User가 메세지 입력
* 2. ChatData object 생성 후 ChatProc에 전달
* 3. ChatData의 상태에 따라 ObservableList에 추가하여 UI에 반영

# Reffernce
* [Kitteh Library](https://kitteh.org/)
* [Python으로 초간단 Twitch Chatbot 만들기](https://steemit.com/kr-dev/@maanya/20)

# Original Repository
[DDokDDok Twitch](https://github.com/cauchatbot/Twitch)

# Main Contributor
 * 김상헌
