package citycloud.bolt;

import citycloud.dataFormat.MseData;
import citycloud.util.JsonHelper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by Administrator on 2017-1-19.
 */
public class NeedMseBolt extends BaseBasicBolt {
    private static final Logger LOGGER = LoggerFactory.getLogger(NeedMseBolt.class);
    @Override
    public void execute(Tuple tuple, BasicOutputCollector collector) {
        LOGGER.info("NeedMseBoltTupleData"+tuple.getString(0));
        Map bodyData = JsonHelper.fromJson(tuple.getString(0), new TypeReference<Map<String,MseData>>(){});
        MseData mseData = (MseData) bodyData.get("PresenceNotification");
        //MseData mseData = bodyData.get("PresenceNotification");
        //LOGGER.info("mseData:"+mseData);
        if (mseData!=null){
            collector.emit(new Values(
                    mseData.getApMacAddress(),
                    mseData.getDeviceId(),
                    mseData.getTimestamp()+"")
            );
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("apMacAddress","deviceId","timestamp"));
    }
}
