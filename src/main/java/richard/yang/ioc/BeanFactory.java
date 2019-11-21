package richard.yang.ioc;

/**
 * bean的工厂，负责生产bean和获取bean
 */
public interface BeanFactory {

    /**
     * 获得bean
     * @param name
     * @return
     * @throws Exception
     */
    Object getBean(String name) throws Exception;

    /**
     * 创建bean
     * @param beanDefinition
     * @return
     * @throws Exception
     */
    Object createBean(BeanDefinition beanDefinition) throws Exception;

}
