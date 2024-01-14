package redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class RedisClient {

    private final Logger log = LoggerFactory.getLogger(RedisClient.class);

    public void redisCluster() throws IOException {

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(20);
        config.setMaxIdle(10);
        config.setMinIdle(5);

        Set<HostAndPort> jedisClusterNode = new HashSet<>();
        jedisClusterNode.add(new HostAndPort("127.0.0.1", 6379));

        try (JedisCluster jedisCluster = new JedisCluster(jedisClusterNode, 6000, 5000, 10, "123456", config)) {
            //connectionTimeout：指的是连接一个url的连接等待时间
            //soTimeout：指的是连接上一个url，获取response的返回等待时间
            System.out.println(jedisCluster.set("cluster", "edsion"));
            System.out.println(jedisCluster.get("cluster"));
        } catch (Exception e) {
            log.error("error: " + e.getMessage());
        }
    }

    public void singleRedis() {

        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("123456");
        String response = jedis.ping();

        log.debug(response); // PONG


        //设置 redis 字符串数据
        log.info("redis 更新前为: {}", jedis.get("name"));
        jedis.setex("name", 10, "czh");
        // 获取存储的数据并输出
        System.out.println("redis 更新后为: " + jedis.get("name"));

        //判断某个键是否存在
        log.debug("判断某个键是否存在：" + jedis.exists("name"));

        //系统中所有的键
        Set<String> keys = jedis.keys("*");
        System.out.println(keys);

        //按索引查询
        System.out.println("按索引查询：" + jedis.select(0));

        //查看键name所存储的值的类型
        System.out.println("查看键name所存储的值的类型：" + jedis.type("name"));

        // 随机返回key空间的一个
        System.out.println("随机返回key空间的一个：" + jedis.randomKey());

    }

    public static void main(String[] args) throws IOException {
        RedisClient client = new RedisClient();
        client.singleRedis();
//        client.redisCluster();
    }
}
