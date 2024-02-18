package ru.kpfu.itis.lldan.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class WeatherController {
    @GetMapping("/weather")
    public String weather() {
        String json;
        try (InputStream stream = new URI("https://api.openweathermap.org/data/2.5/weather?q=Kazan&appid=0c9d371ebde9e8af7cf225e608128f8d").toURL().openStream()) {
            BufferedReader bfr = new BufferedReader(new InputStreamReader(stream));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = bfr.readLine()) != null) {
                content.append(line);
            }
            json = content.toString();
        } catch (IOException | URISyntaxException e) {
            return "An error occurred while accessing the API";
        }

        try {
            JSONObject object = new JSONObject(json);
            double temp = Math.round(object.getJSONObject("main").getDouble("temp") - 273);
            double humidity = object.getJSONObject("main").getDouble("humidity");
            String description = object.getJSONArray("weather").getJSONObject(0).getString("main");
            return "Weather in Kazan [" + description + "]:<br>Temperature: " + temp + " °C<br>Humidity: " + humidity + " %";
        } catch (JSONException e) {
            return "An error occurred while processing JSON";
        }

    }
}
