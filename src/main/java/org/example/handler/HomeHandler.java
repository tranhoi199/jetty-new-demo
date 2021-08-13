package org.example.handler;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.transport.layered.TFramedTransport;
import org.example.config.Config;
import org.example.gen.ArtistListSongResponse;
import org.example.gen.Song;
import org.example.gen.SongResponse;
import org.example.gen.SongService;
import org.example.rythm.Rythm;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class HomeHandler extends HttpServlet {
    protected SongService.Client client;
    private Map config = Config.getInstance().getConfig();
    private final Rythm rythmEngine = new Rythm();
    public HomeHandler() throws TTransportException {
        int port = Integer.parseInt((String) config.get("thriftPort"));
        TTransport transport = new TFramedTransport(new TSocket((String) config.get("thriftHost"), port));
        transport.open();
        TProtocol protocol = new TBinaryProtocol(transport);
        client = new SongService.Client(protocol);
    }

    private Song _getSongBaseOnId(int songId) {
        try {
            SongResponse songResponse = client.getSong(songId);
            if (songResponse.getCode() == 200) {
                return songResponse.getSong();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Song>  _getArtistListSong(String artist) {
        try{
            ArtistListSongResponse artistListSongResponse = client.getSongsByArtist("Ed Sheeran");
            // if object return is success, return a list
            if (artistListSongResponse.getCode() == 200) {
                return artistListSongResponse.getListSong();
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String result;
        List<Song> songList = _getArtistListSong("Ed Sheeran");
        try {
            result = rythmEngine.getRythmEngine().render("home.html", songList, "song2");
        } catch (Exception e) {
            result = "<h1>Something wrong when engine render</h1>";
            e.printStackTrace();
        }
        out.print(result);
    }
}
