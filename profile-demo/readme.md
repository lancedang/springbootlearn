#### SpringBoot环境下@Profile使用
1. 定义不同application-{env}.properties
2. 指定spring.profiles.active属性使Spring激活具体某个环境
    1. 默认配置文件application.properties中指定 spring.profiles.active=dev
    2. java jar命令行添加参数： java -jar -Dspring.profiles.active=dev  profile-demo.jar
3. 验证
    1. 读取不同配置文件中的相同属性
    2. 读取@Profile修饰类中的@Bean方法
 
4. 结论
    1. 指定active profile后**自动在env中注入对应环境的属性**
        - 通过@Value("${user.nickname}")可获取    
    2. 指定active profile后**激活@Profile修饰的类，并初始化其中的@Bean修饰的方法**