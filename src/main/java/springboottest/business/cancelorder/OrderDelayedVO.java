package springboottest.business.cancelorder;

import lombok.Data;

import java.util.Objects;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

@Data
public class OrderDelayedVO implements Delayed {

    private String orderId;

    /**
     * 订单创建时间的时间戳
     */
    private Long startTime;

    public OrderDelayedVO(String orderId, Long startTime) {
        this.orderId = orderId;
        this.startTime = startTime;
    }

    @Override
    public int compareTo(Delayed other) {
        if (other == this) {
            return 0;
        } else if (other instanceof OrderDelayedVO) {
            OrderDelayedVO otherRequest = (OrderDelayedVO) other;
            long otherStartTime = otherRequest.getStartTime();
            return (int) (this.startTime - otherStartTime);
        } else {
            return 0;
        }
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(startTime, TimeUnit.MILLISECONDS) - unit.convert(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDelayedVO that = (OrderDelayedVO) o;
        return startTime.equals(that.startTime) &&
                orderId.equals(that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, startTime);
    }
}
