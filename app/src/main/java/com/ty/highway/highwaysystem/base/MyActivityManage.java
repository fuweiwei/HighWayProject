package com.ty.highway.highwaysystem.base;

import android.app.Activity;
import android.util.Log;

import java.util.Stack;

/**
 * 管理Activity栈
 * Created by ${dzm} on 2015/9/8 0008.
 */
public class MyActivityManage {

    private static MyActivityManage instance;
    private Stack<Activity> activityStack = null;

    private MyActivityManage() {
    }

    public static MyActivityManage getInstance() {
        if (instance == null) {
            instance = new MyActivityManage();
        }
        return instance;
    }

    /**
     * 当前activity出栈
     */
    public void popActivity() {
        Activity activity = activityStack.lastElement();
        if (activity != null) {
            activity.finish();
            activity = null;
        }
    }

    /**
     * 出栈
     *
     * @param activity
     */
    public void popActivity(Activity activity) {
        if (activityStack != null && activityStack.size() > 0) {
            if (activity != null) {
                activity.finish();
                activityStack.remove(activity);
                activity = null;
            }
        }
    }

    /**
     * 获取当前activity
     *
     * @return
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * activity入栈
     *
     * @param activity
     */
    public void pushActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 退出所有activity
     */
    public void popAllActivity() {
        if (activityStack != null) {
            while (activityStack.size() > 0) {
                Activity activity = currentActivity();
                if (activity == null) {
                    break;
                }
                popActivity(activity);
            }
        }
    }
}
