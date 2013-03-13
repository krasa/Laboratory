package krasa.laboratory;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;

public class Start {

	public static final String path = "src/main/webapp";

	private static Server server;

	public static void main(String[] args) throws Exception {
		System.err.println(new File(path).getAbsolutePath());

		System.setProperty("spring.profiles.active", "DEV");
		// System.setProperty("spring.profiles.active", "STUB");

		server = new Server();
		SelectChannelConnector connector = new SelectChannelConnector();
		connector.setPort(8080);
		server.setConnectors(new Connector[] { connector });
		WebAppContext context = new WebAppContext();
		context.setServer(server);
		context.setContextPath("/");
		context.setWar(path);
		server.addHandler(context);
		server.start();
		server.join();
	}

	private static class MonitorThread extends Thread {

		private ServerSocket socket;

		public MonitorThread() {
			setDaemon(true);
			setName("StopMonitor");
			try {
				socket = new ServerSocket(5678, 1, InetAddress.getByName("127.0.0.1"));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public void run() {
			System.out.println("*** running jetty 'stop' thread");
			Socket accept;
			try {
				accept = socket.accept();
				BufferedReader reader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
				reader.readLine();
				System.out.println("*** stopping jetty embedded server");
				server.stop();
				accept.close();
				socket.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
}
