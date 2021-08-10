package org.example;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.example.handler.*;

/**
 * Hello world!
 */
public class JettyServer {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
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
        server.setHandler(handler);
    }
}
