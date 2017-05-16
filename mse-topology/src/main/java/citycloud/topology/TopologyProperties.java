package citycloud.topology;

/**
 * Created by Administrator on 2017-1-19.
 */
public interface TopologyProperties {
    //topic
    String TOPIC = "mseTestTopic";
    //zookeeper
    String ZK_HOSTS = "10.66.0.224:2181,10.66.0.224:2181,10.66.0.224:2181";
    //storm 在zookeeper的根目录
    String ZK_ROOT = "/storm";
    String ID = "mseTestId";

    String[] ZK_SERVERS = {"10.66.0.224","10.66.0.225","10.66.0.226"};
    //zookeeper port
    Integer ZK_PORT = 2181;
    //redis
    String REDIS_HOST = "10.66.0.224";
    Integer REDIS_PORT = 6379;
}
