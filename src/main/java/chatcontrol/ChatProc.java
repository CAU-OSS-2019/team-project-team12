package chatcontrol;

public class ChatProc {

	private DataTable tb;
	private ChatData ds;
	
	public ChatProc()
	{
		this.tb = new DataTable();
	}
	public DataTable getDataTable()
	{
		return this.tb;
	}
	
	public void doProc(ChatData inputds)
	{
		ds = inputds;
		checkUser();
		if(!checkSpamImoji())
			checkBadword();
		if(ds.getIsNamed())
		{
			System.out.println("네임드당");
		}
		if(ds.getHavetoDisplay_Named())
		{
			System.out.println("새로운 네임드입장");
		}
		if (ds.getIsBadword())
		{
			System.out.println("욕설임");
		}
		else
		{
			System.out.println("ok");
		}
	}
	
	private void checkUser()
	{
		if(tb.getNamedList().contains(ds.getUserID()))
		{
			ds.setIsNamed(true);
			
			if(!tb.getMyList().contains(ds.getUserID()))
			{
				ds.setHavetoDisplay_Named(true);
				tb.getMyList().add(ds.getUserID());
				//havetodisplay_named가 true인 경우, ui쪽에서 입장 표시 출력해주어야함
			}
		}
	}
	
	public boolean checkUser(String userID)
	{
		if(tb.getNamedList().contains(userID))
		{
			if(!tb.getMyList().contains(userID))
			{
				tb.getMyList().add(ds.getUserID());
				return true;
			}
		}
		return false;
	}
	
	private void checkBadword()
	{
		ds.setIsBadword(false);
		
		for(int i=0; i<ds.getChatText().length(); i++) //repeat for input string size times
		{
			if(tb.getBWTable()[(int)(ds.getChatText().charAt(i))].size()!=0) //Is there a bad word which have input(i) for first character?
			{
				for(int j=0; j<tb.getBWTable()[(int)(ds.getChatText().charAt(i))].size(); j++) // repeat for Table[input(i)]'s size times
				{
					if((ds.getChatText().length()-i) >= tb.getBWTable()[(int)(ds.getChatText().charAt(i))].get(j).length())
						//the left characters are not enough to become a selected bad word.
					{
						int h=0;
						int count=1;
						for(int k=1; k<tb.getBWTable()[(int)(ds.getChatText().charAt(i))].get(j).length(); k++)
							//compare input strings with selected bad word
						{
							while(	(ds.getChatText().charAt(i+k+h)>=' ' && ds.getChatText().charAt(i+k+h)<='@')
									||(ds.getChatText().charAt(i+k+h)>='[' && ds.getChatText().charAt(i+k+h)<='`')
									||(ds.getChatText().charAt(i+k+h)>='{' && ds.getChatText().charAt(i+k+h)<='~') )
							{
								h++;
								if(ds.getChatText().length()<=(i+k+h))
									break;
							}
							if(ds.getChatText().length()<=(i+k+h))
							{
								//System.out.println("break1");
								break;
							}
							if(tb.getBWTable()[(int)(ds.getChatText().charAt(i))].get(j).charAt(k) != ds.getChatText().charAt(i+k+h) )
							{
								//System.out.println("break2");
								break;
							}
							else
								count++;
							
							if (count== tb.getBWTable()[(int)(ds.getChatText().charAt(i))].get(j).length())
							{
								String SearchedBadword = tb.getBWTable()[(int)(ds.getChatText().charAt(i))].get(j);
								System.out.println(SearchedBadword); //searched bad word
								ds.setIsBadword(true);
								if( (SearchedBadword.equals("www")) || (SearchedBadword.equals("http")) )
								{
									//System.out.println("url임");
									if(checkSafetyURL(i))
									{
										System.out.println("안전한 url입니다");
										ds.setIsBadword(false);
									}
									else
										System.out.println("안전하지 않은 url입니다");
								}
								return;
							}
						}
					}
				}
			}
		}
	}
	
	private boolean checkSafetyURL(int n)
	{
		for(int i=0; i<tb.getSafeURLList().size(); i++)
		{
			int count = 0;
			if(ds.getChatText().length()-n >= tb.getSafeURLList().get(i).length())
			{
				for(int j=0; j<tb.getSafeURLList().get(i).length(); j++)
				{
					if(tb.getSafeURLList().get(i).charAt(j) == ds.getChatText().charAt(n+j))
						count++;
					else
						break;
				}
				if(count==tb.getSafeURLList().get(i).length())
					return true;
			}
		}
		return false;
	}

	private boolean checkSpamImoji()
	{
		if (ds.getChatText().length()>=30)
		{
			int count = 0;
			for(int i=0; i<ds.getChatText().length(); i++)
			{
				if(((int)(ds.getChatText().charAt(i)))>=9472 && ((int)(ds.getChatText().charAt(i))<=9999))
				{
					count++;
				}
			}
			if(count>=20)
			{
				ds.setIsBadword(true);
				return true;
			}
		}
		return false;
	}

}
