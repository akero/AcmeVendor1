package com.acme.acmevendor.activity.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.acme.acmevendor.R;
import com.acme.acmevendor.databinding.ActivityAddClientBinding;
import com.acme.acmevendor.utility.NetworkUtils;

public class AddClientActivity extends AppCompatActivity {

    private ActivityAddClientBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_client);
    }

    public void btnCloseClick(View view) {
        finish();
    }

    public void btnSaveClick(View view) {
        if (binding.etFullName.getText().toString().isEmpty() ||
                binding.etEmail.getText().toString().isEmpty() ||
                binding.etCompanyName.getText().toString().isEmpty() ||
                binding.etCompanyAddress.getText().toString().isEmpty() ||
                binding.etGst.getText().toString().isEmpty() ||
                binding.etPhone.getText().toString().isEmpty()) {

            Toast.makeText(this, "Fill all the fields", Toast.LENGTH_LONG).show();
        } else if (!NetworkUtils.isNetworkAvailable(this)) {
            Toast.makeText(this, "Check your Internet Connection and Try Again", Toast.LENGTH_LONG).show();
        } else {
            binding.etFullName.setText("");
            binding.etEmail.setText("");
            binding.etCompanyName.setText("");
            binding.etCompanyAddress.setText("");
            binding.etGst.setText("");
            binding.etPhone.setText("");
            showSuccessMessage();
        }
    }

    public void showSuccessMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.custom_emailsent, null);

        TextView tvMsg = view.findViewById(R.id.tvMsg);
        TextView tvResubmit = view.findViewById(R.id.tvResubmit);
        tvResubmit.setVisibility(View.GONE);
        tvMsg.setText("Client Added Successfully");

        Button btnClose = view.findViewById(R.id.btnClose);
        builder.setView(view);
        final AlertDialog dialog = builder.create();

        if (dialog != null) {
            dialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        }

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        if (dialog != null) {
            dialog.show();
        }
    }
}
