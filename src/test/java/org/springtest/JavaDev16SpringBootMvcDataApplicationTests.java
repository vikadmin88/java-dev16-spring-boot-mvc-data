package org.springtest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springtest.controller.controller.NoteController;
import org.springtest.controller.controller.RootController;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class JavaDev16SpringBootMvcDataApplicationTests {

    @Autowired
    private RootController rootController;
    @Autowired
    private NoteController noteController;

    @Test
    void contextLoads() {
        assertThat(rootController).isNotNull();
        assertThat(noteController).isNotNull();
    }

}
