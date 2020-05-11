一小时未支付自动取消订单流程
使用到的技术，redis + java中DelayQueue延时队列
redis数据结构，Hash数据结构 pub_key : { ‘key1’：‘value1’， ....}

1：用户下单时
redis插入一条记录，订单号key，订单信息订单号+创建时间
DelayQueue插入一条记录，记录包括两个参数，订单号 + 下订单时的时间戳Long

2：用户结算完成时
redis删除对应的记录
DelayQueue删除对应记录

3：一小时未支付自动取消订单逻辑
项目启动时，启动一个线程，自旋DelayQueue.take()获取并执行取消订单业务
线程读取redis中保存的历史数据，并添加进DelayQueue中
当流程中发生异常时，短信通知相关负责人员
