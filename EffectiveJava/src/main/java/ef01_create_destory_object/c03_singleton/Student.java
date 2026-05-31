package ef01_create_destory_object.c03_singleton;

import java.io.Serializable;

public class Student implements Serializable {
    private String name;
    private transient Integer age;
}
