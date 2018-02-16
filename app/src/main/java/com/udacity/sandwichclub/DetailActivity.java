package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        TextView alsoKnownAs = findViewById(R.id.also_known_tv);
        TextView placeOfOrigin = findViewById(R.id.origin_tv);
        TextView description = findViewById(R.id.description_tv);
        TextView ingredients = findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
        ingredients.setText(sandwich.getIngredients().toString());
        alsoKnownAs.setText(sandwich.getAlsoKnownAs().toString());
        description.setText(sandwich.getDescription());
        placeOfOrigin.setText(sandwich.getPlaceOfOrigin());

        placeOfOrigin.setText(noInfo(sandwich.getPlaceOfOrigin()));
        description.setText(noInfo(sandwich.getDescription()));

        List<String> alsoKnownAsSandwich = sandwich.getAlsoKnownAs();
        String removeBrackets = "";
        for (String string : alsoKnownAsSandwich) {
            removeBrackets += string + ", ";
        }
        if (removeBrackets.length() > 0) {
            removeBrackets = removeBrackets.substring(0, removeBrackets.length() - 2);
        }
        alsoKnownAs.setText(noInfo(removeBrackets));

        removeBrackets = "";
        List<String> ingredientList = sandwich.getIngredients();
        for (String s : ingredientList) {
            removeBrackets += s + "\n";
        }
        ingredients.setText(noInfo(removeBrackets));
    }

    private String noInfo(String string) {
        if (string.equals("")) {
            return getString(R.string.detail_error_message);
        } else {
            return string;
        }

    }

    private void populateUI() {

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

}

