package com.fcdream.dress.kenny.ioc;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by shmdu on 2017/8/31.
 */

public class AnnotateUtil {

    private static final String TAG = AnnotateUtil.class.getName();

    public static int getBindLayoutResId(Object currentClass) {
        BindLayout bindLayout = currentClass.getClass().getAnnotation(BindLayout.class);
        return bindLayout.layout();
    }

    /**
     * @param currentClass
     * @param sourceView
     */
    public static void initBindView(Object currentClass, View sourceView) {
        bindView(currentClass.getClass(), currentClass, sourceView);
        // 初始化父类
        Class<?> superClazz = currentClass.getClass().getSuperclass();
        while (superClazz != null) {
            bindView(superClazz, currentClass, sourceView);
            superClazz = superClazz.getSuperclass();
        }
    }

    private static void bindView(Class<?> clazz, Object currentClass, View sourceView) {
        Field[] fields = clazz.getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                // 返回BindView类型的注解内容
                BindView bindView = field.getAnnotation(BindView.class);
                if (bindView != null) {
                    int viewId = bindView.id();
                    boolean isClick = bindView.click();
                    try {
                        field.setAccessible(true);
                        View view = sourceView.findViewById(viewId);
                        if (view == null) {
                            continue;
                        }
                        if (isClick && currentClass instanceof android.view.View.OnClickListener) {
                            view.setOnClickListener((android.view.View.OnClickListener) currentClass);
                        }
                        field.set(currentClass, view);
                    } catch (Exception e) {
                        Log.d("TAG", "BindView error : " + field.getName());
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * @param activity
     */
    public static void initBindView(Activity activity) {
        initBindView(activity, activity.getWindow().getDecorView());
    }

    public static void invokeMehode(View view, Object currentClass) {
        boolean isHold = holdInvokeMehode(currentClass.getClass(), view, currentClass);
        if (isHold) {
            return;
        }
        Class<?> superClazz = currentClass.getClass().getSuperclass();
        while (superClazz != null) {
            isHold = holdInvokeMehode(superClazz, view, currentClass);
            if (isHold) {
                return;
            }
            superClazz = superClazz.getSuperclass();
        }
    }

    private static boolean holdInvokeMehode(Class<?> clazz, View view, Object currentClass) {
        Field[] fileds = clazz.getDeclaredFields();
        for (Field field : fileds) {
            if (field.isAnnotationPresent(BindView.class)) {
                BindView bindView = field.getAnnotation(BindView.class);
                if (bindView.id() == view.getId()) {
                    String method = bindView.clickEvent();
                    if (TextUtils.isEmpty(method)) {
                        continue;
                    }
                    try {
                        Method m = clazz.getDeclaredMethod(method, View.class);
                        m.setAccessible(true);
                        m.invoke(currentClass, view);
                        return true;
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                        Log.d(TAG, "InvokeMehode error : " + field.getName());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        Log.d(TAG, "InvokeMehode error : " + field.getName());
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                        Log.d(TAG, "InvokeMehode error : " + field.getName());
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                        Log.d(TAG, "InvokeMehode error : " + field.getName());
                    }
                }
            }
        }
        return false;
    }
}
