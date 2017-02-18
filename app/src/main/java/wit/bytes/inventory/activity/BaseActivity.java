package wit.bytes.inventory.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import wit.bytes.inventory.R;

/**
 * Created by Md. Sifat-Ul Haque on 2/17/2017.
 */

public class BaseActivity extends AppCompatActivity {

    protected Toolbar mToolbar;
    protected ActionBar actionBar;

    protected void setupToolBar(int toolbarResId) {
        if (mToolbar == null) {
            mToolbar = (Toolbar) findViewById(toolbarResId);
        }

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }

        actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white);
        }
    }
}
