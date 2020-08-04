package org.zxy.flea.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

public class SalesRedisUtil {

    private final String SALES_REDIS_KEY = "sales_key";

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 添加salesId到redis集合中
     * @param salesId 商品id
     */
    public void add(String salesId) {
        redisTemplate.opsForSet().add(SALES_REDIS_KEY, salesId);
    }

    /**
     * 删除元素
     * @param salesId 商品id
     */
    public void delete(String salesId) {
        redisTemplate.opsForSet().remove(SALES_REDIS_KEY, salesId);
    }

    /**
     * 批量删除元素列表
     * @param salesIdList 商品id列表
     */
    public void delete(List<String> salesIdList) {
        for (String salesId : salesIdList) {
            redisTemplate.opsForSet().remove(SALES_REDIS_KEY, salesId);
        }
    }

    public List<String> getRandSalesIdList(int listSize) {
        Set<String> salesIdSet = redisTemplate.opsForSet().members(SALES_REDIS_KEY);

        List<String> salesIdList = new ArrayList<>();
        if (CollectionUtils.isEmpty(salesIdSet)) {
            return salesIdList;
        }

        int maxSize = Math.min(listSize, salesIdSet.size());
        List<Integer> randArray = new ArrayList<>();
        for (int i = 0; i < salesIdSet.size(); i++) {
            randArray.add(i);
        }

        Random random = new Random(System.currentTimeMillis());
        Collections.shuffle(randArray, random);

        // 取前maxSize个数据；
        List<Integer> newRandArray = randArray.subList(0, maxSize);
        Collections.sort(newRandArray);

        List<String> result = new ArrayList<>();
        int index = 0;
        int begin_index = 0;
        for (String str : salesIdSet) {
            if (index == newRandArray.get(begin_index)) {
                result.add(str);
                begin_index++;
            }
            // 相等表示已经取到足够的数字；
            if(begin_index == newRandArray.size()) {
                break;
            }

            index++;
        }

        return result;

    }

}
