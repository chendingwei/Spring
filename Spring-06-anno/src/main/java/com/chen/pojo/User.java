package com.chen.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//等价于<bean id="User" class="com.chen.pojo.User"/>
@Component
public class User {
    //相当于
    // <property name="name" value="chen"/>
    @Value("chen")
    public String name;
}
