package com.lance.springboot.datasourcedemo;

import com.lance.springboot.datasourcedemo.entity.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DatasourceDemoApplication.class)
public class DatasourceDemoApplicationTests {

    @Autowired
    @Qualifier("myDataSource")
    DataSource dataSource;

    @Test
    public void contextLoads() throws SQLException {
        System.out.println(dataSource);
        Connection connection = dataSource.getConnection();

        List<Student> students = new ArrayList<Student>(16);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from student");
        Student student = null;
        while (resultSet.next()) {
            student = new Student();
            student.setId(resultSet.getInt("id"));
            student.setName(resultSet.getString("name"));
            student.setPassword(resultSet.getString("password"));
            student.setAge(resultSet.getInt("age"));
            students.add(student);
        }
        System.out.println(students);

    }

}
