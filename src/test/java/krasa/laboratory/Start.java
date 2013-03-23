package krasa.laboratory;

import java.io.File;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.webapp.WebAppContext;

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
		server.setHandler(context);
		server.start();
		server.join();
	}

}
