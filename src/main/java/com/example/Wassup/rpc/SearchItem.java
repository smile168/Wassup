package com.example.Wassup.rpc;

import com.example.Wassup.db.DBConnection;
import com.example.Wassup.db.DBConnectionFactory;
import com.example.Wassup.entity.Item;
import org.json.JSONArray;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

// test: localhost:8080/Wassup/search?lat=37.38&lon=-122.08
@WebServlet(name = "SearchItem", value = "/search")
public class SearchItem extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public SearchItem() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        double lat = Double.parseDouble(request.getParameter("lat"));
        double lon = Double.parseDouble(request.getParameter("lon"));

        // Term can be empty or null.
        String term = request.getParameter("term");
        DBConnection connection = DBConnectionFactory.getConnection();
        try {
            List<Item> items = connection.searchItems(lat, lon, term);
            JSONArray array = new JSONArray();
            for (Item item : items) {
                array.put(item.toJSONObject());
            }
            RpcHelper.writeJsonArray(response, array);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }

    }


    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }
}
