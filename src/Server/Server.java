package Server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.net.ServerSocketFactory;
import java.io.File;
public class Server {
	
	// Declare the port number
	private static int port;
	private static String dic;
	// Identifies the user number connected
	private static int counter = 0;

	public static void main(String[] args) {
		File file=new File(args[1]);
		if(file.exists())
		{
			port=Integer.parseInt(args[0]);
			dic=args[1];
			ServerSocketFactory factory = ServerSocketFactory.getDefault();
			try(ServerSocket server = factory.createServerSocket(port)){
				System.out.println("Waiting for client connection..");
				
				// Wait for connections.
				while(true){
					Socket client = server.accept();
					counter++;
					System.out.println("Client "+counter+": Applying for connection!");
					// Start a new thread for a connection
					MyThread t = new MyThread(client,counter,dic);
					t.start();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else 
			System.out.println("The dictionary doesn't exist.");
	}
}
