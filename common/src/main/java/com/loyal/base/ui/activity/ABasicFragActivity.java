package com.loyal.base.ui.activity;

import android.content.Context;

import com.loyal.base.impl.IFrag2ActListener;
import com.loyal.base.ui.fragment.ABasicFragment;

/**
 * {@link ABasicFragment#onAttach(Context)}
 */
public abstract class ABasicFragActivity extends ABasicPerMissionActivity implements IFrag2ActListener {

    @Override
    public void onFrag2Act(String tag, Object... objectParams) {
       /* if (TextUtils.equals("show", tag)) {
            Object object = objectParam[0];
            if (object instanceof CharSequence) {
                show((CharSequence) object);
            } else if (object instanceof Integer) {
                show((Integer) object);
            }
        }*/
    }
}
