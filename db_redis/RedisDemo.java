package db_redis;

public class RedisDemo {
    public static void main(String[] args) throws InterruptedException {
        // Basic Redis without expiry
        SimpleRedis redis = new SimpleRedis();
        redis.set("name", "Anay");
        System.out.println(redis.get("name")); // Output: Anay
        redis.del("name");
        System.out.println(redis.get("name")); // Output: NULL

        // Redis with TTL
        RedisWithTTL redisTTL = new RedisWithTTL();
        redisTTL.set("session", "active", 2);
        System.out.println(redisTTL.get("session")); // active
        Thread.sleep(3000); // wait 3 seconds
        System.out.println(redisTTL.get("session")); // NULL (expired)
    }
}