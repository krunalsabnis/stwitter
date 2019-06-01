package com.kru.stwitter.config;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import twitter4j.FilterQuery;
import twitter4j.Status;
import twitter4j.StatusAdapter;
import twitter4j.TwitterStream;

import java.util.List;

/**
 * @author kru on 30-5-19
 * @project spring-twitter-streamer
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class TwitterMessageProducer extends MessageProducerSupport {
    List<String> terms;
    StatusListener statusListener;
    FilterQuery filterQuery;
    TwitterStream twitterStream;


    public TwitterMessageProducer(TwitterStream twitterStream, MessageChannel outputChannel) {
        this.twitterStream = twitterStream;
        setOutputChannel(outputChannel);
    }

    @Override
    protected void onInit() {
        super.onInit();
        statusListener = new StatusListener();
        String[] termsArray = terms.toArray(new String[terms.size()]);
        filterQuery = new FilterQuery(0, null, termsArray);
    }


    @Override
    public void doStart() {
        twitterStream.addListener(statusListener);
        twitterStream.filter(filterQuery);
    }

    @Override
    public void doStop() {
        twitterStream.cleanUp();
        twitterStream.clearListeners();
    }


    class StatusListener extends StatusAdapter {
        @Override
        public void onStatus(Status status) {
            log.debug("received : {}", status);
            sendMessage(MessageBuilder.withPayload(status).build());
        }
    }
}
