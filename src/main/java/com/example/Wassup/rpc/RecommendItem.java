package com.example.Wassup.rpc;

import com.example.Wassup.entity.Item;
import com.example.Wassup.recommendation.GeoRecommendation;
import org.json.JSONArray;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "RecommendItem", value = "/recommendation")
public class RecommendItem extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("user_id");
        double lat = Double.parseDouble(request.getParameter("lat"));
        double lon = Double.parseDouble(request.getParameter("lon"));

        GeoRecommendation geoRecommendation = new GeoRecommendation();
        List<Item> items = geoRecommendation.recommendItems(userId, lat, lon);
        JSONArray array = new JSONArray();
        for(Item item : items) {
            array.put(item.toJSONObject());
        }
        RpcHelper.writeJsonArray(response, array);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
