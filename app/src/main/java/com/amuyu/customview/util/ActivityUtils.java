package com.amuyu.customview.util;


import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import static com.google.common.base.Preconditions.checkNotNull;

public class ActivityUtils {

    public static void addFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    public static Fragment getFragmentByClassName(String className) {
        try {
            Class cls = Class.forName(className);
            return (Fragment)cls.newInstance();
        } catch (ClassNotFoundException e) {
            return null;
        } catch (IllegalAccessException | InstantiationException e) {
            return null;
        }

    }
}
