package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	/*public static void main(String[] args) throws IOException {
		Thread serverThread = new Thread(() -> {
			try{ 
				Server s = new Server(Integer.parseInt(args[0]));
				s.listen();
			} catch(IOException e){
				 e.printStackTrace();
			}
			});
		}
	*/
	private ServerSocket accepter;

	public Server(int port) throws IOException {
		accepter = new ServerSocket(port);
		System.out.println("Server: IP address: " + accepter.getInetAddress() + " (" + port + ")");
	}

	public void listen() throws IOException {
		for (;;) {
			Socket s = accepter.accept();
			new Thread(() -> {
				
			}).start();
		}
	}
	
	private class SocketEchoThread extends Thread {
	    private Socket socket;
	    
	    public SocketEchoThread(Socket socket) {
	        this.socket = socket;
	    }

	    public void run() {
	        try {
	            PrintWriter writer = new PrintWriter(socket.getOutputStream());
	            sendGreeting(writer);
	            String msg = getMessage();
	            System.out.println("Server: Received [" + msg + "]");
	            echoAndClose(writer, msg);
	        } catch (IOException ioe) {
	            ioe.printStackTrace();
	        } 
	    }
	    
	    private void sendGreeting(PrintWriter writer) {
            //writer.println("Connection open.");
           // writer.println("I will echo a single message, then close.");	
	    }
	    
	    private void echoAndClose(PrintWriter writer, String msg) throws IOException {
            writer.print(msg);
            writer.flush();
            socket.close();	    	
	    }
	    
	    private String getMessage() throws IOException {
            BufferedReader responses = 
            		new BufferedReader(new InputStreamReader(socket.getInputStream()));
            StringBuilder sb = new StringBuilder();
            while (!responses.ready()){}
            while (responses.ready()) {
                sb.append(responses.readLine() + '\n');
            }
	    	return sb.toString();
	    }
	}
}