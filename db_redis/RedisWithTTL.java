package db_redis;

import java.util.HashMap;

class RedisWithTTL extends SimpleRedis {
    private HashMap<String, Long> expiry; // Key → Expiration time
    
    public RedisWithTTL() {
        super();
        expiry = new HashMap<>();
    }

    public void set(String key, String value, int ttlSeconds) {
        super.set(key, value);
        expiry.put(key, System.currentTimeMillis() + ttlSeconds * 1000);
    }

    @Override
    public String get(String key) {
        if(expiry.containsKey(key)) {
            long expireTime = expiry.get(key);
            if(System.currentTimeMillis() > expireTime) {
                super.del(key);
                expiry.remove(key);
                return "NULL (expired)";
            }
        }
        return super.get(key);
    }
}