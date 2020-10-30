<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">

</beans>


## 注解说明
- @Autowired: 自动装配通过byType和byName
- @Nullable： 标识字段可以为null
- @Resource ： 自动装配通过名字，类型
- @Component： 组件，放在类上，说明这个类被Spring容器管理了