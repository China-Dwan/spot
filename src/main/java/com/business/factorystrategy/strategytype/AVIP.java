package com.business.factorystrategy.strategytype;

import org.springframework.stereotype.Component;
import com.business.factorystrategy.testautowire.TestAotuwire;

import javax.annotation.Resource;

@Component
public class AVIP implements DisCount {

    @Resource
    private TestAotuwire testAotuwire;

    @Override
    public Double disCount(Double originPrice) {
        testAotuwire.sys("11");
        return 0.5*originPrice;
    }
}
