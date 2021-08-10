package org.example.handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import org.example.model.ActionResponse;
import org.example.model.CreateObjectResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class SongLikeHandler extends BaseHandler {


    public SongLikeHandler() throws TTransportException {
    }

    @Override
    protected void doGetOperation(Map mapParams, HttpServletResponse resp) throws IOException {

    }

    @Override
    protected void doPostOperation(Map mapParams, JsonObject requestBodyJson, HttpServletResponse resp) throws IOException {

    }

    @Override
    protected void doPutOperation(Map mapParams, JsonObject requestBodyJson, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();

        String requestId = (String) mapParams.get("id");
        String action = (String) mapParams.get("action");
//        String numLike= req.getParameter("numlike");

        if (requestId == null || action == null) {
            String message = new Gson().toJson(new CreateObjectResponse(HttpServletResponse.SC_BAD_REQUEST, "not sufficient parameter"));
            _printResponse(HttpServletResponse.SC_BAD_REQUEST, message, resp, out);
            return;
        }

        int songId = Integer.parseInt(requestId);
//        int numberOfLike = Integer.parseInt(numLike);
        if (action.equals("like")) {
            try {
                client.performLike(songId);
                String message = new Gson().toJson(new ActionResponse(200, "like", "liked successfully"));
                _printResponse(HttpServletResponse.SC_OK, message, resp, out);
                return;
            } catch (TException e) {
                e.printStackTrace();
                String message = new Gson().toJson(new ActionResponse(500, "like", "something wrong!"));
                _printResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message, resp, out);
                return;
            }
        }

        if (action.equals("unlike")) {
            try {
                client.performUnlike(songId);
                String message = new Gson().toJson(new ActionResponse(200, "unlike", "unliked successfully"));
                _printResponse(HttpServletResponse.SC_OK, message, resp, out);
            } catch (TException e) {
                e.printStackTrace();
                String message = new Gson().toJson(new ActionResponse(500, "unlike", "something wrong!"));
                _printResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message, resp, out);
            }
        }

        String message = new Gson().toJson(new ActionResponse(400, "unknow", "action not support!"));
        _printResponse(HttpServletResponse.SC_BAD_REQUEST, message, resp, out);
    }

    @Override
    protected void doDeleteOperation(Map mapParams, HttpServletResponse resp) throws IOException {

    }

}
