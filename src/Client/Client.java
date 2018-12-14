package Client;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.lang.NullPointerException;
public class Client {
	private String inputStr = null;
	private Socket socket = null;
	public Boolean run(String address, int port) {
		try {
			// Create a stream socket bounded to any port and connect it to the
			socket = new Socket(address, port);
			System.out.println("Connection established");
			return true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	public String query(String str)
	{
		String query=null;
		try
		{
			inputStr=str;
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
			if (!(inputStr.equals("exit"))) 
			{
				// Send the input string to the server by writing to the socket output stream
				out.write("query "+inputStr + "\n");
				out.flush();
				System.out.println("Message sent");
				
				// Receive the reply from the server by reading from the socket input stream
				String received = in.readLine(); // This method blocks until there
													// is something to read from the
													// input stream
				String[] info=received.split(",,");
				System.out.println("Message received: " + info[0]);
				query=info[1];
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			query="The server is closed.";
		} catch(NullPointerException e) {
			query="Please connect firstly.";
		}
		return query;
	}
	public String add(String str) 
	{
		String add=null;
		try
		{
			inputStr=str;
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
			if (!(inputStr.equals("exit"))) 
			{
				// Send the input string to the server by writing to the socket output stream
				out.write("add "+inputStr + "\n");
				out.flush();
				System.out.println("Message sent");
				String received = in.readLine();
				String[] info=received.split(",,");
				System.out.println("Message received: " + info[0]);
				add=info[1];
			}
		}catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			add="The server is closed.";
		} catch(NullPointerException e) {
			add="Please connect firstly.";
		}
		return add;
	}
	public String remove(String str)
	{
		String remove=null;
		try
		{
			inputStr=str;
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
			if (!(inputStr.equals("exit"))) 
			{
				out.write("remove "+inputStr + "\n");
				out.flush();
				System.out.println("Message sent");
				String received = in.readLine();
				String[] info=received.split(",,");
				System.out.println("Message received: " + info[0]);
				remove=info[1];
			}
		}catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			remove="The server is closed.";
		}catch(NullPointerException e) {
			remove="Please connect firstly.";
		}
		return remove;
	}

}
