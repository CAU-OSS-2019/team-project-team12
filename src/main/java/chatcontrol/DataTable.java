package chatcontrol;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class DataTable {
	private static ArrayList<String>[] BWTable;
	private static ArrayList<String> namedList;
	private static ArrayList<String> myList;
	private static ArrayList<String> safeURLList;
	
	public DataTable() {
		this.SetBWTable();
		this.SetNamedList();
		this.SetSafeURLList();
		this.myList = new ArrayList<String>();
	}
	
	public void SetBWTable()
	{
		this.BWTable = new ArrayList[65536];
		for(int i = 0; i<65536; i++)
		{
			BWTable[i] = new ArrayList<String>();
		}
		try
		{
			File file = new File(getClass().getResource("/txt/BADWORD.txt").getPath());

			FileReader filereader = new FileReader(file);

			BufferedReader bufReader = new BufferedReader(filereader);
			String line = "";
			while((line = bufReader.readLine()) != null)
			{
				BWTable[(int)(line.charAt(0))].add(line);
				//System.out.println(line);
			}
			bufReader.close();
		}
		catch (FileNotFoundException e)
		{
			System.out.println("badword file not found");
	        // TODO: handle exception
	    }
		catch(IOException e)
		{
	        System.out.println(e);
	    }
	}
	public void AddToBWTable(String newWord)
	{
		FileWriter writer = null;
		File file = new File(getClass().getResource("/txt/BADWORD.txt").getPath());
		
	    int index = BWTable[newWord.charAt(0)].indexOf(newWord);
	   	if(index == -1)
	   	{
			try
			{
				writer = new FileWriter(file, true);
				writer.write(newWord + "\n");
				writer.flush();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if(writer != null) writer.close();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		this.SetBWTable();
	}
	public void AddToBWTable(Vector<String> newWords)
	{
		FileWriter writer = null;
		File file = new File(getClass().getResource("/txt/BADWORD.txt").getPath());
		
		for(int i = 0; i < newWords.size(); i++)
		{
	    	int index = BWTable[newWords.get(i).charAt(0)].indexOf(newWords.get(i));
	    	if(index == -1)
	    	{
				try
				{
					writer = new FileWriter(file, true);
					writer.write(newWords.get(i) + "\n");
					writer.flush();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
				finally
				{
					try
					{
						if(writer != null) writer.close();
					}
					catch(IOException e)
					{
						e.printStackTrace();
					}
				}
			}
		}
		this.SetBWTable();
	}
	
	public void DeleteFromBWTable(String deleteWord)
	{
		File file = new File(getClass().getResource("/txt/BADWORD.txt").getPath());
		
		int index = BWTable[(int)(deleteWord.charAt(0))].indexOf(deleteWord);
		if(index != -1)
    	{
			BWTable[(int)(deleteWord.charAt(0))].remove(deleteWord);	
		}
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			for(int i = 0; i < 65536; i++)
			{
				for(int j = 0; j < BWTable[i].size(); j++)
				{
					writer.write(BWTable[i].get(j));
					writer.newLine();
					writer.flush();
				}
			}
			writer.close();
		}
		catch(IOException e)
		{
		}		
	}
	public void DeleteFromBWTable(Vector<String> deleteWords)
	{
		File file = new File(getClass().getResource("/txt/BADWORD.txt").getPath());
		
		for(int i = 0; i < deleteWords.size(); i++)
		{
	    	int index = BWTable[(int)(deleteWords.get(i).charAt(0))].indexOf(deleteWords.get(i));
	    	if(index != -1)
	    	{
				BWTable[(int)(deleteWords.get(i).charAt(0))].remove(deleteWords.get(i));	
			}
		}
		
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			for(int i = 0; i < 65536; i++)
			{
				for(int j = 0; j < BWTable[i].size(); j++)
				{
					writer.write(BWTable[i].get(j));
					writer.newLine();
					writer.flush();
				}
			}
			writer.close();
		}
		catch(IOException e)
		{
		}		
	}
	
	public void SetNamedList()
	{
		this.namedList = new ArrayList<String>();
		try
		{
			File file = new File(getClass().getResource("/txt/NAMED.txt").getPath());
			FileReader filereader = new FileReader(file);
			BufferedReader bufReader = new BufferedReader(filereader);
			String line = "";
			while((line = bufReader.readLine()) != null){
				namedList.add(line);
				//System.out.println(line);
			}
			bufReader.close();
		}
		catch (FileNotFoundException e)
		{
			System.out.println("named file not found");
	        // TODO: handle exception
	    }
		catch(IOException e)
		{
	        System.out.println(e);
	    }
	}

	public void AddToNamedList(String newUser)
	{
		File file = new File(getClass().getResource("/txt/NAMED.txt").getPath());
		FileWriter writer = null;
		int index = namedList.indexOf(newUser);
	    if(index == -1)
	    {
			try
			{
				writer = new FileWriter(file, true);
				writer.write(newUser + "\n");
				writer.flush();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if(writer != null) writer.close();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		this.SetNamedList();
	}
	public void AddToNamedList(Vector<String> newUser)
	{
		File file = new File(getClass().getResource("/txt/NAMED.txt").getPath());
		FileWriter writer = null;
		for(int i = 0; i < newUser.size(); i++){
			int index = namedList.indexOf(newUser.get(i));
	    	if(index == -1)
	    	{
				try
				{
					writer = new FileWriter(file, true);
					writer.write(newUser.get(i) + "\n");
					writer.flush();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
				finally
				{
					try
					{
						if(writer != null) writer.close();
					}
					catch(IOException e)
					{
						e.printStackTrace();
					}
				}
			}
		}
		this.SetNamedList();
	}

	public void DeleteFromNamedList(String deleteUser)
	{
		File file = new File(getClass().getResource("/txt/NAMED.txt").getPath());
		
	    int index = namedList.indexOf(deleteUser);
	    if(index != -1)
	    {
			namedList.remove(deleteUser);	
		}
	    
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));

			for(int j = 0; j < namedList.size(); j++)
			{
				writer.write(namedList.get(j));
				writer.newLine();
				writer.flush();
			}
			writer.close();
		}
		catch(IOException e)
		{
		}		
	}
	public void DeleteFromNamedList(Vector<String> deleteUser)
	{
		File file = new File(getClass().getResource("/txt/NAMED.txt").getPath());
		
		for(int i = 0; i < deleteUser.size(); i++)
		{
	    	int index = namedList.indexOf(deleteUser.get(i));
	    	if(index != -1)
	    	{
				namedList.remove(deleteUser.get(i));	
			}
		}
		
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));

			for(int j = 0; j < namedList.size(); j++)
			{
				writer.write(namedList.get(j));
				writer.newLine();
				writer.flush();
			}
			writer.close();
		}
		catch(IOException e)
		{
		}		
	}
	
	public void SetSafeURLList()
	{
		this.safeURLList = new ArrayList<String>();
		try
		{
			File file = new File(getClass().getResource("/txt/URL.txt").getPath());
			FileReader filereader = new FileReader(file);
			BufferedReader bufReader = new BufferedReader(filereader);
			String line = "";
			while((line = bufReader.readLine()) != null){
				safeURLList.add(line);
				//System.out.println(line);
			}
			bufReader.close();
		}
		catch (FileNotFoundException e)
		{
			System.out.println("url file not found");
	        // TODO: handle exception
	    }
		catch(IOException e)
		{
	        System.out.println(e);
	    }
	}

	public void AddToURLList(String newURL)
	{
		File file = new File(getClass().getResource("/txt/URL.txt").getPath());
		FileWriter writer = null;
	
		int index = safeURLList.indexOf(newURL);
	    if(index == -1)
	    {
			try
			{
				writer = new FileWriter(file, true);
				writer.write(newURL + "\n");
				writer.flush();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if(writer != null) writer.close();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}
	    }
		this.SetSafeURLList();
	}
	public void AddToURLList(Vector<String> newURL)
	{
		File file = new File(getClass().getResource("/txt/URL.txt").getPath());
		FileWriter writer = null;
		for(int i = 0; i < newURL.size(); i++)
		{
			int index = safeURLList.indexOf(newURL.get(i));
	    	if(index == -1)
	    	{
				try
				{
					writer = new FileWriter(file, true);
					writer.write(newURL.get(i) + "\n");
					writer.flush();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
				finally
				{
					try
					{
						if(writer != null) writer.close();
					}
					catch(IOException e)
					{
						e.printStackTrace();
					}
				}
			}
		}
		this.SetSafeURLList();
	}

	public void DeleteFromURLList(String deleteURL)
	{
		File file = new File(getClass().getResource("/txt/URL.txt").getPath());
		

	    int index = safeURLList.indexOf(deleteURL);
	    if(index != -1)
	    {
			safeURLList.remove(deleteURL);	
		}
		
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			for(int j = 0; j < safeURLList.size(); j++)
			{
				writer.write(safeURLList.get(j));
				writer.newLine();
				writer.flush();
			}
			writer.close();
		}
		catch(IOException e)
		{
		}	
	}
	public void DeleteFromURLList(Vector<String> deleteURL)
	{
		File file = new File(getClass().getResource("/txt/URL.txt").getPath());
		
		for(int i = 0; i < deleteURL.size(); i++)
		{
	    	int index = safeURLList.indexOf(deleteURL.get(i));
	    	if(index != -1)
	    	{
				safeURLList.remove(deleteURL.get(i));	
			}
		}
		
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			for(int j = 0; j < safeURLList.size(); j++)
			{
				writer.write(safeURLList.get(j));
				writer.newLine();
				writer.flush();
			}
			writer.close();
		}
		catch(IOException e)
		{
		}		
	}
	
	public ArrayList<String>[] getBWTable()
	{
		return this.BWTable;
	}
	public ArrayList<String> getNamedList()
	{
		return this.namedList;
	}
	public ArrayList<String> getMyList()
	{
		return this.myList;
	}
	public ArrayList<String> getSafeURLList()
	{
		return this.safeURLList;
	}

}
