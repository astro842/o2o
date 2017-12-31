package com.astro;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Created by astro on 2017/12/20.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml",
                   "classpath:spring/spring-service.xml"})
@Slf4j
public class BaseTest {

    Logger logger = LoggerFactory.getLogger(BaseTest.class);


    @Test
    public void test1(){
        logger.debug("debugggg");
        logger.info("infooooo");
        logger.error("errrrrrrrrrr");
        log.error("xixixixixii");
    }
}
