package citycloud.kafkatest;

/**
 * Created by Administrator on 2017-1-5.
 */
public class KafkaConsumerProducerDemoT {
    public static void main(String[] args) {
        KafkaProducerT producerThread = new KafkaProducerT(KafkaProperties.topic);
        producerThread.start();
        //KafkaConsumerT consumerThread = new KafkaConsumerT(KafkaProperties.topic);
        //consumerThread.start();
    }
}
