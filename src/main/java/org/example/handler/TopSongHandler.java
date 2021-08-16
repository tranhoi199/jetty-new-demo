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

public class TopSongHandler extends BaseHandler {
    public TopSongHandler() throws TTransportException {
    }

    @Override
    protected void doGetOperation(Map mapParams, HttpServletResponse resp) throws IOException {
        System.out.println("in get top song");
        PrintWriter out = resp.getWriter();

        String type = (String) mapParams.get("type");
        String topNumber = (String) mapParams.get("topnumber");

        if (type == null) {
            String listSongResponse = new Gson().toJson(new ListSongResponse(400, null, "invalid request"));
            _printResponse(HttpServletResponse.SC_BAD_REQUEST, listSongResponse, resp, out);
            return;
        }

        int numberOfResult = topNumber == null ? 10 : Integer.parseInt(topNumber);

        if (type.equals("toplike")) {
            try {
                org.example.gen.ArtistListSongResponse topSongBaseOnLike = client.getTopSongBaseOnLike(numberOfResult);
                _printResponse(HttpServletResponse.SC_OK, new Gson().toJson(topSongBaseOnLike), resp, out);
                return;
            } catch (TException e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                e.printStackTrace();
            }
        }

        if (type.equals("toplisten")) {
            try {
                ArtistListSongResponse topSongBaseOnListen = client.getTopSongBaseOnListen(numberOfResult);
                _printResponse(HttpServletResponse.SC_OK, new Gson().toJson(topSongBaseOnListen), resp, out);
            } catch (TException e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPostOperation(Map mapParams, JsonObject requestBodyJson, HttpServletResponse resp) throws IOException {

    }

    @Override
    protected void doPutOperation(Map mapParams, JsonObject requestBodyJson, HttpServletResponse resp) throws IOException {

    }

    @Override
    protected void doDeleteOperation(JsonObject mapParams, HttpServletResponse resp) throws IOException {

    }
}
