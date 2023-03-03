package io.github.thedxns.todo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TodoApplicationTests {

	@Mock
    TodoApplication todoApplication;

    @Test
    @DisplayName("The context should not be null")
    void contextLoads() {
		Assertions.assertNotNull(todoApplication);
    }

}
