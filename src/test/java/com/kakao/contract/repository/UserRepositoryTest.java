package com.kakao.contract.repository;

import com.kakao.contract.entity.User;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void userTest(){
        User user = new User();
        user.setName("Loella");
        user.setEmail("lella@gmail.com");
        userRepository.save(user);
        System.out.println(">>>>>>>>>>>>>>>>>>>" + userRepository.findAll());
    }

}
