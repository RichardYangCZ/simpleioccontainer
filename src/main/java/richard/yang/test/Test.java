package richard.yang.test;

import richard.yang.ioc.ApplicationContext;

public class Test {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ApplicationContext(AppConfig.class);
        System.out.println(applicationContext.getBean("tiger").toString());
    }
}
