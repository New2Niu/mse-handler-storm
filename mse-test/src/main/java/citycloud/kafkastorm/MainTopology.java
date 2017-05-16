package citycloud.kafkastorm;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.kafka.KafkaConfig;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.topology.TopologyBuilder;

/**
 * Created by wojustme on 2017/2/8.
 */
public class MainTopology {
  public static void main(String[] args) throws Exception {
    TopologyBuilder topologyBuilder = new TopologyBuilder();
    topologyBuilder.setSpout("kafkaSpout",
        new KafkaSpout(new SpoutConfig(
            new ZkHosts("10.66.0.224:2181,10.66.0.225:2181,10.66.0.226:2181"),
            "test",
            "/storm",
            "kafkaSpout")),1);
    topologyBuilder.setBolt("mybolt1",new MyBolt(),1).shuffleGrouping("kafkaSpout");

    Config config = new Config();
    config.setNumWorkers(1);

    //3、提交任务  -----两种模式 本地模式和集群模式
    if (args.length>0) {
      StormSubmitter.submitTopology(args[0], config, topologyBuilder.createTopology());
    }else {
      LocalCluster localCluster = new LocalCluster();
      localCluster.submitTopology("storm2kafka", config, topologyBuilder.createTopology());
    }
  }
}
