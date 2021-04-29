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
public class StockController {
    
    /* Hits stock api with the company keyword & returns stock graph */
    @CrossOrigin(origins = "**", allowedHeaders = "*")
    @RequestMapping(value = "/getStockDetail.htm", method = RequestMethod.GET)
    public ResponseEntity<String> getStockDetail(@RequestParam String stockName) throws JSONException {
        String urlString = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY_ADJUSTED&symbol="+stockName+"&outputsize=compact&apikey=SLIIE11N2DCT0QUM";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(urlString, String.class);
        JSONObject root = new JSONObject(result);
        return new ResponseEntity<>(root.toString(), HttpStatus.OK);
    }
   
}
