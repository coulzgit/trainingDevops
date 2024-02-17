package com.UnitTest.springUnitTest;

import com.UnitTest.springUnitTest.domains.ToDoRepository;
import com.UnitTest.springUnitTest.domains.ToDoServiceImpl;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
        basePackages = {
                "com.UnitTest.springUnitTest.domains"
        }
)
@MockBeans({@MockBean(ToDoRepository.class), @MockBean(ToDoServiceImpl.class)})
public class TestConfig {
}

