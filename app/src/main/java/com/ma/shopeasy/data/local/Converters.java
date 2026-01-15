package com.ma.shopeasy.data.local;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ma.shopeasy.domain.model.FAQItem;

import java.lang.reflect.Type;
import java.util.List;

public class Converters {
    @TypeConverter
    public static List<FAQItem> fromString(String value) {
        Type listType = new TypeToken<List<FAQItem>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<FAQItem> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
