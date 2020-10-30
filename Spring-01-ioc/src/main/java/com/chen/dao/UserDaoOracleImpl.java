package com.chen.dao;

public class UserDaoOracleImpl implements UserDao{
    @Override
    public void getUser() {
        System.out.println("通过oracle获取数据");
    }
}
