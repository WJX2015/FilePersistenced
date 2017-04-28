package com.example.lenovo_g50_70.filepersistenced;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by lenovo-G50-70 on 2017/4/14.
 * 测试内存泄漏类
 */

public class ActivityPool {
    public static ActivityPool activityPool = new ActivityPool();
    public static ArrayList<Activity> activities = new ArrayList<>();

    public static ActivityPool getActivity() {
        return activityPool;
    }

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }
}
