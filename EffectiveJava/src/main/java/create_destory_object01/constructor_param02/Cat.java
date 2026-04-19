package create_destory_object01.constructor_param02;

import lombok.Getter;

@Getter
public class Cat {
    private final int id;
    private final String name;
    //可选参数
    private final String color;
    private final int age;
    private Cat(Builder builder){
        this.id = builder.id;
        this.name = builder.name;
        this.color = builder.color;
        this.age = builder.age;
    }
    public static class Builder {
        //必须参数
        private final int id;
        private final String name;
        //可选参数
        private String color = "white";
        private int age = 5;
        public Builder(int id, String name) {
            this.id = id;
            this.name = name;
        }
        public Builder color(String color){
            this.color = color;
            return this;
        }
        public Builder age(int age){
            this.age = age;
            return this;
        }
        public Cat builder(){
            return new Cat(this);
        }
    }
}
