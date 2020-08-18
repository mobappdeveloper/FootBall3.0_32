package com.footballio.retrofit;

import com.footballio.model.workout.type.WarmUpItem;
import com.footballio.model.workout.type.WorkoutTypeViewResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ArrayOfObjectsToMapDeserializer implements JsonDeserializer<WorkoutTypeViewResponse> {


    @Override
    public WorkoutTypeViewResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        WorkoutTypeViewResponse result = new WorkoutTypeViewResponse();


        try {
            final HashMap<String, WarmUpItem> map = readServiceUrlMap(json.getAsJsonObject());

            if (map != null) {
                result.dataValues = map;
            }

        } catch (JsonSyntaxException ex) {
            return null;
        }

        return result;
    }


    private HashMap<String, WarmUpItem> readServiceUrlMap(final JsonObject jsonObject) throws JsonSyntaxException {

        if (jsonObject == null) {
            return null;
        }
        Gson gson = new Gson();

        HashMap<String, WarmUpItem> products = new HashMap<>();

        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {

            String key = entry.getKey();
            WarmUpItem value = gson.fromJson(entry.getValue(), WarmUpItem.class);
            products.put(key, value);
        }
        return products;
    }
}

