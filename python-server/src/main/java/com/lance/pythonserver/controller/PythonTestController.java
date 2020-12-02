package com.lance.pythonserver.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class PythonTestController {

    @RequestMapping(method = RequestMethod.GET)
    public MyResponse<Map<String, String>> getName(@RequestParam int id) {
        Map<String, String> data = new HashMap<>();
        data.put("name", "zhang" + id);
        return MyResponse.success(data);
    }

    @RequestMapping(method = RequestMethod.POST)
    public MyResponse<Student> updateName(Student student) {
        student.setAge(student.getAge() + 1);
        return MyResponse.success(student);
    }
}
