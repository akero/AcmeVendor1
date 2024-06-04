package com.acme.acmevendor.activity.dashboard;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.acme.acmevendor.databinding.ActivityRecceHistoryDetailsEditBinding;
import com.acme.acmevendor.R;

public class RecceHistoryDetailsEdit extends AppCompatActivity {

    ViewDataBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_recce_history_details_edit);

        



    }
}