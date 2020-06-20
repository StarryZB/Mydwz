package mydwz.dao.Redis;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisDao {

    private JedisPool jedisPool;

    public RedisDao(String ip,int port) {
        this.jedisPool = new JedisPool(ip, port);
    }

    public String getDwz(String longUrl) {
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                return jedis.get(longUrl);
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String setDwz(String longUrl, String shortUrl) {
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                jedis.set(longUrl, shortUrl);
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getCwz(String shortUrl) {
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                jedis.get(shortUrl);
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}