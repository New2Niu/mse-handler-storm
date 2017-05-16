package citycloud.topology;

import citycloud.bolt.DayPassengerFlowBolt;
import citycloud.bolt.NeedMseBolt;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.kafka.*;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;

import java.util.Arrays;

/**
 * Created by Administrator on 2017-1-19.
 */
public class NginxTopologyStarter {
    public static void main(String[] args) throws InvalidTopologyException, AuthorizationException, AlreadyAliveException {
        //broker
        BrokerHosts brokerHosts = new ZkHosts(TopologyProperties.ZK_HOSTS);
        //spout
        SpoutConfig spoutConfig = new SpoutConfig(brokerHosts, TopologyProperties.TOPIC, TopologyProperties.ZK_ROOT, TopologyProperties.ID);
        spoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme());
        spoutConfig.startOffsetTime = kafka.api.OffsetRequest.EarliestTime();
        spoutConfig.zkServers = Arrays.asList(TopologyProperties.ZK_SERVERS);
        spoutConfig.zkPort = TopologyProperties.ZK_PORT;
        //topology
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("kafkaSpout",new KafkaSpout(spoutConfig),3);
        builder.setBolt("needMseBolt",new NeedMseBolt(),3).shuffleGrouping("kafkaSpout");
        builder.setBolt("dayPassengerFlowBolt",new DayPassengerFlowBolt()).shuffleGrouping("needMseBolt");
        //Topology name
        String name = NginxTopologyStarter.class.getSimpleName();
        //Storm config
        Config config = new Config();
        if(args!=null&&args.length>0){//提交到集群 - 参数args:NIMBUS_SEEDS
            config.put(Config.NIMBUS_SEEDS,Arrays.asList(args[0]));
            config.setNumWorkers(2);
            StormSubmitter.submitTopologyWithProgressBar(name,config,builder.createTopology());
        }else {//提交到本地 -无参
            config.setMaxTaskParallelism(3);
            LocalCluster localCluster = new LocalCluster();
            localCluster.submitTopology(name,config,builder.createTopology());
        }
    }
}
