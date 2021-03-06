package com.brm.web.service;

import com.brm.web.Performer;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("/TwitterService")
public class TwitterService implements Performer {

    @Produce(uri="direct:twitter")
    ProducerTemplate producer;

    @Override
    public Map<String, Object> service(Map<String, Object> param) throws Exception {
        String tweet = (String) param.get("tweet") + " Hey";

        producer.sendBody(tweet);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("reply", "Sent the tweet, [" + tweet + "]");
        return result;
    }
}
