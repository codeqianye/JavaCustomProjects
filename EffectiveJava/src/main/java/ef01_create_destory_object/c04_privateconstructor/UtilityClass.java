package ef01_create_destory_object.c04_privateconstructor;

public class UtilityClass {
    /**
     * 私有构造方法，用于防止该工具类被实例化
     * 这种设计模式常用于只包含静态方法和静态字段工具类
     */
    private UtilityClass() {
      // 抛出运行时异常，防止通过反射创建实例
       throw new RuntimeException("Cannot be instantiated");
    }
}
