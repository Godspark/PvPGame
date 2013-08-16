package com.lcs.client;

import java.io.*;
import java.net.Socket;


public class ConnectToServers implements Runnable
{
	String serverText = "";
	Socket clientSocket = null;
	PrintWriter out = null;
	BufferedReader in = null;
	String newIP = "";
	String newPort = "";

	private void connectToMMServer()
	{
		try
		{
			clientSocket = new Socket("158.37.221.106", 5555);
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			String serverMessage = "";

			while ((serverMessage = in.readLine()) != null)
			{
				serverText = serverText.concat("\n" + serverMessage);
				System.out.println(serverText);

				if (serverMessage.equalsIgnoreCase("reconnect"))
				{
					newIP = in.readLine();
					newPort = in.readLine();
					break;
				}
			}

			out.close();
			in.close();
			stdIn.close();
			clientSocket.close();

		} catch (IOException e)
		{
			System.err.println("Couldn't get I/O");
			System.exit(1);
		}
	}

	private void connectToGameServer()
	{
		try
		{
			clientSocket = new Socket(newIP, Integer.parseInt(newPort));
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			String serverMessage = "";

			while ((serverMessage = in.readLine()) != null)
			{
				serverText = serverText.concat("\n" + serverMessage);
				System.out.println(serverText);

			}

			out.close();
			in.close();
			stdIn.close();
			clientSocket.close();

		} catch (IOException e)
		{
			System.err.println("Couldn't get I/O");
			System.exit(1);
		}
	}

	
	public void run()
	{
		connectToMMServer();
		connectToGameServer();
	}

}