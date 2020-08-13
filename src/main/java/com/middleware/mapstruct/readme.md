1，maven引用，同时注意版本搭配，编译器插件同样需要声明
@Mapper : 标记这个接口作为一个映射接口，并且是编译时 MapStruct 处理器的入口
@Mapping : 解决源对象和目标对象中，属性名字不同的情况
2，api详解
@Mapping
dateFormat：适用于日期转字符串，字符串转日期（LocalDateTime类型要格式为yyyy-MM-dd HH:mm:ss）
numberFormat：适用于用于数字转字符串，字符串转数字