package itmo.abroskin.wst.juddi;

import java.util.HashMap;
import java.util.Map;

public class EndpointSelector {
    private Map<String, String> endpoints = new HashMap<String, String>();

    public String getEndpoint(String key) {
        return endpoints.get(key);
    }

    public void setEndpoint(String key, String url) {
        endpoints.put(key, url);
    }
}

