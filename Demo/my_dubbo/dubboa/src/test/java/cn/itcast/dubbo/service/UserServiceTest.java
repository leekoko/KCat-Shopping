package cn.itcast.dubbo.service;


import cn.itcast.dubbo.pojo.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class UserServiceTest {

    private UserService userService;

    @org.junit.Before
    public void setUp() throws Exception {
        //UserService初始化
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:dubbo/dubbo-*.xml");
        this.userService = applicationContext.getBean(UserService.class);
    }

    @org.junit.Test
    public void queryAll() {
        for (int i = 0; i < 100; i++) {
            List<User> list = this.userService.queryAll();
            for (User user : list) {
                System.out.println(user);
            }
            try {
                Thread.sleep(i * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}