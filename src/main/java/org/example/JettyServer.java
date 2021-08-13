package org.example;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.example.config.Config;
import org.example.handler.*;
import org.ini4j.Ini;

import java.util.Map;

/**
 * Hello world!
 */
public class JettyServer {
    public static void main(String[] args) throws Exception {

        Map config = Config.getInstance().getConfig();
        int serverPort = Integer.parseInt((String) config.get("jettyPort"));

        Server server = new Server(serverPort);
        setServlet(server);

        server.start();
        server.join();
    }

    private static void setServlet(Server server) {
        ServletHandler handler = new ServletHandler();
        handler.addServletWithMapping(ProductHandler.class, "/products");

        handler.addServletWithMapping(SongHandler.class, "/songs");
        handler.addServletWithMapping(SongLikeHandler.class,"/songs/like");
        handler.addServletWithMapping(SongListenHandler.class, "/songs/listen");
        handler.addServletWithMapping(ArtistSongHandler.class, "/songs/artist");
        handler.addServletWithMapping(TopSongHandler.class, "/songs/topsongs");
        handler.addServletWithMapping(HomeHandler.class, "/home");
        server.setHandler(handler);
    }
}
