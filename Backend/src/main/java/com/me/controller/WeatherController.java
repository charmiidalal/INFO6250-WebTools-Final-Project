/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.me.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author charmidalal
 */
@CrossOrigin(origins = "**", allowedHeaders = "*")
@Controller
public class WeatherController {

    /* Fetches weather by zipcode */
    @CrossOrigin(origins = "**", allowedHeaders = "*")
    @RequestMapping(value = "/WeatherByZipcode.htm", method = RequestMethod.GET)
    public ResponseEntity<String> weatherByZipcode(@RequestParam String zipcode) throws JSONException {
        String urlString = "https://api.openweathermap.org/data/2.5/forecast?zip=" + zipcode + "&appid=7ec4719c8fca5b9800a651e5991ca88d";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(urlString, String.class);
        JSONObject root = new JSONObject(result);
        return new ResponseEntity<>(root.toString(), HttpStatus.OK);
    }

    /* Feches weather by geo location coordinates */
    @CrossOrigin(origins = "**", allowedHeaders = "*")
    @RequestMapping(value = "/WeatherByLocation.htm", method = RequestMethod.GET)
    public ResponseEntity<String> weatherByLocation(@RequestParam String lat, @RequestParam String lon) throws JSONException {
        String urlString = "https://api.openweathermap.org/data/2.5/forecast?lat=" + lat + "&lon=" + lon + "&appid=7ec4719c8fca5b9800a651e5991ca88d";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(urlString, String.class);
        JSONObject root = new JSONObject(result);
        return new ResponseEntity<>(root.toString(), HttpStatus.OK);
    }
}
