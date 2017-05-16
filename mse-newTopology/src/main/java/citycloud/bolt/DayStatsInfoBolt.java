package citycloud.bolt;

import citycloud.bean.NeedMseData;
import citycloud.util.JedisPoolFactory;
import citycloud.util.JsonHelper;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;
import redis.clients.jedis.Jedis;

import java.util.Map;

/**
 * Created by wojustme on 2017/2/8.
 */
public class DayStatsInfoBolt implements IRichBolt {

  private OutputCollector outputCollector;
  private Jedis jedis;


  public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

  }

  public Map<String, Object> getComponentConfiguration() {
    return null;
  }

  public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
    this.outputCollector = outputCollector;
    this.jedis = JedisPoolFactory.getJedisPool().getResource();
  }

  public void execute(Tuple tuple) {
    String inputStr = new String((byte[]) tuple.getValue(0));
    NeedMseData needMseData = JsonHelper.fromJson(inputStr, NeedMseData.class);
    this.jedis.sadd("dayInfo", needMseData.toString());
  }

  public void cleanup() {

  }
}
