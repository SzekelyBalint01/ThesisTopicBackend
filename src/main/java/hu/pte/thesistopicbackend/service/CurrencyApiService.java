package hu.pte.thesistopicbackend.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CurrencyApiService {

    public int exchange(String[] args) {
        try {
            String apiKey = "cur_live_jBVugcLcA1DxNELKVZhrk9OVTc9nhf2KNOCvIG2a";
            String baseCurrency = "USD";
            String targetCurrencies = "HUF";


            // Az új URL összeállítása az új paraméterekkel
            String url = String.format("https://api.currencyapi.com/v3/latest?apikey=%s&base_currency=%s&currencies=%s",
                    apiKey, baseCurrency, targetCurrencies,25);

            URL apiUrl = new URL(url);
            HttpURLConnection request = (HttpURLConnection) apiUrl.openConnection();
            request.connect();

            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonObject jsonobj = root.getAsJsonObject();

            String req_result = jsonobj.get("result").getAsString();

            System.out.println("API válasz: " + req_result);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
