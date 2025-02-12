package com.lance.pdfdemo.freemarker;

import lombok.Data;

@Data
public class User {
  private Integer id;
  private String username; 
  private String address;
  private Long date=1606924800000L;
  //图片方式2:作为model变量形式传递进去
  private String image="https://img1.bdstatic.com/static/common/img/icon_cf1b905.png";
  //省略 getter/setter
}