package citycloud.web;

import citycloud.kafka.KafkaProperties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Properties;

/**
 * Created by wojustme on 2017/1/16.
 */

@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class MseReceiverServlet extends HttpServlet {
  private static final Logger LOGGER = LoggerFactory.getLogger(MseReceiverServlet.class);
  private Producer<String,String> producer;
  private Properties props;
  @Override
  public void init() throws ServletException {
    LOGGER.info("begin...");
    // 初始时，初始kafka连接的信息。。
    props = new Properties();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaProperties.BOOTSTRAP_SERVERS);
    props.put(ProducerConfig.ACKS_CONFIG,KafkaProperties.ACKS);
    props.put(ProducerConfig.RETRIES_CONFIG,KafkaProperties.RETRIES);
    props.put(ProducerConfig.LINGER_MS_CONFIG,KafkaProperties.LINGER_MS);
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,KafkaProperties.KEY_SERIALIZER);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaProperties.VALUE_SERIALIZER);
    props.put(ProducerConfig.CLIENT_ID_CONFIG,KafkaProperties.CLIENT_ID);
    producer = new KafkaProducer<String, String>(props);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    LOGGER.debug("citycloud.web.MseReceiverServlet doPost invocation");
    BufferedReader br = new BufferedReader(new InputStreamReader(
        (ServletInputStream) req.getInputStream(), "utf-8"));
    StringBuffer sb = new StringBuffer("");
    String temp;
    while ((temp = br.readLine()) != null) {
      sb.append(temp);
    }
    br.close();
//    //接收post过来的数据
//    BodyData bodyData = JsonHelper.fromJson(sb.toString(), BodyData.class);
//    //mse 数据
//    MseData mseData = bodyData.get("PresenceNotification");
//    NeedMseFields needMseFields = new NeedMseFields(
//            mseData.getApMacAddress(),
//            mseData.getDeviceId(),
//            mseData.getTimestamp()
//            );
//    String needMseStr = JsonHelper.toJson(needMseFields);
//    LOGGER.info("needMseStr:",needMseStr);
//    String recordKey = mseData.getApMacAddress()+mseData.getTimestamp();
//    String recordValue = JsonHelper.toJson(mseData);;
    //接收的数据发送到kafka topic
    LOGGER.info("bodyData:"+sb.toString());
    if (sb.length()>0&&sb.toString().contains("PresenceNotification")){
      producer.send(new ProducerRecord<String, String>(KafkaProperties.TOPIC,sb.toString()));
    }
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");
    PrintWriter writer = resp.getWriter();
    writer.write("this is get method");
    writer.flush();
    writer.close();
  }

  @Override
  public void destroy() {
    LOGGER.info("end...");
  }
}