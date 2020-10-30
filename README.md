# Spring


# 1、Spring

https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html#beans

## 1.1 简介

- SSM  = SpringMVC + Spring + Mybatis

```xml
<!-- https://mvnrepository.com/artifact/org.springframework/spring-webmvc -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
    <version>5.2.9.RELEASE</version>
</dependency>

<!-- https://mvnrepository.com/artifact/org.springframework/spring-jdbc -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-jdbc</artifactId>
    <version>5.2.9.RELEASE</version>
</dependency>
```

## 1.2 优点

- Spring是一个免费的容器
- 轻量级，非入侵式框架
- 控制反转（IOC)
- 面向切面编程(AOP)
- 支持事务处理

总结：Spring就是一个轻量级的控制反转，面向切面编程的框架。

## 1.3 组成

![image-20201025172757920](C:\Users\chend\AppData\Roaming\Typora\typora-user-images\image-20201025172757920.png)

## 1.4 What Spring can do

![image-20201026084018571](C:\Users\chend\AppData\Roaming\Typora\typora-user-images\image-20201026084018571.png)

## 1.5 拓展

![image-20201026084122171](C:\Users\chend\AppData\Roaming\Typora\typora-user-images\image-20201026084122171.png)

## 1.6 未来

- SpringBoot
  - 一个快速开发的模块
  - 可以快速开发单个微服务
  - 约定大于配置

- SpringCloud
  - 是基于SpringBoot实现的

Spring与SpringMVC是学习SpringBoot的前提。

# 2、IOC理论推导

## 2.1 IOC初探

1. UserDao接口
2. UserDaoImpl实现类
3. UserService业务接口
4. UserServiceImpl业务实现类

用Set接口实现

```java
private UserDao userDao;

//利用set进行动态实现值的注入！
public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
}
```

- 使用了Set注入之后，程序由主动创建对象，变成了变动的接受对象！
- 系统耦合性降低，更专注也业务的实现。
- IOC控制反转，控制程序的主动权由程序员转移到了用户手上。

## 2.2 IOC本质

控制反转是一种通过描述（XML或者注解）并通过第三方去生产或获取特定对象的方式。在Spring中实现IOC的是IOC容器，IOC container，其实现方式是依赖注入（Dependency Injection，DI）。

对象由Spring进行创建、装配和管理。

# 3、HelloSpring

## 3.1 pom.xml

```xml
<dependencies>
    <!-- https://mvnrepository.com/artifact/org.springframework/spring-webmvc -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
        <version>5.2.9.RELEASE</version>
    </dependency>

</dependencies>
```

一个maven依赖，导入多个Spring依赖文件

![image-20201026094339350](C:\Users\chend\AppData\Roaming\Typora\typora-user-images\image-20201026094339350.png)

## 3.2 实体类（需要进行IOC的对象）

```java
public class Hello {
    private String str;

    public String getStr() {
        return str;
    }
	//需要注意，必须有set方法，Spring才能进行IOC
    public void setStr(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return "Hello{" +
                "str='" + str + '\'' +
                '}';
    }
}
```

## 3.3 配置文件beans.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--
    类型 变量名 = new 类型();
    Hello hello = new Hello();

    id = 变量名
    class = new 的类型
    property 相当于给对象的属性赋值
    -->

    <bean id="hello" class="com.chen.pojo.Hello">
        <property name="str" value="Spring"/>
    </bean>

</beans>
```

## 3.4 Mytest.java

```java
public class Mytest {
    public static void main(String[] args) {
        //获取spring的上下文对象
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        //对象都由Spring管理，直接可以从Spring中取出对象
        Hello hello = (Hello) context.getBean("hello");
        System.out.println(hello.toString());
    }
}
```

# 4、IOC创建对象的方式

## 4.1 使用无参构造方法创建对象。

- JavaBean

  ```java
  public class User {
      private String name;
  
      public User() {
          System.out.println("User已经创建");
      }
  }
  ```

- beans.xml

  ```xml
  <bean id="User" class="com.chen.pojo.User">
      <property name="name" value="chen"/>
  </bean>
  ```

## 4.2 假设要用有参构造方法创建对象。

User.java

```java
public class User {
    private String name;

    public User(String name){
        this.name = name;
    }
}
```

- 下标赋值

  - beans.xml

  ```xml
  <!--下标赋值-->
  <bean id="User" class="com.chen.pojo.User">
      <constructor-arg index="0" value="chen"/>
  </bean>
  ```

- 类型赋值

  - 不建议使用，可能出现多个相同类型参数的情况

  ```xml
  <bean id="User" class="com.chen.pojo.User">
      <constructor-arg type="java.lang.String" value="ding"/>
  </bean>
  ```

- 参数名赋值

  ```xml
  <!--参数名赋值-->
  <bean id="User" class="com.chen.pojo.User">
      <constructor-arg name="name" value="wei"/>
  </bean>
  ```

## 4.3 总结

在配置文件加载的时候，容器中的对象就已经初始化了。

# 5、Spring配置

## 5.1 别名

```xml
<bean id="User" class="com.chen.pojo.User">
    <constructor-arg name="name" value="wei"/>
</bean>

<alias name="User" alias="user"/>
```

## 5.2 Bean的配置

```xml
<!--bean配置-->
<!--
    id : bean的唯一标识符，也就是相当于对象名
    class : bean对象所对应的全限定名： 包名+类名
    name : 别名，而且可以同时取多个别名
    -->
<bean id="User" class="com.chen.pojo.User" name="user,u2 u3">
    <property name="name" value="chen"/>
</bean>
```



## 5.3 import

一般用于团队开发，可以将多个配置文件导入合并为一个。

```xml
<import resource="beans.xml"/>
```

# 6、依赖注入 DI

## 6.1 构造器注入

有参形式创建，给JavaBean传参，实现注入。

## 6.2 Set方式注入【重点】

- 依赖：bean对象的创建依赖于容器
- 注入：bean对象的属性，由容器注入

【环境搭建】

1. 复杂类型

   ```java
   public class Address {
       private String address;
   
       public String getAddress() {
           return address;
       }
   
       public void setAddress(String address) {
           this.address = address;
       }
   }
   ```

2. 真实对象

   ```java
   public class Student {
       private String name;
       private Address address;
       private String[] book;
       private List<String> hobbies;
       private Map<String,String> card;
       private Set<String> game;
       private Properties info;
       private String wife;
   }
   ```

3. applicationContext.xml

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://www.springframework.org/schema/beans
           https://www.springframework.org/schema/beans/spring-beans.xsd">
   
       <bean id="Address" class="com.chen.pojo.Address">
           <property name="address" value="Sichuan,Chengdu"/>
       </bean>
       <bean id="Student" class="com.chen.pojo.Student">
           <!--1、普通值注入 value-->
           <property name="name" value="chen"/>
           <!--2、bean注入 ref-->
           <property name="address" ref="Address"/>
           <!--3、数组注入-->
           <property name="book">
               <array>
                   <value>红楼梦</value>
                   <value>西游记</value>
               </array>
           </property>
           <!--4、List注入-->
           <property name="hobbies">
               <list>
                   <value>听歌</value>
                   <value>唱歌</value>
               </list>
           </property>
           <!--4、Map注入-->
           <property name="card">
               <map>
                   <entry key="身份证" value="352203"/>
                   <entry key="学生证" value="20182209"/>
               </map>
           </property>
           <!--5、Set注入-->
           <property name="game">
               <set>
                   <value>LOL</value>
               </set>
           </property>
   
           <!--6、Null注入-->
           <property name="wife">
               <null/>
           </property>
   
           <!--7、Properties注入-->
           <property name="info">
               <props>
                   <prop key="driver">mysql</prop>
                   <prop key="url">127.0.0.1:3306</prop>
                   <prop key="username">root</prop>
                   <prop key="password">0000</prop>
               </props>
           </property>
       </bean>
   </beans>
   ```

## 6.3 拓展方式注入

1. P命名空间注入

   - 无参构造

   ```xml
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:p="http://www.springframework.org/schema/p"
          xsi:schemaLocation="http://www.springframework.org/schema/beans
           https://www.springframework.org/schema/beans/spring-beans.xsd">
       
    	<!--
   	xmlns:p="http://www.springframework.org/schema/p"
   	-->   
       <!--p空间命名注入，可以直接注入属性值-->
       <bean id="User" class="com.chen.pojo.User" p:name="chen" p:age="15"/>
   </beans>
   ```

2. C命名空间注入

   - 有参构造

   ```xml
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:c="http://www.springframework.org/schema/c"
          xsi:schemaLocation="http://www.springframework.org/schema/beans
           https://www.springframework.org/schema/beans/spring-beans.xsd">
   	<!--
   	xmlns:c="http://www.springframework.org/schema/c"
   	-->
       <!--c空间命名注入，可以直接注入属性值-->
       <bean id="User2" class="com.chen.pojo.User" c:name="chen" c:age="16"/>
   </beans>
   ```

# 7、bean的作用域

![image-20201027094616928](C:\Users\chend\AppData\Roaming\Typora\typora-user-images\image-20201027094616928.png)

## 7.1 The Singleton Scope 单例模式

![image-20201027095021025](C:\Users\chend\AppData\Roaming\Typora\typora-user-images\image-20201027095021025.png)

```xml
<bean id="accountService" class="com.something.DefaultAccountService"/>

<!-- the following is equivalent, though redundant (singleton scope is the default) -->
<bean id="accountService" class="com.something.DefaultAccountService" scope="singleton"/>
```

## 7.2 The Prototype Scope 原型模式

![image-20201027095111171](C:\Users\chend\AppData\Roaming\Typora\typora-user-images\image-20201027095111171.png)

```xml
<!--每次从容器中get的时候，都会产生一个新对象-->
<bean id="accountService" class="com.something.DefaultAccountService" scope="prototype"/>
```

## 7.3 其余的request、session、application只能在web开发中使用。

# 8、bean的自动装配

- 自动装配是Spring满足bean依赖的一种方式
- Spring会在上下文中自动寻找，并给bean装配属性
- 有三种装配的方式
  - 在xml中显式配置
  - 在java中显式配置
  - 隐式的自动装配方法

## 8.1 测试



## 8.2 byName

```xml
<!--如果id是大写，会报空指针异常-->
<bean id="cat" class="com.chen.pojo.Cat"/>
<bean id="dog" class="com.chen.pojo.Dog"/>
<!--
    byName : 会自动在容器上下文进行查找，和自己对象set方法后面的值对应的beanID
    注意：区分大小写
    -->
<bean id="People" class="com.chen.pojo.People" autowire="byName">
    <property name="name" value="chen"/>
</bean>
```

## 8.3 byType

```xml
<!--如果有重复类型，会报异常-->
<bean id="Cat" class="com.chen.pojo.Cat"/>
<bean id="Dog" class="com.chen.pojo.Dog"/>

<!--
        byName : 会自动在容器上下文进行查找，和自己对象set方法后面的值对应的class类型
        注意：区分大小写
        -->
<bean id="People" class="com.chen.pojo.People" autowire="byType">
    <property name="name" value="chen"/>
</bean>
```

## 8.4 小结

- byName的时候，需要保证所有bean的id唯一，且这个bean需要和自动注入的属性的set方法的值一致。
- byType的时候，需要保证所有bean的id唯一，且这个bean需要和自动注入的class类型一致。

## 8.5 注解实现自动装配

使用注解的步骤：

1. 导入约束

   ```xml
   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   ```

2. 配置注解的支持

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:context="http://www.springframework.org/schema/context"
          xsi:schemaLocation="http://www.springframework.org/schema/beans
           https://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">
   
       <context:annotation-config/>
   </beans>
   ```

   ### 8.5.1 @Autowired

   1. 直接在属性上使用即可，也可以在set方法上使用

      ```java
      public class People {
          @Autowired
          private Cat cat;
          
          @Autowired
          private Dog dog;
          private String name;
      }
      ```

   2. 原理上是使用byType的方式实现

# 9、使用注解开发

在Spring4之后，要使用注解开发，必须保证AOP的包已导入。

需要导入context约束，增加注解支持。

1. bean

   ```java
   //等价于<bean id="User" class="com.chen.pojo.User"/>
   @Component
   public class User {
       public String name;
   }
   ```

2. 属性如何注入

   ```java
   public class User {
       //相当于
       // <property name="name" value="chen"/>
       @Value("chen")
       public String name;
   }
   ```

3. 衍生注解

   @Component有几个衍生注解，在web开发中，会按照MVC三层架构分层

   - dao 【@Repository】 
   - service 【@Service】
   - controller 【@Controller】

   上述四个注解功能相同，都是为了将类放入Spring容器中，注册bean

4. 自动装配注解

   - @Autowired: 自动装配通过byType和byName
   - @Nullable： 标识字段可以为null
   - @Resource ： 自动装配通过名字，类型

5. 作用域

   @Scope("prototype") 

   @Scope("singl")

6. 小结

   xml与注解

   - xml更加万能，适用于任何场合，维护简单方便
   - 注解不是自己的类使用不了，维护相对复杂

   最佳实践：

   - xml用来管理bean

   - 注解负责完成属性的注入

   - 使用注解，需要开启注解支持，包含约束和扫描包的设置

     ```xml
     <?xml version="1.0" encoding="UTF-8"?>
     <beans xmlns="http://www.springframework.org/schema/beans"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xmlns:context="http://www.springframework.org/schema/context"
            xsi:schemaLocation="http://www.springframework.org/schema/beans
             https://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">
         <!--指定要扫描的包，包下的注解会生效-->
         <context:component-scan base-package="com.chen.pojo"/>
         <context:annotation-config/>
     </beans>
     ```

# 10、使用java的方式配置Spring

完全不适用Spring的xml配置，全权交付给java实现。

## 10.1 User.java

```java
package com.chen.pojo;

import org.springframework.beans.factory.annotation.Value;

public class User {
    private String name;

    public String getName() {
        return name;
    }
    @Value("chen")
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
```

10.2 MyConfig.java

```java
package com.chen.config;

import com.chen.pojo.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {

    @Bean
    public User getUser(){
        return new User();
    }
}
```

10.3 Mytest.java

```java
public class MyTest {

    @Test
    public void test(){
        ApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
        User getUser = context.getBean("getUser", User.class);
        System.out.println(getUser.toString());
    }
}
```

# 11、代理模式

## 11.1 有什么用？

这是Spring AOP的底层模式

## 11.2 分类

- 静态代理
- 动态代理 

## 10.3 静态代理

角色分析：

- 抽象角色：一般用接口或者抽象类解决
- 真实角色：被代理的角色
- 代理角色：代理真实角色，代理成功后，会有一些附属操作
- 客户：访问代理对象的人

好处：

- 可以使真实角色的操作更加纯粹，不去关注一些额外的业务
- 额外业务就交付给代理角色，实现业务的分工。
- 额外业务发生扩展的时候，方便集中管理。

缺点：

- 一个真实角色，就会产生一个代理角色，代码量较大。

## 10.4 动态代理

角色分析：

- 动态代理与静态代理的角色相同
- 代理类是动态生成的
- 动态代理分为两大类：基于接口的动态代理和基于类的动态代理
  - 基于接口 ——JDK的动态代理
  - 基于类——cglib
  - java字节码实现：javasist

需要了解两个类：Proxy：代理 ，InvocationHandler：调用处理程序实现的接口

```java
package com.chen.demo03;

import com.chen.demo2.Rent;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

//用这个类自动生成代理类
public class ProxyInvocationHandler implements InvocationHandler {
    //被代理的接口
    private Object target;

    public void setTarget(Object target) {
        this.target = target;
    }

    //生成代理类
    public Object getProxy(){
        return Proxy.newProxyInstance(this.getClass().getClassLoader(),
                                target.getClass().getInterfaces(),
                                this);
    }

    //处理代理实例，并返回结果
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //动态代理的本质就是使用放射机制
        Object res = method.invoke(target, args);
        return res;
    }
}
```

# 12、AOP

## 12.1 什么是AOP

AOP为Aspect Oriented Programming的缩写，意为：面向切面编程，通过预编译方式和运行期间动态代理实现程序功能的统一维护的一种技术。AOP是OOP的延续，是软件开发中的一个热点，也是Spring框架中的一个重要内容，是函数式编程的一种衍生范型。利用AOP可以对业务逻辑的各个部分进行隔离，从而使得业务逻辑各部分之间的耦合度降低，提高程序的可重用性，同时提高了开发的效率。

## 12.2使用Spring实现AOP

### 方式一：使用Spring的API接口

1. 接口

   ```java
   package com.chen.service;
   
   public interface UserService {
       public void add();
       public void delete();
       public void update();
       public void selete();
   }
   ```

2. 实现类

   ```java
   package com.chen.service;
   
   public class UserServiceImpl implements UserService{
       @Override
       public void add() {
           System.out.println("增加一个用户");
       }
   
       @Override
       public void delete() {
           System.out.println("删除一个用户");
       }
   
       @Override
       public void update() {
           System.out.println("更新一个用户");
       }
   
       @Override
       public void selete() {
           System.out.println("查询一个用户");
       }
   }
   ```

3. 前后环绕

   ```java
   package com.chen.log;
   
   import org.springframework.aop.MethodBeforeAdvice;
   
   import java.lang.reflect.Method;
   
   public class Log implements MethodBeforeAdvice {
   
       /*
       method: 要执行的目标对象的方法
       args: 参数
       o: 目标对象
        */
   
       @Override
       public void before(Method method, Object[] args, Object o) throws Throwable {
           System.out.println(o.getClass().getName()+"的"+method.getName()+"被执行");
       }
   }
   ```

   ```java
   package com.chen.log;
   
   import org.springframework.aop.AfterReturningAdvice;
   
   import java.lang.reflect.Method;
   
   public class AfterLog implements AfterReturningAdvice {
   
       /*
   
        */
       @Override
       public void afterReturning(Object returnValue, Method method, Object[] objects, Object o1) throws Throwable {
           System.out.println("执行了"+method.getName()+"方法，返回结果为"+returnValue);
       }
   }
   ```

4. applicationContext.xml

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:aop="http://www.springframework.org/schema/aop"
          xsi:schemaLocation="http://www.springframework.org/schema/beans
           https://www.springframework.org/schema/beans/spring-beans.xsd
           http://www.springframework.org/schema/aop
           https://www.springframework.org/schema/aop/spring-aop.xsd">
   
       <bean id="UserService" class="com.chen.service.UserServiceImpl"/>
       <bean id="Log" class="com.chen.log.Log"/>
       <bean id="AfterLog" class="com.chen.log.AfterLog"/>
   
       <!--方式一-->
       <aop:config>
           <!--切入点-->
           <!--expression:表达式  execution(需要执行的位置 * * * * *)-->
           <aop:pointcut id="pointcut" expression="execution(* com.chen.service.UserServiceImpl.*(..))"/>
           <!--执行环绕增加-->
           <aop:advisor advice-ref="Log" pointcut-ref="pointcut"/>
           <aop:advisor advice-ref="AfterLog" pointcut-ref="pointcut"/>
       </aop:config>
   </beans>
   ```

5. 测试类

   ```java
   import com.chen.service.UserService;
   import com.chen.service.UserServiceImpl;
   import org.junit.Test;
   import org.springframework.context.ApplicationContext;
   import org.springframework.context.support.ClassPathXmlApplicationContext;
   
   public class Mytest {
       @Test
       public void Test1(){
           ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
   
           //动态代理，代理的是接口！
           UserService userService = (UserService) context.getBean("UserService");
   
           userService.add();
       }
   }
   ```

### 方式二：自定义类实现AOP，主要是切面

1. diy

   ```java
   package com.chen.diy;
   
   public class DiyPointCut {
   
       public void before(){
           System.out.println("method exec before");
       }
   
       public void after(){
           System.out.println("method exec after");
       }
   }
   ```

2. applicationContext.xml

   ```xml
   <!--方式二-->
   <bean id="diy" class="com.chen.diy.DiyPointCut"/>
   
   <aop:config>
       <aop:aspect ref="diy">
           <aop:pointcut id="point" expression="execution(* com.chen.service.UserServiceImpl.*(..))"/>
           <aop:before method="before" pointcut-ref="point"/>
           <aop:after method="after" pointcut-ref="point"/>
       </aop:aspect>
   </aop:config>
   ```

### 方式三：使用注解实现AOP

1. 切面类

   ```java
   package com.chen.diy;
   
   import org.aspectj.lang.ProceedingJoinPoint;
   import org.aspectj.lang.annotation.After;
   import org.aspectj.lang.annotation.Around;
   import org.aspectj.lang.annotation.Aspect;
   import org.aspectj.lang.annotation.Before;
   
   //标注这个类是一个切面
   @Aspect
   public class AnnotataionPointCut {
   
       @Before("execution(* com.chen.service.UserServiceImpl.*(..))")
       public void before(){
           System.out.println("method exec beforr");
       }
       @After("execution(* com.chen.service.UserServiceImpl.*(..))")
       public void after(){
           System.out.println("method exec after");
       }
       @Around("execution(* com.chen.service.UserServiceImpl.*(..))")
       public void around(ProceedingJoinPoint jp) throws Throwable {
           System.out.println("环绕前");
           Object proceed = jp.proceed();
           System.out.println("环绕后");
       }
   }
   ```

2. applicationContext.xml

   ```xml
   <!--方式三-->
   <bean id="annotationPointCut" class="com.chen.diy.AnnotataionPointCut"/>
   <!--开启注解支持 JDK（默认，proxy-target-class = "false"）
                       cglib(proxy-target-class = "true")
       -->
   <aop:aspectj-autoproxy/>
   ```

# 13、整合Mybatis

步骤：

1. 导入jar包

   - junit

     ```xml
     <dependency>
         <groupId>junit</groupId>
         <artifactId>junit</artifactId>
         <version>4.12</version>
     </dependency>
     ```

   - mybatis

     ```xml
     <dependency>
         <groupId>org.mybatis</groupId>
         <artifactId>mybatis</artifactId>
         <version>3.5.2</version>
     </dependency>
     ```

   - mysql数据

     ```xml
     <dependency>
         <groupId>mysql</groupId>
         <artifactId>mysql-connector-java</artifactId>
         <version>5.1.47</version>
     </dependency>
     ```

   - spring相关

     ```xml
     <dependency>
         <groupId>org.springframework</groupId>
         <artifactId>spring-webmvc</artifactId>
         <version>5.2.9.RELEASE</version>
     </dependency>
     <dependency>
         <groupId>org.springframework</groupId>
         <artifactId>spring-jdbc</artifactId>
         <version>5.1.9.RELEASE</version>
     </dependency>
     ```

   - aop

     ```xml
     <dependency>
         <groupId>org.aspectj</groupId>
         <artifactId>aspectjweaver</artifactId>
         <version>1.9.4</version>
     </dependency>
     ```

   - mybatis-spring

     ```xml
     <dependency>
         <groupId>org.mybatis</groupId>
         <artifactId>mybatis-spring</artifactId>
         <version>2.0.5</version>
     </dependency>
     ```

2. 编写配置文件

3. 测试

## 13.1 回忆mybatis

1. 编写实体类
2. 编写核心配置文件
3. 编写接口
4. 编写Mapper.xml
5. 测试



## 13.2 Mybatis-Spring方式一

1. 编写数据源

   ```xml
   <!--DataSource:使用spring的数据源替换mybatis的配置-->
   <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
       <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
       <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSL=false&amp;useUnicode=true&amp;characterEncoding=UTF-8"/>
       <property name="username" value="root"/>
       <property name="password" value="0000"/>
   </bean>
   ```

2. sqlSessionFactory

   ```xml
   <!--sqlsessionfactory-->
   <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
       <property name="dataSource" ref="dataSource" />
       <!--绑定mybatis-->
       <property name="configLocation" value="classpath:mybatis-config.xml"/>
   </bean>
   ```

3. sqlSessionTemplate

   ```xml
   <!--SqlSessionTemplate就是SqlSession-->
   <bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
       <!--只能通过构造器注入，应该没有set方法-->
       <constructor-arg index="0" ref="sqlSessionFactory"/>
   </bean>
   ```

4. 接口加实现类

   ```java
   package com.chen.mapper;
   
   import com.chen.pojo.User;
   import org.mybatis.spring.SqlSessionTemplate;
   
   import java.util.List;
   
   public class UserMapperImpl implements UserMapper{
       //原来的操作，都有sqlSession来执行，现在都通过sqlSessionTemplate
       private SqlSessionTemplate sqlSession;
   
       public void setSqlSession(SqlSessionTemplate sqlSession) {
           this.sqlSession = sqlSession;
       }
   
       @Override
       public List<User> selectUser() {
           UserMapper mapper = sqlSession.getMapper(UserMapper.class);
           return mapper.selectUser();
       }
   }
   ```

5. 将实现类注入到Spring中

   ```xml
   <bean id="userMapper" class="com.chen.mapper.UserMapperImpl">
       <property name="sqlSession" ref="sqlSession"/>
   </bean>
   ```

6. 测试类

   ```java
   import com.chen.mapper.UserMapper;
   import com.chen.pojo.User;
   import org.junit.Test;
   import org.springframework.context.ApplicationContext;
   import org.springframework.context.support.ClassPathXmlApplicationContext;
   
   import java.io.IOException;
   public class MyTest {
       @Test
       public void selectUserTest() throws IOException {
           ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
           UserMapper userMapper = context.getBean("userMapper", UserMapper.class);
           List<User> users = userMapper.selectUser();
           for (User user : users) {
               System.out.println(user.toString());
           }
       }
   }
   ```

##  13.3 关键对象

1. Spring-dao.xml

   ```xml
   <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
       <property name="dataSource" ref="dataSource" />
       <!--绑定mybatis配置文件-->
       <property name="configLocation" value="classpath:mybatis-config.xml"/>
   </bean>
   
   <bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
       <!--利用构造器注入-->
       <constructor-arg index="0" ref="sqlSessionFactory"/>
   </bean>
   ```

## 13.3 Mybatis-Spring方式二

官方说明

![image-20201030093631880](C:\Users\chend\AppData\Roaming\Typora\typora-user-images\image-20201030093631880.png)

1. MapperImpl.java

   ```java
   package com.chen.mapper;
   
   import com.chen.pojo.User;
   import org.mybatis.spring.support.SqlSessionDaoSupport;
   
   import java.util.List;
   //继承SqlSessionDaoSupport
   public class UserMapperImpl2 extends SqlSessionDaoSupport implements UserMapper{
       @Override
       public List<User> selectUser() {
           return getSqlSession().getMapper(UserMapper.class).selectUser();
       }
   }
   ```

2. applicationContext.xml

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://www.springframework.org/schema/beans
           https://www.springframework.org/schema/beans/spring-beans.xsd">
   
       <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
           <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
           <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSL=false&amp;useUnicode=true&amp;characterEncoding=UTF-8"/>
           <property name="username" value="root"/>
           <property name="password" value="0000"/>
       </bean>
   
       <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
           <property name="dataSource" ref="dataSource" />
           <property name="configLocation" value="classpath:mybatis-config.xml"/>
       </bean>
   
   	<bean id="userMapper2" class="com.chen.mapper.UserMapperImpl2">
           <property name="sqlSessionFactory" ref="sqlSessionFactory"/>
       </bean>
   </beans>
   ```

   

# 14、声明式事务

## 14.1 回顾事务

- 要把一组业务当成一个业务来做，要么都成功，要么都失败。
- 涉及到数据的一致性问题，不能马虎。
- 确保完整性和一致性。

事务的ACID原则：

- 原子性
- 一致性
- 隔离性
  - 多个业务可能操作同一个资源，防止数据损坏
- 持久性
  - 事务一旦提交，无论系统发生什么问题，结果都不会被影响。

## 14.2 事务

为什么需要事务？

- 如果不配置事务，可能存在数据提交不一致的情况下
- 如果不在Spring中配置声明式事务，就需要在代码中配置事务
- 事务十分重要，涉及数据的一致性和完整性

官网指南：

![image-20201030102054684](C:\Users\chend\AppData\Roaming\Typora\typora-user-images\image-20201030102054684.png)

## 14.3 声明式事务

1. 声明事务

   ```xml
   <!--声明式事务-->
   <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
       <constructor-arg ref="dataSource" />
   </bean>
   ```

2. 结合AOP织入事务

   ```xml
   <!--结合AOP实现事务的织入-->
   <!--配置事务通知-->
   <tx:advice id="txAdvice" transaction-manager="transactionManager">
       <!--给哪些方法配置事务-->
       <!--配置事务的传播特性  propagation="REQUIRED"-->
       <tx:attributes>
           <tx:method name="add" propagation="REQUIRED"/>
           <tx:method name="delete" propagation="REQUIRED"/>
           <tx:method name="update" propagation="REQUIRED"/>
           <tx:method name="query" read-only="true"/>
           <tx:method name="*" propagation="REQUIRED"/>
       </tx:attributes>
   </tx:advice>
   ```

3. 配置事务的切入

   ```xml
   <!--配置事务的切入-->
   <aop:config>
       <!--execution(* com.chen.mapper.*(所有类).*(所有方法).(..)(参数类型))-->
       <aop:pointcut id="txPoint" expression="execution(* com.chen.mapper.*.*(..))"/>
       <aop:advisor advice-ref="txAdvice" pointcut-ref="txPoint"/>
   </aop:config>
   ```

   
