package cn.kimmking.kkconfig.client.repository;

import cn.kimmking.kkconfig.client.config.ConfigMeta;
import cn.kimmking.utils.HttpUtils;
import com.alibaba.fastjson.TypeReference;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * default impl for kk repository.
 *
 * @Author : kimmking(kimmking@apache.org)
 * @create 2024/5/4 19:11
 */
public class KKRepositoryImpl implements KKRepository{

    ConfigMeta meta;
    Map<String, Long> versionMap = new HashMap<>();
    Map<String, Map<String, String>> configMap = new HashMap<>();
    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    List<KKRepositoryChangeListener> listeners = new ArrayList<>();

    public KKRepositoryImpl(ApplicationContext applicationContext, ConfigMeta meta) {
        this.meta = meta;
        executor.scheduleWithFixedDelay(this::heartbeat, 1000, 5000, TimeUnit.MILLISECONDS);
    }

    public void addListener(KKRepositoryChangeListener listener) {
        listeners.add(listener);
    }

    @Override
    public Map<String, String> getConfig() {
        String key = meta.genKey();
        if(configMap.containsKey(key)) {
            return configMap.get(key);
        }
        return findAll();
    }

    private @NotNull Map<String, String> findAll() {
        String listPath = meta.listPath();
        System.out.println("[KKCONFIG] list all configs from kk config server.");
        List<Configs> configs = HttpUtils.httpGet(listPath, new TypeReference<List<Configs>>(){});
        Map<String,String> resultMap = new HashMap<>();
        configs.forEach(c -> resultMap.put(c.getPkey(), c.getPval()));
        return resultMap;
    }

    private void heartbeat() {
        String versionPath = meta.versionPath();
        Long version = HttpUtils.httpGet(versionPath, new TypeReference<Long>() {});
        String key = meta.genKey();;
        Long oldVersion = versionMap.getOrDefault(key, -1L);
        if(version > oldVersion) { // 发生了变化了
            System.out.println("[KKCONFIG] current=" +version+ ", old=" + oldVersion);
            System.out.println("[KKCONFIG] need update new configs.");
            versionMap.put(key, version);
            Map<String, String> newConfigs = findAll();
            configMap.put(key, newConfigs);
            listeners.forEach(l -> l.onChange(new KKRepositoryChangeListener.ChangeEvent(meta, newConfigs)));
        }

    }
}
