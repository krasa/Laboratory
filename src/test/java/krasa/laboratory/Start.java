package krasa.laboratory;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;

import java.io.File;

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
		server.setConnectors(new Connector[]{connector});
		WebAppContext context = new WebAppContext();
		context.setServer(server);
		context.setContextPath("/");
		context.setWar(path);
		server.addHandler(context);
		server.start();
		server.join();
	}

}
