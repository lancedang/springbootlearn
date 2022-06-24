package com.lance.pdfdemo.freemarker;

import com.lance.pdfdemo.util.Itext5HtmlToPDFUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private Configuration configuration;

    @GetMapping("/index")
    public String index(Model model) throws Exception {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setId(i);
            user.setUsername("用户名" + i);
            user.setAddress("地址" + i);

            users.add(user);
        }
        model.addAttribute("users", users);

        String test = test();

        HtmlUtil.html2Pdf(test, "test-1.pdf");

        Itext5HtmlToPDFUtil.toPdf(test, "test-2.pdf");

        return "index";
    }

    public String test() throws IOException, TemplateException {
        Template template = configuration.getTemplate("index.ftl","utf-8");
        Writer out = new StringWriter();
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setId(i);
            //todo,PDF中文打印不出
            user.setUsername("姓名按时发达的法定法法师法师法师打发算法算法三法师法师法师法师的法师法师法发顺丰打法发顺丰发撒暗室逢灯-" + i);
            user.setAddress("地址-" + i);
            users.add(user);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("users", users);

        template.process(map, out);
        out.flush();
        return out.toString();
    }

    public String test2() throws IOException, TemplateException {
        Template template = configuration.getTemplate("index.ftl","utf-8");
        Writer out = new StringWriter();
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setId(i);
            //todo,PDF中文打印不出
            user.setUsername("name-" + i);
            user.setAddress("address-" + i);
            users.add(user);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("users", users);

        template.process(map, out);
        out.flush();
        return out.toString();
    }
}