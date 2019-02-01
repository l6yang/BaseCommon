package com.loyal.base.impl;

import android.content.DialogInterface;
import android.view.View;

import com.loyal.base.widget.CommandDialog;

public interface CommandViewClickListener {
    void onViewClick(DialogInterface dialog, View view, Object tag);
}