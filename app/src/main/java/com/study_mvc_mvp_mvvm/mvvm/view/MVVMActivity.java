package com.study_mvc_mvp_mvvm.mvvm.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.study_mvc_mvp_mvvm.R;
import com.study_mvc_mvp_mvvm.databinding.MvvmBinding;
import com.study_mvc_mvp_mvvm.mvvm.viewmodel.MVVMViewModel;


public class MVVMActivity extends AppCompatActivity {

    MVVMViewModel viewModel = new MVVMViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MvvmBinding binding = DataBindingUtil.setContentView(this, R.layout.mvvm);
        binding.setViewModel(viewModel);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_mvx, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_reset:
                viewModel.onResetSelected();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
