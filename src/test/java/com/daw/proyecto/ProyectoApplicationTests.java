package com.daw.proyecto;

import org.apache.catalina.core.ApplicationContext;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
class ProyectoApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @org.junit.Test
    void contextLoads() {
        assertNotNull(applicationContext);
    }

    @Test
    void entryPointTest() {
        assertDoesNotThrow(() -> ProyectoApplication.main(new String[]{}));
    }

}
