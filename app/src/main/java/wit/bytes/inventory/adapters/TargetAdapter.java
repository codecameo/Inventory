package wit.bytes.inventory.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

import wit.bytes.inventory.R;
import wit.bytes.inventory.models.Target;

/**
 * Created by Md. Sifat-Ul Haque on 2/18/2017.
 */

public class TargetAdapter extends RecyclerView.Adapter<TargetAdapter.ViewHolder> {

    private int mTargetColor, mAchieveColor;
    private ArrayList<Target> mTargets = new ArrayList<>();

    public TargetAdapter(int targetColor, int achieveColor){
        mTargetColor = targetColor;
        mAchieveColor = achieveColor;

        Target target = new Target();

        target.setTarget(500);
        target.setAchieve(260);
        mTargets.add(target);

        target = new Target();

        target.setTarget(100);
        target.setAchieve(60);
        mTargets.add(target);

        target = new Target();

        target.setTarget(150);
        target.setAchieve(70);
        mTargets.add(target);

        target = new Target();

        target.setTarget(200);
        target.setAchieve(160);
        mTargets.add(target);

        target = new Target();

        target.setTarget(110);
        target.setAchieve(65);
        mTargets.add(target);

        target = new Target();

        target.setTarget(400);
        target.setAchieve(260);
        mTargets.add(target);

        target = new Target();

        target.setTarget(400);
        target.setAchieve(460);
        mTargets.add(target);

    }

    @Override
    public TargetAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_target_list, parent, false));
    }

    @Override
    public void onBindViewHolder(TargetAdapter.ViewHolder holder, int position) {
        holder.bindTo();
    }

    @Override
    public int getItemCount() {
        return mTargets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final HorizontalBarChart mHorizontalBarChart;
        private BarDataSet mBarDataSet;
        private float mTarget = 0;
        private float mAchieve = 0;
        private BarData mBarData;
        private ArrayList<Integer> colors;

        public ViewHolder(View itemView) {
            super(itemView);
            mHorizontalBarChart = (HorizontalBarChart) itemView.findViewById(R.id.horizontal_bar);
            mHorizontalBarChart.setOnTouchListener(null);
            setupChart();
        }

        private void setupChart() {
            ArrayList<BarEntry> barEntries = new ArrayList<>();
            barEntries.add(new BarEntry(5,mTarget));
            barEntries.add(new BarEntry(0,mAchieve));
            mBarDataSet = new BarDataSet(barEntries,"");

            colors = new ArrayList<>();
            colors.add(mTargetColor);
            colors.add(mAchieveColor);

            mBarDataSet.setColors(colors);
            mBarDataSet.setValueTextSize(10);

            mBarData = new BarData(mBarDataSet);
            mBarData.setBarWidth(3.0f); // set custom bar width

            mHorizontalBarChart.getXAxis().setDrawGridLines(false);
            mHorizontalBarChart.getXAxis().setDrawAxisLine(false);

            mHorizontalBarChart.getAxisLeft().setDrawGridLines(false);
            mHorizontalBarChart.getAxisLeft().setAxisMinimum(0);
            mHorizontalBarChart.getAxisLeft().setDrawAxisLine(false);

            mHorizontalBarChart.getAxisRight().setAxisMinimum(0);
            mHorizontalBarChart.getAxisRight().setDrawGridLines(false);
            mHorizontalBarChart.getAxisRight().setDrawAxisLine(false);

            mHorizontalBarChart.getLegend().setEnabled(false);
            mHorizontalBarChart.getXAxis().setEnabled(false);
            mHorizontalBarChart.getAxisLeft().setEnabled(false);
            //mHorizontalBarChart.getAxisRight().setEnabled(false);

            mHorizontalBarChart.setDescription(null);
            mHorizontalBarChart.setFitBars(true); // make the x-axis fit exactly all bars

            mHorizontalBarChart.setData(mBarData);
            mHorizontalBarChart.invalidate(); // refresh

        }

        private void bindTo(){
            updateChart(mTargets.get(getAdapterPosition()));
        }

        private void updateChart(Target target) {

            if (Float.compare(target.getTarget(),mTarget) != 0 || Float.compare(target.getAchieve(), mAchieve)!= 0){

                mTarget = target.getTarget();
                mAchieve = target.getAchieve();

                ArrayList<BarEntry> barEntries = new ArrayList<>();
                barEntries.add(new BarEntry(5,mTarget));
                barEntries.add(new BarEntry(0,mAchieve));
                mBarDataSet = new BarDataSet(barEntries,"");

                mBarDataSet.setColors(colors);
                mBarDataSet.setValueTextSize(10);

                mBarData = new BarData(mBarDataSet);
                mBarData.setBarWidth(3.0f); // set custom bar width

                mHorizontalBarChart.setData(mBarData);
                mHorizontalBarChart.invalidate(); // refresh
            }

        }
    }
}
