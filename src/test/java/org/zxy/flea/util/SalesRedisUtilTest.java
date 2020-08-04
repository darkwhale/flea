package org.zxy.flea.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
@Slf4j
class SalesRedisUtilTest {
    @Resource
    private SalesRedisUtil salesRedisUtil;

    @Test
    void add() {
        salesRedisUtil.add("140");
    }

    @Test
    void delete() {
        salesRedisUtil.delete("110");
    }

    @Test
    void testDelete() {
    }

    @Test
    void getRandSalesIdList() {
        log.info("sales: {}", salesRedisUtil.getRandSalesIdList(2));
    }
}