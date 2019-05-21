# DDOKDDOK/UI
## DDOKDDOK Chatbot UI Repository
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

# Installation
* Maven을 이용하는 프로젝트이므로, .zip파일을 로컬에 다운받아서 압축을 푼 뒤 Maven Project에 Import해야합니다.
* Javafx 기반의 UI입니다. javafxapplication을 extends하고있는 Main.java파일을 run하면, UI의 PrimaryStage를 Start합니다.
* TwitchController.java와 YoutubeController.java의 
*Desktop.getDesktop().edit(new File("AbsolutePath"));*
으로 구현된 필터링 할 욕설과 채팅 허용 url링크, 입장 안내를 받을 스트리머 각각의 목록을 저장하고있는 .txt파일을 여는 부분에서, "AbsolutePath"부분을 RelativePath로 대체하는 방법을 아직 찾지 못했습니다. 실행하시기  Repository에 포함되어있는 keywords.txt, urls.txt, streamers.txt파일의 절대경로를 수정해주셔야합니다.

 
