package com.lcs.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ListenForClients implements Runnable
{

	private static int port = 5556, maxConnections = 0;
	static List<doComms> threads = new ArrayList<doComms>();
	// Listen for incoming connections and handle them
	public void run()
	{
		int i = 0;
		System.out.println("Starting gameserver...");
		try
		{
			ServerSocket listener = new ServerSocket(port);
			Socket server;

			while ((i++ < maxConnections) || (maxConnections == 0))
			{
				doComms connection;
				System.out.println("Listening for new connection...");
				server = listener.accept();
				System.out.println("New connection made from " + server.getInetAddress().toString());
				doComms conn_c = new doComms(server);
				Thread t = new Thread(conn_c);
				t.start();
				threads.add(conn_c);
				conn_c.out.println("Hello and welcome to Spare Time Destruction!");
				for(int x = 0; x < threads.size(); x++)
				{
					doComms d = threads.get(x);
					if(d.server.isClosed())
					{
						threads.remove(d);
						System.out.println("Removed a client.");
						i--;
						x--;
					}
				}
				for(doComms d : threads)
				{
					if(!d.server.isClosed())
					{
						d.sendMessage("New connection from: " + server.getInetAddress());
						d.sendMessage("There are currently " + i + " clients");
						if (i > 1)
						{
							d.sendMessage("Two or more clients connected, lets play!");
						}
					}
				}
			}
		} catch (IOException ioe)
		{
			System.out.println("IOException on socket listen: " + ioe);
			ioe.printStackTrace();
		}
	}
}
