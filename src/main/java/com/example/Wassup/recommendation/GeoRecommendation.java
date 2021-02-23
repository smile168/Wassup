package com.example.Wassup.recommendation;

import com.example.Wassup.db.DBConnection;
import com.example.Wassup.db.DBConnectionFactory;
import com.example.Wassup.entity.Item;

import java.util.*;

public class GeoRecommendation {
    public List<Item> recommendItems (String userId, double lat, double lon) {
        List<Item> recommendedItems = new ArrayList<>();
        DBConnection dbConnection = DBConnectionFactory.getConnection();
        Set<String> favoriateItemIds = dbConnection.getFavoriteItemIds(userId);

        // key: event type, value: current count
        Map<String, Integer> allCategories = new HashMap<>();
        for(String itemId : favoriateItemIds) {
            Set<String> categories = dbConnection.getCategories(itemId);
            for(String category : categories) {
                allCategories.put(category, allCategories.getOrDefault(category, 0) + 1);
            }
        }

        List<Map.Entry<String, Integer>> categoryList = new ArrayList<>(allCategories.entrySet());
        Collections.sort(categoryList, (Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) -> {
            return Integer.compare(e2.getValue(), e1.getValue());
        });

        Set<String> visitedItemIds = new HashSet<>();
        for (Map.Entry<String, Integer> categoryEntry : categoryList) {
            List<Item> items = dbConnection.searchItems(lat, lon, categoryEntry.getKey());

            for(Item item : items) {
                if(!favoriateItemIds.contains(item.getItemId()) && !visitedItemIds.contains(item.getItemId())) {
                    recommendedItems.add(item);
                    // dedup
                    visitedItemIds.add(item.getItemId());
                }
            }
        }

        dbConnection.close();
        return recommendedItems;
    }

}
