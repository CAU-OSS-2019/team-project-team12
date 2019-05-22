# DDOKDDOK
## DDOKDDOK Chatbot
 * DDOKDDOK Team의 UI 디자인 및 UI Controller 개발을 위한 Repository입니다.

# Main Features
## DDOKDDOK Chatbot의 시작을 위한 로그인 기능을 제공합니다.
 * 테스트를 위해 Twitch는 twitch, Youtube는 youtube를 입력하면 로그인하여 각 Platform의 메인창으로 이동합니다.
 * Twitch Platform에서 사용하기 위해서는, 사용 스트리머의 채널 이름(방송 url *"twitch.tv/□"* 에서의 '□'부분)을 입력하고 Twitch 버튼을 눌러야합니다.
 * Youtube Platform에서 사용하기 위해서는, 사용 스트리머 채널의 공유 url *"youtube.com/watch?v=□"*(혹은, *"youtu.be/□*) 에서의 '□'부분을 입력하고 Youtube 버튼을 눌러야합니다.
 * Youtube 버튼을 누르면, 사용자 인증 API키를 받는 2차 로그인 창과 함께, youtube 로그인을 위한 인터넷 창이 열립니다. youtube에 로그인을 하면 API 키가 "애플리케이션 ~ 붙여넣으세요."라는 메세지와 함께 나타나고, 이것을 DDOKDDOK Chatbot 프로그램에 입력하면 됩니다.
## 로그인에 실패하면 로그인 실패 안내창이 나타납니다. 
 * 안내창을 닫으면 다시 로그인 할 수 있습니다.
 
## DDOKDDOK Chatbot이 각 플랫폼에서 작동할 때, UI는 욕설 채팅 로그를 기록합니다.
 * 임시적으로 출력 테스트를 위한 값들을 default로 넣어두었습니다.
 * 채팅 로그는 해당 채팅의 내용과 함께 해당 채팅을 입력한 유저의 id와 nickname을 Table의 형식으로 표시해줍니다.(TO DO : Connection with model)
 * Table의 한 Column을 선택하여 해당하는 유저의 밴을 결정할 수 있습니다.
 
## 챗봇이 활용할 욕설 필터링 및 url링크, 입장 알림을 위한 Document(.txt file)들을 관리합니다.
 * 각 기능에 맞는 버튼을 누르면 각 기능에 해당하는 현재의 Document와 Document 수정을 위한 입력창이 열립니다.
 * 입력창에 수정할 내용을 입력한 뒤 추가 또는 삭제 버튼을 누르면 Document가 수정됩니다.

## 모든 리소스 파일은 코드와 분리되어 있습니다.
* src/main/resources 폴더 아래에 image, fxml, font 등 각종 리소스 파일들이 존재합니다.
* OATUH Key관리를 위한 JSON폴더의 경우 OATUH 키 보호를 위해 Git에 업로드되지 않으므로 반드시 Google 혹은 Twitch에서 개별적으로 키를 발급받아 사용하여야 합니다.

## Package별 ReadMe가 존재합니다.
* 세부적인 흐름을 파악하고 싶다면, 각 패키지 폴더(ex> src/main/java/twitch)에 존재하는 README.md파일을 확인하시기 바랍니다.

# Installation
* [Maven을 윈도우 10에 설치하기](https://printhelloworld.tistory.com/113)
* [Eclipse에서 JavaFX 컴파일하기](https://www.eclipse.org/efxclipse/references.html)

# DDokDDok Youtube Live Streaming Python Module Repository.
[DDokDDok Livestreaming Python Module](https://github.com/cauchatbot/Youtube)

## 이 Release Version은 Windows 용입니다.
 * Youtube 기능을 사용하실때에는, 위에 게시된 DDokDDok Python Module의 README를 반드시 읽어주셔야 동작합니다.
 * 봇을 사용하기 전, `.../src/main/java/UI`에 위치한 `java_get_auth_key1.py`와 `bot.py`의 `FILE_PATH`를 반드시 변경하여주십시오.
