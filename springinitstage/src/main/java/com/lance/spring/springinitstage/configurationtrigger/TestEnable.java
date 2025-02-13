package com.lance.spring.springinitstage.configurationtrigger;

import com.lance.authority.autoconfigure2.MyEnableAnnotation;

//加到这种非spring控制的bean上面，不起作用，spring不会扫描到
@MyEnableAnnotation
public class TestEnable {
}
