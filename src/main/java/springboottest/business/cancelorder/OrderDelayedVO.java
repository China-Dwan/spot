package springboottest.business.cancelorder;

import lombok.Data;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

@Data
public class OrderDelayedVO implements Delayed {

    private String orderId;
    private long startTime;

    public OrderDelayedVO(String orderId, long startTime) {
        this.orderId = orderId;
        this.startTime = startTime;
    }

    @Override
    public int compareTo(Delayed other) {
        if (other == this) {
            return 0;
        }
        if (other instanceof OrderDelayedVO) {
            OrderDelayedVO otherRequest = (OrderDelayedVO) other;
            long otherStartTime = otherRequest.getStartTime();
            return (int) (this.startTime - otherStartTime);
        }
        return 0;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(startTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (NumberUtils.createInteger(orderId) ^ (NumberUtils.createInteger(orderId) >>> 32));
        result = prime * result + (int) (startTime ^ (startTime >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OrderDelayedVO other = (OrderDelayedVO) obj;
        if (orderId != other.orderId)
            return false;
        if (startTime != other.startTime)
            return false;
        return true;
    }
}
