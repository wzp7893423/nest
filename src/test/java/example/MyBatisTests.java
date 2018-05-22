package example;

import com.nest.admin.config.AutoDataJdbcConfig;
import com.nest.function.sys.user.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Demonstrates queries can be mapped using MyBatis.
 *
 * @author Jens Schauder
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutoDataJdbcConfig.class)
@AutoConfigureJdbc
public class MyBatisTests {

    @Autowired
    UserRepository userRepository;

    @Test
    public void exerciseSomewhatComplexEntity() {

    }
}
