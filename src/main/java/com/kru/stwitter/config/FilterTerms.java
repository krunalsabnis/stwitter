package com.kru.stwitter.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author kru on 30-5-19
 * @project spring-twitter-streamer
 */

@Component
@ConfigurationProperties(prefix = "twitter.config")
@Getter
@Setter
public class FilterTerms {
    List<String> terms;
}
