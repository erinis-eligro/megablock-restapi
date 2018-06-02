package kr.co.mz.fnm.megablock.restapi.controller;

import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping(value = "/rs")
public class RSController {
    private static String baseUrl = "http://13.67.92.148:9002";

    @RequestMapping(value = "/peer/list", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Map<String, Object> rsPeerList() {
        RestTemplate restTemplate = new RestTemplate();

        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/api/v1/peer/list").build().toUri();
        String response = restTemplate.getForObject(uri, String.class);

        JsonParser jsonParser = new BasicJsonParser();
        Map<String, Object> jsonMap = jsonParser.parseMap(response);

        return jsonMap;
    }
}
