package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        try {
                 /* Get the JSON object representing the sandwich */
            JSONObject sandwichJson = new JSONObject(json);

            JSONObject name = sandwichJson.getJSONObject("name");
            String mainName = name.getString("mainName");
            JSONArray alsoKnownAs = name.getJSONArray("alsoKnownAs");

            //Go thru the also known as array and add to sandwich details
            ArrayList<String> alsoKnownAsObject = new ArrayList<>();
            for (int i = 0; i < alsoKnownAs.length(); i++) {
                alsoKnownAsObject.add(alsoKnownAs.getString(i));
            }

                /* These are the values that will be collected */
            String placeOfOrigin = sandwichJson.getString("placeOfOrigin");
            String description = sandwichJson.getString("description");
            String image = sandwichJson.getString("image");
            JSONArray ingredients = sandwichJson.getJSONArray("ingredients");

            //Go thru the array of ingredients and add it to the sandwich details
            ArrayList<String> ingredientsObject = new ArrayList<>();
            for (int i = 0; i < ingredients.length(); i++) {
                ingredientsObject.add(ingredients.getString(i));
            }

            // return sandwich details
            return new Sandwich(mainName, alsoKnownAsObject, placeOfOrigin, description, image, ingredientsObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
