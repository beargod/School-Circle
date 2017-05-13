package com.example.administrator.school_circle.customUi;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.school_circle.R;
import com.example.administrator.school_circle.model.CourseTable;
import com.example.administrator.school_circle.model.LeftCell;
import com.example.administrator.school_circle.model.TopCell;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.zhouchaoyuan.excelpanel.BaseExcelPanelAdapter;

public class CourseTableAdapter extends BaseExcelPanelAdapter<TopCell, LeftCell, CourseTable> {

    private Context context;

    public CourseTableAdapter(Context context) {
        super(context);
        this.context=context;
    }

    //=========================================normal cell=========================================
    @Override
    public RecyclerView.ViewHolder onCreateCellViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_content_cell, parent, false);
        CellHolder cellHolder = new CellHolder(layout);
        return cellHolder;
    }

    @Override
    public void onBindCellViewHolder(RecyclerView.ViewHolder holder, int verticalPosition, int horizontalPosition) {
        final CourseTable course = getMajorItem(verticalPosition, horizontalPosition);
        if (null == holder || !(holder instanceof CellHolder) || course == null) {
            return;
        }
        CellHolder viewHolder = (CellHolder) holder;
        viewHolder.cellContainer.setTag(course);
        if(course!=null) {
            viewHolder.cellContainer.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new SweetAlertDialog(context)
                                    .setTitleText("")
                                    .setContentText(course.toString())
                                    .show();
                        }
                    }
            );
            viewHolder.courseName.setText(course.getName());
            viewHolder.classroom.setText(course.getClassroom());
        }else {
            viewHolder.courseName.setText("");
            viewHolder.classroom.setText("");
            viewHolder.cellContainer.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }
    }

    static class CellHolder extends RecyclerView.ViewHolder {

        public final TextView courseName;
        public final TextView classroom;
        public final LinearLayout cellContainer;

        public CellHolder(View itemView) {
            super(itemView);
            courseName = (TextView) itemView.findViewById(R.id.course_name);
            classroom = (TextView) itemView.findViewById(R.id.classroom);
            cellContainer = (LinearLayout) itemView;
        }
    }

    //=========================================top cell===========================================
    @Override
    public RecyclerView.ViewHolder onCreateTopViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_top_cell, parent, false);
        TopCellHolder cellHolder = new TopCellHolder(layout);
        return cellHolder;
    }

    @Override
    public void onBindTopViewHolder(RecyclerView.ViewHolder holder, int position) {
        TopCell topCell = getTopItem(position);
        if (null == holder || !(holder instanceof TopCellHolder) || topCell == null||topCell.getDate()==null) {

            return;
        }
        TopCellHolder viewHolder = (TopCellHolder) holder;
        viewHolder.weekday.setText(topCell.getWeekday());
        viewHolder.monthDay.setText(topCell.getDate());

    }

    static class TopCellHolder extends RecyclerView.ViewHolder {

        public TextView weekday;
        public TextView monthDay;
        public View bottomLine;

        public TopCellHolder(View itemView) {
            super(itemView);
            weekday = (TextView) itemView.findViewById(R.id.weekday);
            monthDay = (TextView) itemView.findViewById(R.id.month_day);
            bottomLine =  itemView.findViewById(R.id.bottom_line);
        }
    }

    //=========================================left cell===========================================
    @Override
    public RecyclerView.ViewHolder onCreateLeftViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_left_cell, parent, false);
        LeftCellHolder cellHolder = new LeftCellHolder(layout);
        return cellHolder;
    }

    @Override
    public void onBindLeftViewHolder(RecyclerView.ViewHolder holder, int position) {
        LeftCell leftCell = getLeftItem(position);
        if (null == holder || !(holder instanceof LeftCellHolder) || leftCell == null) {
            return;
        }
        LeftCellHolder viewHolder = (LeftCellHolder) holder;
        viewHolder.course.setText(leftCell.getCourse());
        viewHolder.time.setText(leftCell.getTime());
    }

    static class LeftCellHolder extends RecyclerView.ViewHolder {

        public final TextView course;
        public final TextView time;

        public LeftCellHolder(View itemView) {
            super(itemView);
            course = (TextView) itemView.findViewById(R.id.course);
            time = (TextView) itemView.findViewById(R.id.time);
        }
    }


    //=========================================top left cell=======================================
    @Override
    public View onCreateTopLeftView() {
        return LayoutInflater.from(context).inflate(R.layout.activity_content_cell, null);
    }
}