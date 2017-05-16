package citycloud.util;

import redis.clients.jedis.JedisPool;

/**
 * Created by Administrator on 2017-1-23.
 */
public final class JedisPoolFactory {
    private static final String HOST = "10.66.0.224";
    private static final Integer PORT = 6379;

    private JedisPoolFactory(){}
    private static class JedisPoolHolder{
        private static final JedisPool JEDIS_POOL = new JedisPool(HOST,PORT);
    }

    public static JedisPool getJedisPool(){
        return JedisPoolHolder.JEDIS_POOL;
    }
}
