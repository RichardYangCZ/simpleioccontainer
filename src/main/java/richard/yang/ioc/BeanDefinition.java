package richard.yang.ioc;

import lombok.Data;

/**
 * 承载bean的信息，本项目只保存bean class
 */

@Data
public class BeanDefinition {
    /**
     * 存储类名
     */
    private String className;
}
