package citycloud.spout;

import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.ZkHosts;

/**
 * Created by wojustme on 2017/2/8.
 */
public class MseSpout extends KafkaSpout {
  private static ZkHosts zkHosts;
  static {
    ZkHosts zkHosts = new ZkHosts("10.66.0.224:2181,10.66.0.225:2181,10.66.0.226:2181");
  }
  public MseSpout() {
    super(new SpoutConfig(zkHosts, "test", "/storm", "mseSpout"));
  }
}
