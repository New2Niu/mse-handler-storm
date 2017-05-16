package citycloud.kafkatest;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

/**
 * Created by Administrator on 2017-1-5.
 */
public class KafkaConsumerT extends Thread {
    private final Consumer<Integer,String> consumer;
    private final String topic;
    public KafkaConsumerT(String topic)
    {
        consumer = new KafkaConsumer<>(createConsumerProps());
        consumer.subscribe(Arrays.asList(topic));
        this.topic = topic;
    }
    private static Properties createConsumerProps()
    {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("zookeeper.connect", KafkaProperties.zkConnect);
        props.put("group.id", KafkaProperties.groupId);
        props.put("zookeeper.session.timeout.ms", "40000");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    }
    @Override
    public void run() {
        ConsumerRecords<Integer,String> records = consumer.poll(5);
        records.forEach((record)->{
            System.out.println("receive:"+record.value());
        });
    }
}
