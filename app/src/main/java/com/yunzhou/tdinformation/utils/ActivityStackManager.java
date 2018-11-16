package com.yunzhou.tdinformation.utils;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by LXP
 * Create Date 2018/9/10 15:21
 * Used Activity管理类
 */
public class ActivityStackManager {
    private static Stack<Activity> activityStack;
    // 保证可见性
    private volatile static ActivityStackManager instance;

    private ActivityStackManager() {
    }

    public synchronized static ActivityStackManager getActivityStackManager() {
        if (instance == null) {
            synchronized (ActivityStackManager.class){
                if (instance == null) {
                    instance = new ActivityStackManager();
                }
            }
        }
        return instance;
    }

    /**
     * finish the activity and remove it from stack.
     *
     * @param activity
     */
    public void popActivity(Activity activity) {
        if (activityStack == null) return;
        if (activity != null) {
            activityStack.remove(activity);
            activity = null;
        }
    }

    /**
     * 获取当前的Activity
     * get the current activity.
     *
     * @return
     */
    public Activity currentActivity() {
        if (activityStack == null || activityStack.isEmpty()) return null;
        return activityStack.lastElement();
    }

    /**
     * 获取最后一个的Activity
     * get the first activity in the stack.
     *
     * @return
     */
    public Activity firstActivity() {
        if (activityStack == null || activityStack.isEmpty()) return null;
        return activityStack.firstElement();
    }


    /**
     * 添加activity到Stack
     * add the activity to the stack.
     *
     * @param activity
     */
    public void pushActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * remove所有Activity
     * remove all activity.
     */
    public void popAllActivity() {
        if (activityStack == null) return;
        while (true) {
            if (activityStack.empty()) {
                break;
            }
            Activity activity = currentActivity();
            popActivity(activity);
        }
    }

    /**
     * remove所有Activity但保持目前的Activity
     * remove all activity but keep the current activity.
     */
    public void popAllActivityWithOutCurrent() {
        Activity activity = currentActivity();
        while (true) {
            if (activityStack.size() == 1) {
                break;
            }
            if (firstActivity() == activity) {
                break;
            } else {
                popActivity(firstActivity());
            }
        }
    }
}
