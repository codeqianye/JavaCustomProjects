package singleton03;

import ef01_create_destory_object.c03_singleton.Elvis2;

import java.util.function.Supplier;

public class Test1 {
    public static void main(String[] args) {
        Supplier<Elvis2> supplier = Elvis2::getInstance;
        Elvis2 elvis2 = supplier.get();
        System.out.println(elvis2);
    }
}
