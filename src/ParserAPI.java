import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ParserAPI {
    String address;
    MeasurementJSON measurementJSON;

    public ParserAPI(String url) {
        this.address = url;
    }

    public MeasurementJSON downloadJSON() throws IOException {

        try {
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            int code = connection.getResponseCode();
            String errorMsg = "";

            switch (code) {
                case 400:
                    errorMsg = "Input validation error";
                    break;
                case 401:
                    errorMsg = "No API key found in headers or querystring";
                    break;
                case 403:
                    errorMsg = "Invalid authentication credentials (wrong apikey)";
                    break;
                case 404:
                    errorMsg = "Not found";
                    break;
                case 500:
                    errorMsg = "Unexpected error";
                    break;
            }

            if (!errorMsg.equals("")) throw new IOException(errorMsg);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));

            Gson gson = new GsonBuilder().create();

            measurementJSON = gson.fromJson(bufferedReader, MeasurementJSON.class);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return measurementJSON;
    }
}
