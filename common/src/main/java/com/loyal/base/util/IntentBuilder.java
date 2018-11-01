package com.loyal.base.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.loyal.base.impl.IntentFrame;

public class IntentBuilder extends Intent implements IntentFrame.ActFrame, IntentFrame.FragFrame {
    private Activity activity;
    private Fragment fragment;

    /**
     * 下一个Activity收不到上一个Activity中Intent携带的参数
     *
     * @see #IntentBuilder(Activity)
     */
    public IntentBuilder(Activity activity) {
        super();
        initActivity(activity);
    }

    /**
     * @param intent default=null
     *               first：putExtras(intent);
     *               then：putExtra("",xxxx);
     */
    public IntentBuilder(Activity activity, Intent intent) {
        super(intent);
        initActivity(activity);
    }

    private void initActivity(Activity activity) {
        this.activity = activity;
    }

    public IntentBuilder(Fragment fragment) {
        super();
        initFragment(fragment);
    }

    public IntentBuilder(Fragment fragment, Intent intent) {
        super(intent);
        initFragment(fragment);
    }

    private void initFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void startActivityByAct(@Nullable String className) {
        try {
            startActivityByAct(Class.forName(className));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startActivityByAct(@Nullable Class<?> tClass) {
        if (null == activity)
            throw new NullPointerException("the Context must not null");
        else if (null == tClass)
            throw new ActivityNotFoundException("No Activity found to handle Intent {  }");
        else {
            setClass(activity, tClass);
            activity.startActivity(this);
        }
    }

    @Override
    public void startActivityForResultByAct(@Nullable String className, int reqCode) {
        try {
            startActivityForResultByAct(Class.forName(className), reqCode);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startActivityForResultByAct(@Nullable Class<?> tClass, @IntRange(from = 2) int reqCode) {
        if (null == activity)
            throw new NullPointerException("the Context must not null");
        else if (null == tClass)
            throw new NullPointerException("No Activity found to handle Intent {  }");
        else {
            setClass(activity, tClass);
            activity.startActivityForResult(this, reqCode);
        }
    }

    @Override
    public void startServiceByAct(@Nullable String className) {
        try {
            startServiceByAct(Class.forName(className));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startServiceByAct(@Nullable Class<?> tClass) {
        if (null == tClass)
            throw new NullPointerException("No Activity found to handle Intent {  }");
        else {
            if (null == activity)
                throw new NullPointerException("the Context must not null");
            else {
                setClass(activity, tClass);
                activity.startService(this);
            }
        }
    }

    @Override
    public void startActivityByFrag(@Nullable String className) {
        try {
            startActivityByFrag(Class.forName(className));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startActivityByFrag(@Nullable Class<?> tClass) {
        if (null == fragment)
            throw new NullPointerException("The Fragment must not null");
        else if (null == tClass)
            throw new ActivityNotFoundException("No Activity found to handle Intent {  }");
        else {
            Context context = fragment.getContext();
            if (null == context)
                throw new NullPointerException("The fragment.getContext() must not null");
            else {
                setClass(context, tClass);
                fragment.startActivity(this);
            }
        }
    }

    @Override
    public void startActivityForResultByFrag(@Nullable String className, int reqCode) {
        try {
            startActivityForResultByFrag(Class.forName(className), reqCode);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startActivityForResultByFrag(@Nullable Class<?> tClass, int reqCode) {
        if (null == fragment)
            throw new NullPointerException("The Fragment must not null");
        else if (null == tClass)
            throw new NullPointerException("No Activity found to handle Intent {  }");
        else {
            Context context = fragment.getContext();
            if (null == context)
                throw new NullPointerException("The fragment.getContext() must not null");
            else {
                setClass(context, tClass);
                fragment.startActivityForResult(this, reqCode);
            }
        }
    }

    @Override
    public void startServiceByFrag(@Nullable String className) {
        try {
            startServiceByFrag(Class.forName(className));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startServiceByFrag(@Nullable Class<?> tClass) {
        if (null == fragment)
            throw new NullPointerException("The Fragment must not null");
        else if (null == tClass)
            throw new NullPointerException("No Activity found to handle Intent {  }");
        else {
            FragmentActivity fragAct = fragment.getActivity();
            if (null != fragAct) {
                setClass(fragAct, tClass);
                fragAct.startService(this);
            } else throw new NullPointerException("The fragment.getActivity() must not null");
        }
    }
}
