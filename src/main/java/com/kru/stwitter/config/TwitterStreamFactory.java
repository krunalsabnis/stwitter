package com.kru.stwitter.config;

/**
 * @author kru on 30-5-19
 * @project spring-twitter-streamer
 */


import org.springframework.beans.factory.config.AbstractFactoryBean;
import twitter4j.TwitterStream;

public class TwitterStreamFactory extends AbstractFactoryBean<TwitterStream> {
    @Override
    public Class<?> getObjectType() {
        return TwitterStream.class;
    }

    @Override
    protected TwitterStream createInstance() {
        return new twitter4j.TwitterStreamFactory().getInstance();
    }
}
