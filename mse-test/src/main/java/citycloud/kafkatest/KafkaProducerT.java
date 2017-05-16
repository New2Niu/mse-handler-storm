package citycloud.kafkatest;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * Created by Administrator on 2017-1-5.
 */
public class KafkaProducerT extends Thread {
    private final Producer<Integer,String> producer;
    private final String topic;
    private final Properties props = new Properties();
    public KafkaProducerT(String topic)
    {
        props.put("bootstrap.servers", "citycloud-224:9092,citycloud-225:9092,citycloud-226:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<>(props);
        this.topic = topic;
    }
    @Override
    public void run() {
        int messageNo = 999;
        while (true)
        {
            String messageStr = new String("Message_" + messageNo);
            System.out.println("Send:" + messageStr);
            producer.send(new ProducerRecord<Integer, String>(topic,messageStr.length(),messageStr));
            messageNo++;
            if (messageNo==1010)
                messageNo=999;
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
