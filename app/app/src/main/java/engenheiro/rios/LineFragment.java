package engenheiro.rios;
import android.animation.PropertyValuesHolder;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.db.chart.Tools;
import com.db.chart.model.LineSet;
import com.db.chart.view.AxisController;
import com.db.chart.view.ChartView;
import com.db.chart.view.LineChartView;
import com.db.chart.view.Tooltip;
import com.db.chart.view.animation.Animation;


public class LineFragment extends Fragment {





    /** Third chart */
    private LineChartView mChartThree;
    private boolean mUpdateThree;
    private final String[] mLabelsThree= {"1","2","3","4","5","6","IRR total"};
    private final float[][] mValuesThree = {  {1f,3f,5f,1f,3f,5f,4f}};



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.line, container, false);


        // Init third chart
        mUpdateThree = true;
        mChartThree = (LineChartView) layout.findViewById(R.id.linechart3);


        showChart(2, mChartThree);
        return layout;
    }


    /**
     * Show a CardView chart
     * @param tag   Tag specifying which chart should be dismissed
     * @param chart   Chart view
     */
    private void showChart(final int tag, final LineChartView chart){

        //dismissPlay(btn);
        Runnable action =  new Runnable() {
            @Override
            public void run() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        //showPlay(btn);
                    }
                }, 500);
            }
        };

        switch(tag){
            case 2:
                produceThree(chart); break;
            default:
        }
    }


    /**
     * Update the values of a CardView chart
     * @param tag   Tag specifying which chart should be dismissed
     * @param chart   Chart view
     * @param btn    Play button
     */
    private void updateChart(final int tag, final LineChartView chart, ImageButton btn){

        dismissPlay(btn);

        switch(tag){
            case 2:
                updateThree(chart); break;
            default:
        }
    }


    /**
     * Dismiss a CardView chart
     * @param tag   Tag specifying which chart should be dismissed
     * @param chart   Chart view
     * @param btn    Play button
     */
    private void dismissChart(final int tag, final LineChartView chart, final ImageButton btn){

        dismissPlay(btn);

        Runnable action =  new Runnable() {
            @Override
            public void run() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        showPlay(btn);
                        //showChart(tag, chart, btn);
                    }
                }, 500);
            }
        };

        switch(tag){
            case 2:
                dismissThree(chart, action); break;
            default:
        }
    }


    /**
     * Show CardView play button
     * @param btn    Play button
     */
    private void showPlay(ImageButton btn){

        btn.setEnabled(true);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            btn.animate().alpha(1).scaleX(1).scaleY(1);
        else
            btn.setVisibility(View.VISIBLE);
    }


    /**
     * Dismiss CardView play button
     * @param btn    Play button
     */
    private void dismissPlay(ImageButton btn){

        btn.setEnabled(false);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            btn.animate().alpha(0).scaleX(0).scaleY(0);
        else
            btn.setVisibility(View.INVISIBLE);
    }




    /**
     *
     * Chart 3
     *
     */

    public void produceThree(LineChartView chart){

        Tooltip tip = new Tooltip(getActivity(), R.layout.linechart_three_tooltip, R.id.value);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {

            tip.setEnterAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 1),
                    PropertyValuesHolder.ofFloat(View.SCALE_X, 1f),
                    PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f));

            tip.setExitAnimation(PropertyValuesHolder.ofFloat(View.ALPHA,0),
                    PropertyValuesHolder.ofFloat(View.SCALE_X,0f),
                    PropertyValuesHolder.ofFloat(View.SCALE_Y,0f));
        }

        chart.setTooltips(tip);

        LineSet dataset = new LineSet(mLabelsThree, mValuesThree[0]);
        dataset.setColor(Color.parseColor("#2196F3"))
                .setDotsStrokeThickness(Tools.fromDpToPx(2))
                .setDotsStrokeColor(Color.parseColor("#2196F3"))
                .setDotsColor(Color.parseColor("#eef1f6"));
        chart.addData(dataset);

        Paint gridPaint = new Paint();
        gridPaint.setColor(Color.parseColor("#308E9196"));
        gridPaint.setStyle(Paint.Style.STROKE);
        gridPaint.setAntiAlias(true);
        gridPaint.setStrokeWidth(Tools.fromDpToPx(1f));

        chart.setBorderSpacing(1)
                .setAxisBorderValues(1, 5, -1)
                .setXLabels(AxisController.LabelPosition.OUTSIDE)
                .setYLabels(AxisController.LabelPosition.OUTSIDE)
                .setLabelsColor(Color.parseColor("#FF8E9196"))
                .setXAxis(false)
                .setYAxis(false)
                .setBorderSpacing(Tools.fromDpToPx(5))
                .setGrid(ChartView.GridType.VERTICAL, gridPaint);

        //Animation anim = new Animation().setEndAction(action);

        chart.show();
    }

    public void updateThree(LineChartView chart){

        chart.dismissAllTooltips();
        chart.updateValues(0, mValuesThree[2]);
        chart.updateValues(1, mValuesThree[0]);
        chart.updateValues(2, mValuesThree[1]);
        chart.notifyDataUpdate();
    }

    public static void dismissThree(LineChartView chart, Runnable action){

        chart.dismissAllTooltips();
        chart.dismiss(new Animation().setEndAction(action));
    }

}