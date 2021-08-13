package org.example.handler;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import org.example.cache.Cache;
import org.example.gen.ReturnCode;
import org.example.gen.Song;
import org.example.gen.SongResponse;
import org.example.model.CreateObjectResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SongHandler extends BaseHandler {
    Cache cache = Cache.getInstance();

    public SongHandler() throws TTransportException {

    }

    private List<String> _jsonArrayToList(JsonArray jsonArray) {
        List<String> result = new ArrayList<>();
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.size(); i++) {
                result.add(jsonArray.get(i).getAsString());
            }
        }
        return result;
    }

    @Override
    protected void doDeleteOperation(Map mapParams, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();

        String requestId = (String) mapParams.get("id");
        if (requestId == null) {
            String message = new Gson().toJson(new SongResponse(HttpServletResponse.SC_BAD_REQUEST, null));
            _printResponse(HttpServletResponse.SC_BAD_REQUEST, message, resp, out);
            return;
        }

        int songId = Integer.parseInt(requestId);
        try {
            ReturnCode returnCode = client.removeSong(songId);
            if (returnCode.getValue() == 406) {
                String message = new Gson().toJson(new CreateObjectResponse(HttpServletResponse.SC_NOT_ACCEPTABLE, "id not valid"));
                _printResponse(HttpServletResponse.SC_NOT_ACCEPTABLE, message, resp, out);
                return;
            }

            String message = new Gson().toJson(new CreateObjectResponse(HttpServletResponse.SC_OK, "remove successfully"));
            _printResponse(HttpServletResponse.SC_OK, message, resp, out);
        } catch (TException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGetOperation(Map mapParams, HttpServletResponse resp) throws IOException {
        System.out.println("do get action in song handler");
        PrintWriter out = resp.getWriter();
        String requestId = (String) mapParams.get("id");

        if (requestId == null) {
            String message = new Gson().toJson(new SongResponse(HttpServletResponse.SC_BAD_REQUEST, null));
            _printResponse(HttpServletResponse.SC_BAD_REQUEST, message, resp, out);
            return;
        }

        try {
            int songId = Integer.parseInt(requestId);
            SongResponse songResponse;
            //check if cache contain song and get from cache
            if (cache.isContainKey(songId)) {
                Song song = cache.get(songId);
                songResponse = new SongResponse(200, song);
            } else {
                // get song from song service
                songResponse = client.getSong(songId);
                //if successfully get song, put in cache
                if (songResponse.getCode() == 200) {
                    cache.add(songResponse.getSong());
                }
            }

            if (songResponse.getCode() == 406) {
                String message = new Gson().toJson(new SongResponse(HttpServletResponse.SC_NOT_ACCEPTABLE, null));
                _printResponse(HttpServletResponse.SC_NOT_ACCEPTABLE, message, resp, out);
                return;
            }

            String message =  new Gson().toJson(songResponse);
            _printResponse(HttpServletResponse.SC_OK, message, resp, out);

        } catch (TException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPostOperation(Map mapParams, JsonObject requestBodyJson, HttpServletResponse resp) throws IOException {
        System.out.println("do post action in song handler");
        PrintWriter out;
        out = resp.getWriter();

        JsonArray jsonArray = (JsonArray) requestBodyJson.get("singer");
        List<String> singers = _jsonArrayToList(jsonArray);

        Song song = new Song(0, requestBodyJson.get("title").getAsString(), singers);
        try {
            ReturnCode returnCode = client.addSong(song);
            String message = new Gson().toJson(new CreateObjectResponse(returnCode.getValue(), "created successfully"));
            _printResponse(HttpServletResponse.SC_OK, message, resp, out);
        } catch (TException e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void doPutOperation(Map mapParams, JsonObject requestBodyJson, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();

        JsonArray jsonArray = (JsonArray) requestBodyJson.get("singer");
        List<String> singers = _jsonArrayToList(jsonArray);

        Song song = new Song(
                Integer.parseInt(requestBodyJson.get("id").getAsString()),
                requestBodyJson.get("title").getAsString(),
                singers
        );
        try {
            ReturnCode returnCode = client.updateSong(song);
            if (returnCode.getValue() == 406) {
                String message = new Gson().toJson(new CreateObjectResponse(returnCode.getValue(), "invalid song"));
                _printResponse(HttpServletResponse.SC_NOT_ACCEPTABLE, message, resp, out);
                return;
            }

            String message = new Gson().toJson(new CreateObjectResponse(returnCode.getValue(), "updated successfully"));
            _printResponse(HttpServletResponse.SC_OK, message, resp, out);

        } catch (TException e) {
            e.printStackTrace();
        }
    }
}
