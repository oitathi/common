package com.b2wdigital.fazemu.domain;

import java.util.Properties;

public class KafkaConfigurationVO {

    private Properties consumerProperties;
    private Properties consumerErrorProperties;
    private Properties producerProperties;

    public Properties getConsumerProperties() {
        return consumerProperties;
    }

    public void setConsumerProperties(Properties consumerProperties) {
        this.consumerProperties = consumerProperties;
    }

    public Properties getConsumerErrorProperties() {
        return consumerErrorProperties;
    }

    public void setConsumerErrorProperties(Properties consumerErrorProperties) {
        this.consumerErrorProperties = consumerErrorProperties;
    }

    public Properties getProducerProperties() {
        return producerProperties;
    }

    public void setProducerProperties(Properties producerProperties) {
        this.producerProperties = producerProperties;
    }

}
