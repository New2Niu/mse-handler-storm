package citycloud.kafkastorm;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import redis.clients.jedis.Jedis;

import java.util.Map;

/**
 * Created by wojustme on 2017/2/8.
 */
public class MyBolt extends BaseRichBolt {
  Jedis redis = null;
  @Override
  public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
    redis = new Jedis("10.66.0.224",6379);
  }

  @Override
  public void execute(Tuple tuple) {
    String string = new String((byte[]) tuple.getValue(0));
    System.out.println(string);
    redis.set("storm", string);
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

  }
}
