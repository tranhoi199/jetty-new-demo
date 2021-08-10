package org.example.handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import org.example.gen.ArtistListSongResponse;
import org.example.model.ListSongResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class ArtistSongHandler extends BaseHandler {
    public ArtistSongHandler() throws TTransportException {
    }

    @Override
    protected void doGetOperation(Map mapParams, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        String artist = (String) mapParams.get("artistName");

        //if artist name is null return bad request
        if (artist == null) {
            String message = new Gson().toJson(new ListSongResponse(400, null, "invalid request"));
            _printResponse(HttpServletResponse.SC_BAD_REQUEST, message, resp, out);
            return;
        }

        try {
            ArtistListSongResponse result = client.getSongsByArtist(artist);
            if (result.getCode() == 406) {
                String message = new Gson().toJson(new ListSongResponse(406, null, "invalid artist name"));
                _printResponse(HttpServletResponse.SC_NOT_ACCEPTABLE, message, resp, out);
                return;
            }
            ListSongResponse message = new ListSongResponse(
                    result.getCode(),
                    result.getListSong(),
                    "successfully"
            );
            _printResponse(HttpServletResponse.SC_OK, new Gson().toJson(message), resp, out);

        } catch (TException e) {
            String message = new Gson().toJson(new ListSongResponse(500, null, "internal server error"));
            _printResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message, resp, out);
            e.printStackTrace();
        }
    }

    @Override
    protected void doPostOperation(Map mapParams, JsonObject requestBodyJson, HttpServletResponse resp) throws IOException {

    }

    @Override
    protected void doPutOperation(Map mapParams, JsonObject requestBodyJson, HttpServletResponse resp) throws IOException {

    }

    @Override
    protected void doDeleteOperation(Map mapParams, HttpServletResponse resp) throws IOException {

    }
}
