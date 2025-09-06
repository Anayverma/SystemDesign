package db_redis;

import java.util.HashMap;

class SimpleRedis implements RedisCommands {
    private HashMap<String, String> store; 
    
    public SimpleRedis() {
        store = new HashMap<>();
    }

    @Override
    public void set(String key, String value) {
        store.put(key, value);
        System.out.println("SET " + key + " = " + value);
    }

    @Override
    public String get(String key) {
        return store.getOrDefault(key, "NULL");
    }

    @Override
    public void del(String key) {
        if(store.containsKey(key)) {
            store.remove(key);
            System.out.println("Deleted key: " + key);
        } else {
            System.out.println("Key not found: " + key);
        }
    }
}