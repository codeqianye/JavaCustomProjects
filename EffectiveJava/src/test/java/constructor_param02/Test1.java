package constructor_param02;

import create_destory_object01.constructor_param02.Cat;
import org.junit.Test;

public class Test1 {
    @Test
    public void test(){
        Cat cat = new Cat.Builder(1, "coco").age(3).color("yellow").builder();
        System.out.printf("cat id: %d, name: %s, color: %s, age: %d",cat.getId(),cat.getName(),cat.getColor(),cat.getAge());
    }
}
