package richard.yang.ioc;

/**
 * bean的注册接口
 */
public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition)
            throws Exception;
}
