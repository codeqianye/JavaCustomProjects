package constructor_param02;

import ef01_create_destory_object.c02_constructor_param.Cat;
import org.junit.Test;

public class Test1 {
    @Test
    public void test(){
        Cat cat = new Cat.Builder(1, "coco").age(3).color("yellow").builder();
        System.out.printf("cat id: %d, name: %s, color: %s, age: %d",cat.getId(),cat.getName(),cat.getColor(),cat.getAge());
    }
}
