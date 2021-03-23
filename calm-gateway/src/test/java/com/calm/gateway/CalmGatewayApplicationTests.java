package com.calm.gateway;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class CalmGatewayApplicationTests {

    @Test
    void contextLoads() {
    }

    public static void main(String[] args) {
        List<Long> detailIds = Arrays.asList(100L);
        System.out.println(detailIds);
    }
}
