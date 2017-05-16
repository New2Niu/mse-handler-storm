package citycloud.kafka;

/**
 * Created by Administrator on 2017-1-18.
 */
public interface KafkaProperties {
    /**
     * kafka producer 配置
     */
    //kafka broker 多个逗号分隔
   //String BOOTSTRAP_SERVERS = "localhost:9092";
   String BOOTSTRAP_SERVERS = "citycloud-224:9092,citycloud-225:9092,citycloud-226:9092";
   //kafka 主题
   String TOPIC = "mseTestTopic";
   //每次请求包含该的用户特定字符串，用于追踪调用
   String CLIENT_ID = "mseTestClient";
   //acks 确认信号
   String ACKS = "all";
   //retries 请求失败，自动重试 0--禁止
   Integer RETRIES = 0;
   //batch.size default 16384 16K
   Integer BATCH_SIZE = 16384;
   //buffer.memory default 33554432 32M
   Long BUFFER_MEMORY = 33554432L;
   //linger.ms 延迟发送时间  batch.size优先级高
   Long LINGER_MS = 5000L;
   //key.serializer
   String KEY_SERIALIZER = "org.apache.kafka.common.serialization.StringSerializer";
   //value.serializer
   String VALUE_SERIALIZER = "org.apache.kafka.common.serialization.StringSerializer";
}
