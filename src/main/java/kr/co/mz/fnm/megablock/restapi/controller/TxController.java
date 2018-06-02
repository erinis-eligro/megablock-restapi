package kr.co.mz.fnm.megablock.restapi.controller;

import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/tx")
public class TxController {
    private static String baseUrl = "http://13.209.49.229:9000";

    @RequestMapping(value = "/invoke", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public String makeTx(@RequestBody String body) {
        String rBody = body;
        RestTemplate restTemplate = new RestTemplate();
        HashMap<String ,Object> returnJsonMap = new HashMap<>();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity param= new HttpEntity(rBody, headers);

        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/api/v1/transactions").build().toUri();
        String response = restTemplate.postForObject(uri,param, String.class);

        JsonParser jsonParser = new BasicJsonParser();
        Map<String, Object> jsonMap = jsonParser.parseMap(response);
//        Map<String, Object> hashDetailMap = this.getTxfromHash(jsonMap.get("tx_hash"));
//
//        returnJsonMap.put("tx_hash",jsonMap.get("tx_hash"));
//        returnJsonMap.put("data",hashDetailMap.get("data"));
//        returnJsonMap.put("meta",hashDetailMap.get("meta"));
//        returnJsonMap.put("signature",hashDetailMap.get("signature"));

        return (String)jsonMap.get("tx_hash");
    }

    private Map<String, Object> getTxfromHash(Object hash){
        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/api/v1/transactions").queryParam("hash",hash).build().toUri();
        String response = restTemplate.getForObject(uri,String.class);

        JsonParser jsonParser = new BasicJsonParser();
        Map<String, Object> jsonMap = jsonParser.parseMap(response);

        return jsonMap;

    }

    @RequestMapping(value = "/blocks", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Map<String, Object> blocks() {
        RestTemplate restTemplate = new RestTemplate();

        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/api/v1/blocks").build().toUri();
        String response = restTemplate.getForObject(uri, String.class);

        JsonParser jsonParser = new BasicJsonParser();
        Map<String, Object> jsonMap = jsonParser.parseMap(response);

        return this.getblockfromHash(jsonMap.get("block_hash"));

    }

    private Map<String, Object> getblockfromHash(Object hash){
        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path("/api/v1/blocks").queryParam("hash",hash).build().toUri();
        String response = restTemplate.getForObject(uri,String.class);

        JsonParser jsonParser = new BasicJsonParser();
        Map<String, Object> jsonMap = jsonParser.parseMap(response);

        return jsonMap;

    }

}
