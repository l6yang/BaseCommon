package com.loyal.base.impl;

import android.support.annotation.IntRange;

public interface OnSinglePermissionListener {
    void onSinglePermission(@IntRange(from = 2, to = 1000) int reqCode, boolean successful);
}