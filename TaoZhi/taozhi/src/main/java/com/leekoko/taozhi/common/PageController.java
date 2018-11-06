package com.leekoko.taozhi.common;


import com.leekoko.taozhi.TestAPJ.pojo.User;
import com.leekoko.taozhi.TestAPJ.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 通用页面展示
 * （暂时做测试）
 */
@RestController
@EnableAutoConfiguration
public class PageController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value="/hello")
    public String say(){
        return "Hello 淘知";
    }

    @RequestMapping(value="/listPage")
    public String listPage(){
        Pageable pageable = new PageRequest(0,10,new Sort(Sort.Direction.ASC,"id"));
        Page<User> page = userRepository.findAll(pageable);
        String result = "";
        for (User user:page.getContent()){
            result = user.getName()+":"+user.getDeparment().getName()+":"+user.getRoles().get(0).getName();
        }
        return result;
    }


}
