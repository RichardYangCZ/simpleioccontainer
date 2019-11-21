package richard.yang.test.bean;

import richard.yang.ioc.annotation.Autowired;
import richard.yang.ioc.annotation.Component;

@Component
public class Tiger {

    @Autowired
    private Cat cat;

    @Override
    public String toString() {
        return cat.toString();
    }
}
