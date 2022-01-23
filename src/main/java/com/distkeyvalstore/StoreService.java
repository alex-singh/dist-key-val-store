package com.distkeyvalstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class StoreService {

    private final Environment environment;

    @Value("${node-cluster-addresses}")
    private List<String> nodeClusterAddresses;

    private final Map<String, String> store = new HashMap<String, String>();

    private final HttpNodeClient httpClient = new HttpNodeClient();

    @Autowired
    StoreService(Environment environment){
        this.environment = environment;
    }

    String getValue(String key){
        return store.get(key);
    }

    void putValue(String key, String value){
        store.put(key, value);
        System.out.println("put at port: " + getServerPort());
        System.out.println("key: " + key);
        System.out.println("value: " + value);
        System.out.println();
    }

    void synchroniseNodes(String key, String value){
        List<String> addresses = getNodeClusterAddresses().stream().filter(a -> !a.equals(getNodeAddress())).collect(Collectors.toList());
        httpClient.putRequests(addresses, value, key);
    }


    List<String> getNodeClusterAddresses(){
        return nodeClusterAddresses;
    }

    private String getNodeAddress(){
        return "http://localhost:" + getServerPort();
    }

    private String getServerPort(){
        return this.environment.getProperty("local.server.port");
    }
}
