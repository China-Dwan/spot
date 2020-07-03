package springboottest.business.factorystrategy;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springboottest.business.factorystrategy.strategy.Context;
import springboottest.business.factorystrategy.strategy.StrategyTypeEnum;

@Controller
@RequestMapping("/guest/users")
public class TestDemo {

    @RequestMapping("/ss")
    public void test() {
        Context context = new Context();
        System.out.println(context.getPayPrice(1D, StrategyTypeEnum.AVIP.type()));
    }



    public static void main(String[] args) {
        Context context = new Context();
        System.out.println(context.getPayPrice(1D, StrategyTypeEnum.AVIP.type()));
    }
}
