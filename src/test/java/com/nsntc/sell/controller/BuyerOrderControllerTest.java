package com.nsntc.sell.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Class Name: BuyerOrderControllerTest
 * Package: com.nsntc.interview.controller
 * Description: API 测试
 *              打包时, 自动测试: mvn clean package
 *              跳过测试: mvn clean package -Dmaven.test.skip=true
 * @author wkm
 * Create DateTime: 2017/11/30 下午11:50
 * Version: 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BuyerOrderControllerTest {

   @Autowired
   private MockMvc mockMvc;

    @Test
    public void xxxTest() throws Exception {
        /** 判断返回状态码: 测试地址必须带根路径 */
        mockMvc.perform(MockMvcRequestBuilders.get("/buyer/order/list"))
                /** HttpStatus code */
                .andExpect(MockMvcResultMatchers.status().isOk())
                /** Http body */
                .andExpect(MockMvcResultMatchers.content().string("[{\"id\":2,\"cupSize\":\"B-Size\",\"age\":18,\"address\":null},{\"id\":3,\"cupSize\":\"DDD\",\"age\":20,\"address\":\"台湾\"},{\"id\":4,\"cupSize\":\"CCC\",\"age\":18,\"address\":\"香港\"}]"));
    }

}