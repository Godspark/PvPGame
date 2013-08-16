package com.lcs.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class doComms implements Runnable
{
	Socket server;
	private String line, input;
	// Get input from the clientBufferedReader d
	BufferedReader inputStream;
	// DataInputStream in = new DataInputStream(server.getInputStream());
	PrintStream out;

	doComms(Socket server)
	{
		this.server = server;
		try
		{
			inputStream = new BufferedReader(new InputStreamReader(
					server.getInputStream()));
			out = new PrintStream(server.getOutputStream());
		} catch (IOException e)
		{
			System.out.println("Failed to create reader and writer for socket "
					+ server.getInetAddress());
			e.printStackTrace();
		}
	}

	public void run()
	{

		input = "";

		try
		{

			out.println("Hello client!");
			while ((line = inputStream.readLine()) != null && !line.equals("."))
			{
				input = input + line;
				out.println("EkkoPekko" + line);
			}

			// Now write to the client

			System.out.println("Overall message is:" + input);
			out.println("Overall message is:" + input);

			server.close();
		} catch (IOException ioe)
		{
			System.out.println("IOException on doComm listen: " + ioe);
			ioe.printStackTrace();
			try
			{
				server.close();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void sendMessage(String sendString)
	{
		out.println(sendString);
	}

}
