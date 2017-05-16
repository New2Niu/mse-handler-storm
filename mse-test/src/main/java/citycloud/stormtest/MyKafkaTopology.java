package citycloud.stormtest;


import citycloud.kafkatest.KafkaProperties;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.kafka.*;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2017-1-6.
 */
public class MyKafkaTopology {
    public static class KafkaWordSplitter extends BaseRichBolt {

        private static final Logger LOG = LoggerFactory.getLogger(MyKafkaTopology.class);
        private static final long serialVersionUID = 886149197481637894L;
        private OutputCollector collector;

        @Override
        public void prepare(Map stormConf, TopologyContext context,
                            OutputCollector collector) {
            this.collector = collector;
        }

        @Override
        public void execute(Tuple input) {
            String line = input.getString(0);
            LOG.info("RECV[kafka -> splitter] " + line);
            System.out.println("RECV[kafka -> splitter] " + line);
            String[] words = line.split("\\s+");
            for(String word : words) {
                LOG.info("EMIT[splitter -> counter] " + word);
                collector.emit(input, new Values(word, 1));
            }
            collector.ack(input);
        }

        @Override
        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            declarer.declare(new Fields("word", "count"));
        }

    }

    public static class WordCounter extends BaseRichBolt {

        private static final Logger LOG = LoggerFactory.getLogger(WordCounter.class);
        private static final long serialVersionUID = 886149197481637894L;
        private OutputCollector collector;
        private Map<String, AtomicInteger> counterMap;

        @Override
        public void prepare(Map stormConf, TopologyContext context,
                            OutputCollector collector) {
            this.collector = collector;
            this.counterMap = new HashMap<String, AtomicInteger>();
        }

        @Override
        public void execute(Tuple input) {
            String word = input.getString(0);
            int count = input.getInteger(1);
            LOG.info("RECV[splitter -> counter] " + word + " : " + count);
            AtomicInteger ai = this.counterMap.get(word);
            if(ai == null) {
                ai = new AtomicInteger();
                //this.counterMap.put(word, ai);
            }
            ai.addAndGet(count);
            collector.ack(input);
            //LOG.info("CHECK statistics map: " + this.counterMap);
        }

        @Override
        public void cleanup() {
            LOG.info("The final result:");
            System.out.println("The final result:");
            Iterator<Entry<String, AtomicInteger>> iter = this.counterMap.entrySet().iterator();
            while(iter.hasNext()) {
                Entry<String, AtomicInteger> entry = iter.next();
                LOG.info(entry.getKey() + "\t:\t" + entry.getValue().get());
                System.out.println(entry.getKey() + "\t:\t" + entry.getValue().get());
            }

        }

        @Override
        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            declarer.declare(new Fields("word", "count"));
        }
    }

    public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException, InterruptedException,AuthorizationException {
        String zks = "citycloud-224:2181,citycloud-224:2181,citycloud-224:2181";
        String topic = KafkaProperties.topic;
        String zkRoot = "/stormK"; // default zookeeper root configuration for storm
        String id = "word";
        BrokerHosts brokerHosts = new ZkHosts(zks);

        SpoutConfig spoutConf = new SpoutConfig(brokerHosts, topic, zkRoot, id);
        spoutConf.scheme = new SchemeAsMultiScheme(new StringScheme());
        //spoutConf.forceFromStart = false;
        spoutConf.startOffsetTime=kafka.api.OffsetRequest.EarliestTime();
        spoutConf.zkServers = Arrays.asList(new String[] {"citycloud-224","citycloud-225","citycloud-226"});
        spoutConf.zkPort = 2181;

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("kafka-reader", new KafkaSpout(spoutConf), 5); // 这里并行度设置为5
        builder.setBolt("word-splitter", new KafkaWordSplitter(), 2).shuffleGrouping("kafka-reader");
        builder.setBolt("word-counter", new WordCounter()).fieldsGrouping("word-splitter", new Fields("word"));

        Config conf = new Config();
        String name = MyKafkaTopology.class.getSimpleName();
        if (args != null && args.length > 0) {
            // Nimbus host name passed from command line
            //conf.put(Config.NIMBUS_HOST, args[0]);
            conf.put(Config.NIMBUS_SEEDS,Arrays.asList(args[0]));
            conf.setNumWorkers(3);
            StormSubmitter.submitTopologyWithProgressBar(name+"1", conf, builder.createTopology());
            //StormSubmitter.submitTopology(name,conf,builder.createTopology());
        } else {
            conf.setMaxTaskParallelism(3);
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology(name, conf, builder.createTopology());
            Thread.sleep(60000);
            //cluster.shutdown();
        }
    }
}
