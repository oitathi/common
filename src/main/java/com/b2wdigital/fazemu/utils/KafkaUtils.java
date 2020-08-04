package com.b2wdigital.fazemu.utils;

import org.apache.kafka.clients.consumer.ConsumerRecord;

/**
 * Kafka Utils.
 *
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
public class KafkaUtils {

    public static Integer getAttemptFromHeader(ConsumerRecord<String, String> record) {
        Integer attempt = 0;
        if ((record != null) && (record.headers() != null)) {
            if (record.headers().lastHeader("ATTEMPT") != null && record.headers().lastHeader("ATTEMPT").value() != null) {
                byte[] value = record.headers().lastHeader("ATTEMPT").value();
                attempt = Integer.valueOf(new String(value));
            }
        }
        return attempt;
    }

}
