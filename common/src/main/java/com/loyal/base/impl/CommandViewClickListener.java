package com.loyal.base.impl;

import android.view.View;

import com.loyal.base.widget.CommandDialog;

public interface CommandViewClickListener {
    void onViewClick(CommandDialog dialog, View view, Object tag);
}