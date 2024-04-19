package com.example;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log4j2Test {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void 로깅테스트(){
        logger.debug("debug");
        logger.info("info");
        System.out.println("sout");
    }
}
