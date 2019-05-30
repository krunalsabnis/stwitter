package com.kru.stwitter.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;
import twitter4j.Status;
import twitter4j.TwitterStream;

import java.util.Arrays;

/**
 * @author kru on 30-5-19
 * @project spring-twitter-streamer
 */

@Slf4j
@Configuration
public class TwitterConfig {

    @Bean
    TwitterStreamFactory twitterStreamFactory() {
        return new TwitterStreamFactory();
    }

    @Bean
    TwitterStream twitterStream(TwitterStreamFactory twitterStreamFactory) {
        return twitterStreamFactory.createInstance();
    }

    @Bean
    MessageChannel outputChannel() {
        return MessageChannels.direct().get();
    }

    @Bean
    TwitterMessageProducer twitterMessageProducer(
            TwitterStream twitterStream, MessageChannel outputChannel, FilterTerms filterTerms) {
        if (filterTerms.getTerms() == null || filterTerms.getTerms().size() == 0)
            throw new BeanCreationException("configuration for filter keywords missing. twitter.config.terms");

        TwitterMessageProducer twitterMessageProducer =
                new TwitterMessageProducer(twitterStream, outputChannel);
        twitterMessageProducer.setTerms(filterTerms.getTerms());
        return twitterMessageProducer;
    }

    @Bean
    IntegrationFlow twitterFlow(MessageChannel outputChannel) {
        return IntegrationFlows.from(outputChannel)
                .transform(Status::getText)
                // TO DO : Kafka producer to push tweets to Kafka Stream
                .handle(m -> log.info(m.getPayload().toString()))
                .get();
    }

}
