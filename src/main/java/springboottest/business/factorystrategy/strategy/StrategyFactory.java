package springboottest.business.factorystrategy.strategy;

import org.springframework.stereotype.Component;
import springboottest.business.factorystrategy.strategytype.AVIP;
import springboottest.business.factorystrategy.strategytype.BVIP;
import springboottest.business.factorystrategy.strategytype.DisCount;
import springboottest.business.factorystrategy.strategytype.NOVIP;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;

@Component
public class StrategyFactory {
    @Resource
    private AVIP avip;

    @Resource
    private BVIP bvip;

    @Resource
    private NOVIP novip;

    private static StrategyFactory factory = new StrategyFactory();

    @PostConstruct
    private void StrategyFactory() {
        strategyMap.put(StrategyTypeEnum.AVIP.type(), avip);
        strategyMap.put(StrategyTypeEnum.BVIP.type(), bvip);
        strategyMap.put(StrategyTypeEnum.NOVIP.type(), novip);
    }

    private static HashMap<String, DisCount> strategyMap = new HashMap<>();

    public DisCount creator(String value) {
        return strategyMap.get(value);
    }

    public static StrategyFactory getInstance() {
        return factory;
    }
}
