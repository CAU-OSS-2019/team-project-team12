# DDOKDDOK
## Chat Control
 * DDOKDDOK Team의 채팅 내용 관리를 위한 package입니다.

# Main Features
## DataTable.java
 * 채팅을 필터링하기 위해 필요한 데이터를 생성하는 클래스이다.
 * BWTable(욕설), namedList(유명인 / 타스트리머), myList(내 방에 있는 named), safeURLList(채팅에서 언급 가능한 URL 목록)의 ArrayList를 갖고 있다.
 * BWTable은 ArrayList<String>[], 그 이외의 리스트들은 ArrayList<String> 형이다. 이는 많은 keyword에 대한 정보를 갖고있는 BWTable에서의 탐색을 다른 것에 비해 빠르게 하기 위함이다.
 * 데이터들은 각각의 텍스트파일에 저장되어 있으며 File I/O의 방식으로 관리된다.

### Method Samples
```bash
SetBWTable();
SetNamedList();
SetSafeURLList();
AddToBWTable(String newWord);
AddToNamedList(String newUser);
AddToURLList(String newURL);
DeleteFromBWTable(String deleteWord);
DeleteFromNamedList(String deleteUser);
DeleteFromURLList(String deleteURL);
DeleteFromURLList(Vector<String> deleteURL);
```
### Detail Example
* 예시 1) DataProc.getDataTable.SetBWTable(); // "BADWORD.txt"의 정보를 받아들여 BWTable을 생성한다.
* 예시 2) DataProc.getDataTable.AddToNamedList(newUser); // newUser의 스트링 벡터를 인자로 해, 이들이 기존의 리스트에 있나 확인하고 중복되지 않을 시 이들을 저장하고 추가된 정보를 포함한 네임드리스트를 재생성한다.
* 예시 3) DataProc.getDataTable.DeleteFromURLList(deleteURL); // deleteURL의 스트링 벡터를 인자로 해, 이들이 기존의 리스트에 있나 확인하고 있을 시 이들을 텍스트파일과 safeURLList에서 삭제한다.

## ChatData.java
 * 채팅에 대한 정보를 담고 있는 자료구조
### Variables Samples
```bash
private String userID;
private String userNickName;
private String chatText;
private boolean isBadword;
private boolean isNamed;
private boolean havetoDisplay_Named;
```
 
## ChatProc.java
 * 채팅 데이터와 관련된 작업을 처리하기 위한 class
### Method Samples
```bash
private void checkUser()
public boolean checkUser(String userID)
private void checkBadword()
private boolean checkSafetyURL(int n)
private boolean checkSpamImoji()
public void doProc(ChatData inputds)
```
### Detail Example
* Chatting 내용 처리) chatProc.doProc(new ChatData(~))
* User 방문 이벤트 처리) chatProc.checkUser(UserName as String)
* 내 방에 있는 네임드 목록) chatProc.getDataTable.getMyList()

# Text File Format
* 이용되는 텍스트파일들은 UTF-8 형식으로 인코딩 되어있는 텍스트 파일의 사용을 권장한다.
* 텍스트파일의 맨 마지막 줄은 내용이 없어야 한다.
* 텍스트파일에 대한 수동적인 수정은 권장되지 않는다. (프로그램 상의 Add / Delete 기능 이용을 권장한다.)

# Original Repository
[DDokDDok Twitch](https://github.com/cauchatbot/Twitch)

# Main Contributor
 * 이지호
 * 송제웅
