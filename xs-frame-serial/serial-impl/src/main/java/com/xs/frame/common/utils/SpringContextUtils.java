package com.xs.frame.common.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Administrator on 2015/7/1.
 */
@Service
public class SpringContextUtils implements ApplicationContextAware {
    private static final Log log = LogFactory.getLog(SpringContextUtils.class);
    private static ApplicationContext context;
    public void setApplicationContext(ApplicationContext contex) throws BeansException {
        this.context = contex;
    }

    public static ApplicationContext getContext() {
        if(null==context){
            log.error("=getContext=>error context is null.");
        }
        return context;
    }

    public static <T> T getBean(String name) {
        return (T) getContext().getBean(name);
    }

    public static <T> Map<String, T> getBeansOfType(Class clazz) {
        return getContext().getBeansOfType(clazz);
    }

    public static <T> T getBean(String name, Class clazz) {
        return (T) getContext().getBean(name, clazz);
    }

    public static <T> T getBean(Class clazz) {
        return (T) getContext().getBean(clazz);
    }
}
