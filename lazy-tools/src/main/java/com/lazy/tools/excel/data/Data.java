//package com.lazy.tools.excel.data;
//
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author futao
// * Created on 2019/11/18.
// */
//public class Data {
//
//    public static List<User> get() {
//        int capacity = 1000000;
//        ArrayList<User> users = new ArrayList<>(capacity);
//        for (int i = 0; i < capacity; i++) {
//            User user = new User(
//                    "easyPoi" + i,
//                    1,
//                    new Address(
//                            i + "",
//                            "浙江",
//                            "HZ",
//                            "南京西路"
//                    ), i
//            );
//            user.setCreateTime(new Timestamp(System.currentTimeMillis()));
//            user.setUpdateTime(new Timestamp(System.currentTimeMillis()));
//            users.add(user);
//        }
//        return users;
//    }
//}
