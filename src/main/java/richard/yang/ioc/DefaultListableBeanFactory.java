package richard.yang.ioc;

import lombok.Data;
import richard.yang.ioc.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 既是一个工厂，又是一个注册器
 */

@Data
public class DefaultListableBeanFactory implements BeanFactory, BeanDefinitionRegistry {

    //保存beanName=>bean Object的映射关系
    private Map<String, Object> beanObjects = new ConcurrentHashMap<String, Object>();
    //保存beanName=>beanDefinition的映射关系
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();


    public Object getBean(String name) throws Exception {
        // 判断是否已经实例化，已经实例化则直接返回对象
        Object beanObject = beanObjects.get(name);
        if (beanObject != null) {
            return beanObject;
        }

        BeanDefinition beanDefinition = beanDefinitionMap.get(name);

        if (beanDefinition == null) {
            throw new Exception("no bean name " + name);
        }

        // 创建bean
        Object object = createBean(beanDefinition);
        // 注入依赖
        inject(object);

        // 存入实例对象map中
        beanObjects.put(name, object);

        return object;
    }

    /**
     * 创建bean
     *
     * @param beanDefinition
     * @return
     * @throws Exception
     */
    public Object createBean(BeanDefinition beanDefinition) throws Exception {

        // 通过反射的方式进行创建
        String className = beanDefinition.getClassName();

        Class<?> aClass = Class.forName(className);

        return aClass.newInstance();
    }

    /**
     * 注册bean,其实就是把它存到beanDefinitionMap中
     *
     * @param beanName
     * @param beanDefinition
     * @throws Exception
     */
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) throws Exception {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    /**
     * 实例化bean
     */
    public void preInstantiateBeans() {
        for (String beanName : beanDefinitionMap.keySet()) {
            try {
                getBean(beanName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    /**
     * 注入依赖
     *
     * @param object
     */
    private void inject(Object object) throws Exception {
        //获得属性的注解
        Field[] declaredFields = object.getClass().getDeclaredFields();
        //遍历AutoWired注解
        for (Field declaredField : declaredFields) {
            if(declaredField.isAnnotationPresent(Autowired.class)){
                // 获取依赖的bean
                Object dependBean = getBean(declaredField.getName());
                declaredField.setAccessible(true);
                declaredField.set(object,dependBean);
            }
        }
    }
}
