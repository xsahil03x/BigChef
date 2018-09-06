package com.magarex.bigchef.ui.detail;

import com.magarex.bigchef.model.Recipe;
import com.magarex.bigchef.model.Step;

import java.util.ArrayList;
import java.util.List;

public interface StepItemClickListener {
        void onClick(ArrayList<Step> step,int position);
}
