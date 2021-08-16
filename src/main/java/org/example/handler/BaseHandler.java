package org.example.handler;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.transport.layered.TFramedTransport;
import org.example.config.Config;
import org.example.gen.SongService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class BaseHandler extends HttpServlet {
    protected SongService.Client client;
    private Map config = Config.getInstance().getConfig();
    public BaseHandler() throws TTransportException {
        int port = Integer.parseInt((String) config.get("thriftPort"));
        TTransport transport = new TFramedTransport(new TSocket((String) config.get("thriftHost"), port));
        transport.open();
        TProtocol protocol = new TBinaryProtocol(transport);
        client = new SongService.Client(protocol);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        _setResponseType(resp);
        try {
            Map mapParams = _getAllParameter(req);
            doGetOperation(mapParams, resp);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected abstract void doGetOperation(Map mapParams, HttpServletResponse resp) throws IOException;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        _setResponseType(resp);
        try {
            Map mapParams = _getAllParameter(req);
            JsonObject requestBodyJson = _getBodyJson(req);
            doPostOperation(mapParams, requestBodyJson, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected abstract void doPostOperation(Map mapParams, JsonObject requestBodyJson, HttpServletResponse resp) throws IOException;

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        _setResponseType(resp);
        try {
            Map mapParams = _getAllParameter(req);
            JsonObject requestBodyJson = _getBodyJson(req);
            doPutOperation(mapParams, requestBodyJson, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract void doPutOperation(Map mapParams, JsonObject requestBodyJson, HttpServletResponse resp) throws IOException;

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        _setResponseType(resp);
        try {
//            Map mapParams = getBody(req);
            JsonObject requestBodyJson = _getBodyJson(req);
            doDeleteOperation(requestBodyJson, resp);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected abstract void doDeleteOperation(JsonObject mapParams, HttpServletResponse resp) throws IOException;

    private Map _getAllParameter(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        Set<String> paramNames = request.getParameterMap().keySet();
        for (String name : paramNames) {
            String value = request.getParameter(name);
            map.put(name, value);
        }
        return map;
    }

    private void _setResponseType(HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
    }

    protected void _printResponse(int status, String message, HttpServletResponse resp, PrintWriter out) {
        resp.setStatus(status);
        out.print(message);
    }

    //get json object from request
    protected JsonObject _getBodyJson(HttpServletRequest request) throws IOException {
        JsonObject requestJson = null;
        Reader reqReader = request.getReader();
        JsonParser parser = new JsonParser();
        if (reqReader.ready()) {
            requestJson = (JsonObject) parser.parse(reqReader);
//            requestJson = (JsonObject) JsonParser.parseReader(reqReader).getAsJsonObject();;
        }
        return requestJson;
    }

    public Map<String, String> getBody(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception ex) {
            sb.setLength(0);
        }

        String para = sb.toString();
        Map<String, String> res = new HashMap<>();
        for (String i : para.split("&")) {
            String[] t = i.split("=");
            System.out.println(t[0]);
            System.out.println(t[1]);
            res.put(t[0], t[1]);
        }
        return res;
    }


}
