package com.yuyh.reflection.annotation;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author yuyh.
 * @date 2016/6/13.
 */
public class Inject {
    public static final String TAG = "Reflection";

    public static void inject(Activity activity) {
        injectView(activity);

        injectClick(activity);
    }

    /**
     * 解析View注解
     *
     * @param activity
     */
    private static void injectView(Activity activity) {
        Class clazz = activity.getClass();
        Log.i(TAG, clazz.getName());

        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            InjectView injectView = field.getAnnotation(InjectView.class);
            if (injectView != null && injectView.value() != 0) {
                int id = injectView.value();
                try {
                    field.setAccessible(true);
                    field.set(activity, activity.findViewById(id));
                } catch (IllegalAccessException e) {
                    Log.e(TAG, "IllegalAccessException = " + e.toString());
                }
            }
        }
    }

    /**
     * 解析OnClick以及OnLongClick注解
     *
     * @param activity
     */
    private static void injectClick(final Activity activity) {
        Class clazz = activity.getClass();
        Log.i(TAG, clazz.getName());

        Method[] methods = clazz.getDeclaredMethods();
        for (final Method method : methods) {
            OnClick click = method.getAnnotation(OnClick.class);
            OnLongClick longClick = method.getAnnotation(OnLongClick.class);
            if (click != null && click.value() != 0) {
                View view = activity.findViewById(click.value());//通过注解的值获取View控件
                if (view == null)
                    return;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            method.invoke(activity, v);//通过反射来调用被注解修饰的方法，把View传回去
                        } catch (InvocationTargetException e) {
                            Log.e(TAG, "InvocationTargetException = " + e.toString());
                        } catch (IllegalAccessException e) {
                            Log.e(TAG, "IllegalAccessException = " + e.toString());
                        }
                    }
                });
            }

            if (longClick != null && longClick.value() != 0) {
                View view = activity.findViewById(click.value());
                if (view == null)
                    return;
                view.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        try {
                            method.invoke(activity, v);
                        } catch (InvocationTargetException e) {
                            Log.e(TAG, "InvocationTargetException = " + e.toString());
                        } catch (IllegalAccessException e) {
                            Log.e(TAG, "IllegalAccessException = " + e.toString());
                        }
                        return true;
                    }
                });
            }
        }
    }
}
