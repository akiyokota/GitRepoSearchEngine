package server.util;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by ayokota on 11/16/17.
 */
public class JSONSerializer {

    private static Gson gson = null;

    private static Gson getGson() {
        if(gson == null) {
            gson = new Gson();
        }
        return gson;
    }

    public static <T> String serialize(T t) {
        return getGson().toJson(t);
    }

    public static <T> T deserialize (String json, Class<T> cls) {
        return getGson().fromJson(json, cls);
    }

    public static <T> T deserialize (String json, Type typeToken) {
        return getGson().fromJson(json, typeToken);
    }

}
