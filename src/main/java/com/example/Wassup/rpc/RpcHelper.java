package com.example.Wassup.rpc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.Wassup.entity.Item;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class RpcHelper {
    public static void writeJsonArray(HttpServletResponse response, JSONArray array) throws IOException{
        response.setContentType("application/json");
        response.getWriter().print(array);
    }
    public static void writeJsonObject(HttpServletResponse response, JSONObject obj) throws IOException{
        response.setContentType("application/json");
        response.getWriter().print(obj);
    }

    // Parses a JSONObject from http request.
    public static JSONObject readJSONObject(HttpServletRequest request) {
        StringBuilder sBuilder = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line = null;
            while((line = reader.readLine()) != null) {
                sBuilder.append(line);
            }
            return new JSONObject(sBuilder.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new JSONObject();
    }

    // for unit test, convert a list of item objs to json array
    public static JSONArray getJSONArray(List<Item> items) {
        JSONArray array = new JSONArray();
        try {
            for (Item item: items) {
                array.put(item.toJSONObject());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return array;
    }
}
