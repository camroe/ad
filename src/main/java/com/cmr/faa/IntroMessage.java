package com.cmr.faa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class IntroMessage {

    @Value("${name:Unknown}")
    private String applicationName;

    @Value("${displayBeans:false}")
    private String displayBeans;

    @Autowired
    private ApplicationContext applicationContext;

    public String getApplicationName() {
        return applicationName;
    }

    public boolean isDisplaySpringBeans() {
        return (displayBeans.equals("true"));
    }

    public void printBeans() {
        String[] beans = applicationContext.getBeanDefinitionNames();
        Arrays.sort(beans);
        System.out.println("---------S P R I N G  B E A N S ---------------------------------");
        for (String bean : beans) {
            System.out.println(bean);
        }
        System.out.println("----------------------------------------------------------------");
    }
}
