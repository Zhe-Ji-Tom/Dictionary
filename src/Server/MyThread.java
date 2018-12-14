package Server;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
public class MyThread extends Thread
{
	private String file;
	private int i;
	private Socket clientSocket;
	public MyThread(Socket client,int counter,String dic)
	{
		this.i=counter;
		this.clientSocket=client;
		file=dic;
	}
	public void run()
	{
		try
		{
			boolean f=true;
			while (f) 
			{
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "UTF-8"));
				String clientMsg = null;
				try {
					while((clientMsg = in.readLine()) != null) {
						String response=null;
						System.out.println("Message from client " + i + ": " + clientMsg);
						String[] str=clientMsg.split(" ");
						if(str[0].equals("query"))
						{
							if(str.length!=2)
							{
								response="Please enter the correct input.";
							}
							else
							{
								response=query(str[1]);
							}
						}
						if(str[0].equals("add"))
						{
							String[] str2=str[1].split(":");
							if(str2.length!=2)
							{
								response="Please enter the correct format, like a:a.";
							}
							else
							{
								response=add(str2);
							}
						}
						if(str[0].equals("remove"))
						{
							if(str.length!=2)
							{
								response="Please enter the correct input.";
							}
							else
							{
								response=remove(str[1]);
							}
						}
						out.write("Server Ack " + clientMsg +",,"+response+ "\n");
						out.flush();	//Send acknowledge message and response message togethers
						System.out.println("Response sent");
					}}
					catch(SocketException e) {
						System.out.println("Client "+i+" is closed...");
						f=false;
					}
			}
		} catch (SocketException ex) {
			ex.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String query(String word)
	{
		try
		{
			Scanner inputStream=new Scanner(new FileInputStream(file));
			for(;inputStream.hasNextLine();)
			{
				String[] info=inputStream.nextLine().split(":");
				if(info[0].equals(word))
				{
					inputStream.close();
					return info[1];
				}
			}
			inputStream.close();
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return "There is not this word.";
	}
	public String add(String[] str)
	{
		try
		{
			Scanner inputStream=new Scanner(new FileInputStream(file));
			for(;inputStream.hasNextLine();)
			{
				String[] info=inputStream.nextLine().split(":");
				if(info[0].equals(str[0]))
				{
					inputStream.close();
					return "The word has existed.";
				}
			}
			inputStream.close();
			try
			{
				File f=new File(file);
				FileWriter fw=new FileWriter(f,true);
				PrintWriter pw=new PrintWriter(fw);
				pw.println(str[0]+":"+str[1]);
				pw.flush();
				fw.flush();
				pw.close();
				fw.close();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return "Add Successful.";
	}
	public String remove(String word)
	{
		ArrayList<String> dic=new ArrayList<String>();
		int location=-1;
		try
		{
			Scanner inputStream=new Scanner(new FileInputStream(file));
			for(int i=0;inputStream.hasNextLine();i++)
			{
				String entry=inputStream.nextLine();
				dic.add(entry);
				String[] info=entry.split(":");
				if(info[0].equals(word))
				{
					location=i;
				}
			}
			if(location!=-1)
			{
				dic.remove(location);
			}
			else
			{
				inputStream.close();
				return "There is not this word.";
			}
			PrintWriter outputStream = new PrintWriter(new FileOutputStream(file));
			for(int i=0;i<dic.size();i++)
			{
				outputStream.println(dic.get(i));
			}
			outputStream.close();
			inputStream.close();
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return "Remove Successful.";
	}
}
