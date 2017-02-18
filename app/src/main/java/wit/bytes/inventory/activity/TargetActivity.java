package wit.bytes.inventory.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

import wit.bytes.inventory.R;
import wit.bytes.inventory.adapters.TargetAdapter;

/**
 * Created by Md. Sifat-Ul Haque on 2/17/2017.
 */

public class TargetActivity extends BaseActivity {

    private PieChart mPieChart;
    private RecyclerView mRvTargetList;
    private HorizontalBarChart mHorizontalBarChart;
    private int mTarget = 100, mTargetColor, mAchieve = 60, mAchieveColor;
    private TargetAdapter mTargetAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);
        initViews();
        initVariable();
        setupToolBar(R.id.toolbar);
        setAdapter();
    }

    private void setAdapter() {
        mRvTargetList.setAdapter(mTargetAdapter);
        mRvTargetList.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initVariable() {
        mTargetColor = ContextCompat.getColor(this,R.color.targetColor);
        mAchieveColor = ContextCompat.getColor(this,R.color.achieveColor);
        mTargetAdapter = new TargetAdapter(mTargetColor,mAchieveColor);
    }



    private void initViews() {
        mRvTargetList = (RecyclerView) findViewById(R.id.rv_target_list);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();

        if (id == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
