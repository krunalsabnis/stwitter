# stwitter
Spring Integration for Twitter Streaming to Kafka

A simple demo app with basic integration implementation


## Build

```
> ./gradlew clean build

```


## Run

Rename twitter4j.properties.template to twitter4j.properties and update it with your twitter app credentials.

Configure keyword for filtering tweets in application.yml's 'twitter.config.terms' and Kafka cluster.


```
> ./gradlew bootRun

```
