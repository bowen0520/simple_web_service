package Properties;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String args[]) {
        Student a = new Student(1);
        test(a);
        System.out.println(a.a);

        //List l = null;
        //l.add("a");
        //System.out.println(l.size());
        List list = new ArrayList();
        System.out.println(list);
        test2(list);
        System.out.println(list.size());
        test3(list);
        System.out.println(list.size()); // 2处
    }
    public static void test(Student student){
        student = new Student(2);
    }

    public static void test2(List list) {
        list = null;
    }

    public static void test3(List list) {
        list.add("aaaa");
    }

}
class Student {
    int a;

    public Student(int a) {
        this.a = a;
    }
}
