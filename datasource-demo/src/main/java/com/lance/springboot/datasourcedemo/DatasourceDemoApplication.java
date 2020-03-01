package com.lance.springboot.datasourcedemo;

import com.lance.springboot.datasourcedemo.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@SpringBootApplication
public class DatasourceDemoApplication implements CommandLineRunner {

    @Autowired
    @Qualifier("myDataSource")
    DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(DatasourceDemoApplication.class, args);
        //System.out.println("hi");
    }

    public void run(String... args) throws Exception {
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
