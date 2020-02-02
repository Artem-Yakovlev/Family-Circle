package com.tydeya.familycircle.personviewpage;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.appbar.AppBarLayout;

public class TitleBehaviour extends CoordinatorLayout.Behavior<FrameLayout> {
    int counter = 0;
    public TitleBehaviour(Context context, AttributeSet attributeSet) {}

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull FrameLayout child, @NonNull View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull FrameLayout child, @NonNull View dependency) {
        return super.onDependentViewChanged(parent, child, dependency);
    }
}
