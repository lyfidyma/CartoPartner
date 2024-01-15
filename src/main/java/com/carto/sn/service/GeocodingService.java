package com.carto.sn.service;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class GeocodingService {
	private final OkHttpClient httpClient = new OkHttpClient();

    public JSONObject search(String regionGeocod, String paysGeocod) throws IOException {
    	
        Request request = new Request.Builder()
            .url("https://nominatim.openstreetmap.org/search?format=json&city=" + regionGeocod+"&country="+paysGeocod)
            .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            JSONArray jsonArray = new JSONArray(response.body().string());

            if(jsonArray.length() > 0) {
                return jsonArray.getJSONObject(0);
            }

            return null;
        }
    }

}
