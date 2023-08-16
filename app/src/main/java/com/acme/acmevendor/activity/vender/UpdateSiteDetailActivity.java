package com.acme.acmevendor.activity.vender;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import androidx.databinding.DataBindingUtil;
import com.acme.acmevendor.R;
import com.acme.acmevendor.databinding.ActivityUpdateSiteDetailBinding;

public class UpdateSiteDetailActivity extends AppCompatActivity {

    private ActivityUpdateSiteDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_site_detail);
    }

    public void btnCloseClick(View view) {
        finish();
    }

    public void btnSaveClick(View view) {
        // finish();
    }
}
