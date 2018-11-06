package com.leekoko.taozhi;

import com.leekoko.taozhi.TestAPJ.pojo.User;
import com.leekoko.taozhi.TestAPJ.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class TaozhiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaozhiApplication.class, args);
	}

	@RequestMapping(value="/hello2")
	public String say(){
		return "Hello 淘知222";
	}

}
