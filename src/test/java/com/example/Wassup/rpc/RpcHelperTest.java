package com.example.Wassup.rpc;

import com.example.Wassup.entity.Item;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class RpcHelperTest {
    @Test
    public void testGetJSONArray() throws JSONException {
        Set<String> category = new HashSet<String>();
        category.add("category one");

        List<Item> listItem = new ArrayList<Item>();
        JSONArray jsonArray = new JSONArray();
        JSONAssert.assertEquals(jsonArray, RpcHelper.getJSONArray(listItem), true);

        Item.ItemBuilder builderOne = new Item.ItemBuilder();
        builderOne.setItemId("one");
        builderOne.setRating(3.8);
        builderOne.setCategories(category);
        Item one = builderOne.build();
        Item.ItemBuilder builderTwo = new Item.ItemBuilder();
        builderTwo.setItemId("one");
        builderTwo.setRating(3.8);
        builderTwo.setCategories(category);
        Item two = builderTwo.build();
        listItem.add(one);
        listItem.add(two);

        jsonArray.put(one.toJSONObject());
        jsonArray.put(two.toJSONObject());
        JSONAssert.assertEquals(jsonArray, RpcHelper.getJSONArray(listItem), true);

//        Item empty = new Item.ItemBuilder().build();
//        jsonArray.put(empty);
//        JSONAssert.assertEquals(jsonArray, RpcHelper.getJSONArray(listItem), true);

    }

}