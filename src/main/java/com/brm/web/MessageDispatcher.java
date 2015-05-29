package com.brm.web;

import org.apache.camel.Body;
import org.apache.camel.Header;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class MessageDispatcher {

    @Autowired
    ApplicationContext applicationContext;

    public Map dispatch(@Header("CamelHttpUri") String serviceUri,
                        @Body Map<String, Object> param) {

        Map<String, Object> result = null;

        Performer performer;

        try {
            performer = applicationContext.getBean(serviceUri, Performer.class);
            try {

                result = performer.service(param);
                result.put("status", "ServiceOk");
            } catch (Exception e) {
                result = new HashMap<String, Object>();
                result.put("status", "ServiceFail");
                result.put("reply", e.getMessage());
            }
        } catch (NoSuchBeanDefinitionException e) {
            result = new HashMap<String, Object>();
            result.put("status", "DispatcherFail");
            result.put("reply", "Not found: " + serviceUri);
        } catch (BeansException e) {
            result = new HashMap<String, Object>();
            result.put("status", "DispatcherFail");
            result.put("reply", "Get Bean Fail: " + e.getMessage());
        }
        return result;
    }
}
