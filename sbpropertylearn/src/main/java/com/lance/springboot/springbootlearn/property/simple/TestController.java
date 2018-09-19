package com.lance.springboot.springbootlearn.property.simple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web")
public class TestController {

    @Autowired
    private CountryProperty countryProperty;

    @Autowired
    private CountryProperty2 countryProperty2;

    @Autowired
    private CountryProperty3 countryProperty3;

    @RequestMapping("/test")
    public String testProperty() {
        //String country = countryProperty.toString();
        //String country = countryProperty2.toString();
        String country = countryProperty3.toString();
        System.out.println(country);
        return country;

    }

}
