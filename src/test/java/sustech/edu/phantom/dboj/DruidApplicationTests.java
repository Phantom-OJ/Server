package sustech.edu.phantom.dboj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DruidApplicationTests {
    @Autowired
    DataSource dataSource;

    @Test
    public void contextLoads() throws SQLException{
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from users");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String username = resultSet.getString("password");
            System.out.println(username);
        }
    }

}
