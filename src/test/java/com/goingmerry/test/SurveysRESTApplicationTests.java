package com.goingmerry.test;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.goingmerry.SurveysRESTApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SurveysRESTApplication.class)
@WebAppConfiguration
class SurveysRESTApplicationTests {

    @Test
    public void contextLoads() {
    }
}