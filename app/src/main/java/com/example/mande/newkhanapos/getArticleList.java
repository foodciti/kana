package com.example.mande.newkhanapos;

import android.util.ArraySet;

import com.example.mande.newkhanapos.models.Article;
import com.example.mande.newkhanapos.models.MenuItemModifierGroup;
import com.example.mande.newkhanapos.models.MenuModifier;
import com.example.mande.newkhanapos.models.MenuModifierGroup;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

public class getArticleList extends MainActivity {

    public void getlist()
    {
        ServeraddrApi = "/item/all?type=false";
        new NetworkConnectionTask().execute(ServeraddrHeader + Serveraddr + ServeraddrApi);
    }

    @Override
    public void getJson(StringBuffer buffer)
    {
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(buffer.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int x;
        try {
            x = jsonArray.length();
        }
        catch(Exception e)
        {
            x = 0;
        }

        for (int i = 0; i < x; i++) {
            JSONObject jsonobject = null;

            try {
                jsonobject = jsonArray.getJSONObject(i);
                Article article = new Article();
                article.setItemId(jsonobject.getString("itemId"));
                article.setName(jsonobject.getString("name"));
                article.setPrice(jsonobject.getDouble("price"));


                JSONArray jsonArray1 = jsonobject.getJSONArray("menuItemModiferGroups");

                if(jsonArray1.length()>0) {

                    JSONObject jsonObject1 = jsonArray1.getJSONObject(0);
                    JSONObject jsonObject2 = jsonObject1.getJSONObject("modifierGroup");

                    JSONArray jsonArray2 = jsonObject2.getJSONArray("modifiers");

                    Set<MenuModifier> newmodifiers = new HashSet();
                    MenuModifierGroup newMenuModifierGroup = new MenuModifierGroup();
                    MenuItemModifierGroup newMenuItemModifierGroup = new MenuItemModifierGroup();

                    for (int j = 0; j < jsonArray2.length(); j++) {
                        JSONObject jsonobject3 = jsonArray2.getJSONObject(j);

                        MenuModifier newMenuModifier = new MenuModifier();

                        newMenuModifier.setId(jsonobject3.getInt("id"));
                        newMenuModifier.setName(jsonobject3.getString("name"));
                        newMenuModifier.setPrice(jsonobject3.getDouble("price"));
                        newMenuModifier.setExtraPrice(jsonobject3.getDouble("extraPrice"));
                        newmodifiers.add(newMenuModifier);
                    }

                    newMenuModifierGroup.setModifiers(newmodifiers);
                    newMenuItemModifierGroup.setModifierGroup(newMenuModifierGroup);
                    article.setMenuItemModiferGroups(newMenuItemModifierGroup);
                }
                ArticleList.add(article);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
