package citycloud.bolt;

import citycloud.util.JedisPoolFactory;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;
import redis.clients.jedis.Jedis;

import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017-1-19.
 */
public class DayPassengerFlowBolt extends BaseBasicBolt {
    private Jedis jedis;

    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        this.jedis = JedisPoolFactory.getJedisPool().getResource();
    }

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        jedis.sadd("dayPassengerFlow",tuple.getString(1));
//        Set set = jedis.smembers("dayPassengerFlow");
//        set.forEach(e->{
//            System.out.println(e);
//        });
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }

    @Override
    public void cleanup() {
        if (this.jedis!=null){
            this.jedis.close();
        }
    }
}
