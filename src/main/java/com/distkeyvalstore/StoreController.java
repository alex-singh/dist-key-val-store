package com.distkeyvalstore;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class StoreController {

    @Autowired
    StoreService service;

    @GetMapping("/values/{key}")
    public ResponseEntity<String> getValue(@PathVariable String key) {
        String value = service.getValue(key);
        if (value != null){
            return new ResponseEntity<>(
                    value, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/values/{key}")
    ResponseEntity putValue(@RequestBody String payload, @PathVariable String key){
        String value;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(payload);
            value = jsonNode.get("value").asText();
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        service.putValue(key, value);
        service.synchroniseNodes(key, value);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("private/values/{key}")
    void putValueInNodeOnly(@RequestBody String value, @PathVariable String key){
        service.putValue(key, value);
    }
}
