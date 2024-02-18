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
public class ExchangeController {
    @GetMapping("/exchange")
    public String exchange() {
        String usd = "";
        try (InputStream stream = new URI("http://api.currencylayer.com/live?access_key=19ee0ebf14893805de96f9c87f2a3048&source=USD&currencies=RUB").toURL().openStream()) {
            BufferedReader bfr = new BufferedReader(new InputStreamReader(stream));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = bfr.readLine()) != null) {
                content.append(line);
            }
            usd = content.toString();
        } catch (IOException | URISyntaxException e) {
            return "An error occurred while accessing the API USD";
        }
        String eur = "";
        try (InputStream stream = new URI("http://api.currencylayer.com/live?access_key=19ee0ebf14893805de96f9c87f2a3048&source=EUR&currencies=RUB").toURL().openStream()) {
            BufferedReader bfr = new BufferedReader(new InputStreamReader(stream));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = bfr.readLine()) != null) {
                content.append(line);
            }
            eur = content.toString();
        } catch (IOException | URISyntaxException e) {
            return "An error occurred while accessing the API EUR";
        }

        StringBuilder result = new StringBuilder("Current currency:<br>");
        if (!usd.isEmpty()) {
            try {
                JSONObject object = new JSONObject(usd);
                JSONObject quotes = object.getJSONObject("quotes");
                for (String key : quotes.keySet()) {
                    double value = quotes.getDouble(key);
                    result.append("1 USD -> ").append(value).append(" RUB<br>");
                }
            } catch (JSONException e) {
                return "An error occurred while processing JSON USD";
            }

        }


        if (!eur.isEmpty()) {
            try {
                JSONObject object = new JSONObject(eur);
                JSONObject quotes = object.getJSONObject("quotes");
                for (String key : quotes.keySet()) {
                    double value = quotes.getDouble(key);
                    result.append("1 EUR -> ").append(value).append(" RUB");
                }
            } catch (JSONException e) {
                return "An error occurred while processing JSON EUR";
            }
        }

        return result.toString();
    }
}
