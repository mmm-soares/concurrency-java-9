package com.javferna.packtpub.mastering.simpleserver.concurrent.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.javferna.packtpub.mastering.simpleserver.common.Command;
import com.javferna.packtpub.mastering.simpleserver.common.ConcurrentErrorCommand;
import com.javferna.packtpub.mastering.simpleserver.common.ConcurrentQueryCommand;
import com.javferna.packtpub.mastering.simpleserver.common.ConcurrentReportCommand;
import com.javferna.packtpub.mastering.simpleserver.common.ConcurrentStatusCommand;
import com.javferna.packtpub.mastering.simpleserver.common.ConcurrentStopCommand;
import com.javferna.packtpub.mastering.simpleserver.parallel.cache.ParallelCache;
import com.javferna.packtpub.mastering.simpleserver.parallel.log.Logger;

/**
 * Task that executes a request to the concurrent server
 * @author author
 *
 */
public class RequestTask implements Runnable {

	/**
	 * Socket to communicate with the client
	 */
	private final Socket clientSocket;

	/**
	 * Constructor of the class
	 * @param clientSocket socket to communicate
	 */
	public RequestTask(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	@Override
	/**
	 * Method that executes the request
	 */
	public void run() {

		try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),
				true);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						clientSocket.getInputStream()));) {

			String line = in.readLine();
			
			Logger.sendMessage(line);
			ParallelCache cache = ConcurrentServer.getCache();
			String ret = cache.get(line);
			
			if (ret == null) {
				Command command;
				

				String[] commandData = line.split(";");
				System.err.println("Command: " + commandData[0]);
				switch (commandData[0]) {
				case "q":
					System.err.println("Query");
					command = new ConcurrentQueryCommand(commandData);
					break;
				case "r":
					System.err.println("Report");
					command = new ConcurrentReportCommand(commandData);
					break;
				case "s":
					System.err.println("Status");
					command = new ConcurrentStatusCommand(commandData);
					break;
				case "z":
					System.err.println("Stop");
					command = new ConcurrentStopCommand(commandData);
					break;
				default:
					System.err.println("Error");
					command = new ConcurrentErrorCommand(commandData);
					break;
				}
				ret = command.execute();
				if (command.isCacheable()) {
					cache.put(line, ret);
				}
			} else {
				Logger.sendMessage("Command "+line+" was found in the cache");
			}

			System.err.println(ret);
			out.println(ret);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
