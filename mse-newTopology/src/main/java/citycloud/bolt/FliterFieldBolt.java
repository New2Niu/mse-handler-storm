package citycloud.bolt;

import citycloud.bean.MseData;
import citycloud.bean.NeedMseData;
import citycloud.bean.ReceiveData;
import citycloud.util.JsonHelper;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.List;

/**
 * Created by wojustme on 2017/2/8.
 */
public class FliterFieldBolt extends BaseBasicBolt {

  public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {

    String inputStr = new String((byte[]) tuple.getValue(0));
    // inputStr -> {"PresenceNotification":{"subscriptionName":"xurenhe","entity":"WIRELESS_CLIENTS","deviceId":"da:a1:19:4b:10:73","mseUdi":"AIR-MSE-VA-K9:V01:sc-mse_4fd905c2-8903-11e5-9216-005056a9e331","siteName":"test","apMacAddress":"88:90:8d:92:f2:e0","rssi":-91,"associated":false,"visitor":false,"presence":true,"timestamp":"2017-01-26T14:12:26.366+0800"}}
    ReceiveData receiveData = JsonHelper.fromJson(inputStr, ReceiveData.class);
    MseData realData = receiveData.get("PresenceNotification");

    NeedMseData needData = new NeedMseData();
    needData.setDeviceId(realData.getDeviceId());
    needData.setTimestamp(realData.getTimestamp());
    needData.setApMacAddress(realData.getApMacAddress());
    basicOutputCollector.emit(new Values(JsonHelper.toJson(needData)));

  }

  public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
    outputFieldsDeclarer.declare(new Fields("mseDataJsonStr"));
  }
}
