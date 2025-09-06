package db_redis;

interface RedisCommands {
    void set(String key, String value);
    String get(String key);
    void del(String key);
}