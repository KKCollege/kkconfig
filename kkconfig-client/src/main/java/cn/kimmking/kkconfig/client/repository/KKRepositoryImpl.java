package cn.kimmking.kkconfig.client.repository;

import cn.kimmking.kkconfig.client.config.ConfigMeta;
import cn.kimmking.utils.HttpUtils;
import com.alibaba.fastjson.TypeReference;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * default impl for kk repository.
 *
 * @Author : kimmking(kimmking@apache.org)
 * @create 2024/5/4 19:11
 */
@AllArgsConstructor
public class KKRepositoryImpl implements KKRepository{

    ConfigMeta meta;

    @Override
    public Map<String, String> getConfig() {
        String listPath = meta.getConfigServer() + "/list?app=" + meta.getApp()
                + "&env=" + meta.getEnv() + "&ns=" + meta.getNs();
        List<Configs> configs = HttpUtils.httpGet(listPath, new TypeReference<List<Configs>>(){});
        Map<String,String> resultMap = new HashMap<>();
        configs.forEach(c -> resultMap.put(c.getPkey(), c.getPval()));
        return resultMap;
    }
}
