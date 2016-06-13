package com.yuyh.reflection.java;

import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Test {

    public static final String TAG = "Reflection";

    /**
     * 获取Class对象的三种方式
     */
    public static Class<?> getClassObj() {
        // 根据类名获取Class对象
        Class<?> clazz1 = People.class;

        // 根据对象获取Class对象
        People people = new People();
        Class<?> clazz2 = people.getClass();

        // 根据完整类名获取Class对象
        try {
            Class<?> clazz3 = Class.forName("com.yuyh.reflection.java.People");
        } catch (ClassNotFoundException e) {
            Log.e(TAG, e.toString());
        }

        Log.i(TAG, "clazz1 = " + clazz1);

        return clazz1; // clazz2 clazz3
    }

    /**
     * 反射获取类的对象
     *
     * @return
     */
    public static Object getObject() {
        try {
            // 获取类的Class对象
            Class<?> clz = getClassObj();
            // 获取类对象的Constructor
            Constructor<?> constructor = clz.getConstructor(String.class, int.class, String.class);
            // 在使用时取消 Java语言访问检查，提升反射性能
            constructor.setAccessible(true);
            // 通过 Constructor 来创建对象
            Object obj = constructor.newInstance("yuyh", 25, "xxx@gmail.com");
            Log.i(TAG, obj.toString());

            return obj;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return null;
    }

    /**
     * 反射获取类的方法
     */
    public static void getDeclaredMethods() {
        People people = (People) getObject();
        // 获取到类中的所有方法(不包含从父类继承的方法)
        Method[] methods = people.getClass().getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            Log.i(TAG, "method[" + i + "] = " + methods[i].getName());
        }

        try {
            // 获取类中的某个方法
            Method method = people.getClass().getDeclaredMethod("setEMail", String.class);
            // 判断是否是public方法
            Log.i(TAG, "method is public = " + Modifier.isProtected(method.getModifiers()));
            // 获取该方法的参数类型列表
            Class<?>[] paramTypes = method.getParameterTypes();
            for (int i = 0; i < paramTypes.length; i++) {
                Log.i(TAG, "paramTypes[" + i + "] = " + paramTypes[i].getName());
            }

            Log.i(TAG, "people.email befor= " + people.getEMail());

            // 执行该方法
            method.invoke(people, "xxx@163.com");

            Log.i(TAG, "people.email after= " + people.getEMail());
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    /**
     * 反射获取类的属性
     */
    public static void getDeclaredFields() {
        People people = (People) getObject();
        // 获取当前类和父类的所有属性
        Field[] fields = people.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Log.i(TAG, "fields[" + i + "] = " + fields[i].getName());
        }

        try {
            // 获取当前类的某个属性
            Field field = people.getClass().getDeclaredField("name");
            // 获取属性值
            Log.i(TAG, "people.name before = " + field.get(people));

            // 设置属性值
            field.set(people, "yuyh1");

            Log.i(TAG, "people.name after = " + field.get(people));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取对象的父类
     */
    public static void getSuperClass() {
        Student student = new Student("142315079");
        Class<?> superClass = student.getClass().getSuperclass();
        while (superClass != null) {
            Log.i(TAG, "superClass = " + superClass.getName());
            superClass = superClass.getSuperclass(); // 循环获取上一层父类（如果存在）,至少存在一层java.lang.Object
        }
    }


    /**
     * 获取对象实现的接口
     */
    public static void getInterface() {
        Student student = new Student("142315079");
        // 获取该类实现的所有接口
        Class<?>[] interfaces = student.getClass().getInterfaces();
        for (int i = 0; i < interfaces.length; i++) {
            Log.i(TAG, "interfaces[" + i + "] = " + interfaces[i].getName());
        }
    }


}
