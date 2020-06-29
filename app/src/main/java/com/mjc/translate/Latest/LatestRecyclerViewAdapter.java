package com.mjc.translate.Latest;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.mjc.translate.DBHelper;
import com.mjc.translate.R;

import java.util.ArrayList;


public class LatestRecyclerViewAdapter extends RecyclerView.Adapter<LatestRecyclerViewAdapter.ItemViewHolder> {

    private View view;
    private ArrayList<LatestRecyclerViewItem> itemList = new ArrayList<LatestRecyclerViewItem>();
    private Context context;

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item_latest, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int position) {
        itemViewHolder.onBind(itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public long getItemId(int position) {
        return itemList.get(position).getInput().hashCode();
    }

    // 받을 데이터 추가
    public void addItem(LatestRecyclerViewItem item) {

        itemList.add(item);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView input, output, language, toLanguage;
        private ViewGroup layout;
        private DBHelper dbHelper;
        private SQLiteDatabase db;
        private Intent intent;

        ItemViewHolder(View itemView) {
            super(itemView);
            input = itemView.findViewById(R.id.latest_item_input);
            output = itemView.findViewById(R.id.latest_item_output);
            language = itemView.findViewById(R.id.latest_item_language);
            toLanguage = itemView.findViewById(R.id.latest_item_toLanguage);
            layout = itemView.findViewById(R.id.latest_recyclerview_layout);
        }

        void onBind(final LatestRecyclerViewItem item) {
            input.setText(item.getInput());
            output.setText(item.getOutput());
            language.setText(item.getLanguage());
            toLanguage.setText(item.getToLanguage());

            layout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    alertDialog(context, item);
                    return false;
                }
            });
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String[] values = {
                            input.getText().toString(),
                            output.getText().toString(),
                            language.getText().toString(),
                            toLanguage.getText().toString()
                    };
                    intent = new Intent(context, LatestTransSelectActivity.class);
                    intent.putExtra("values", values);
                    context.startActivity(intent);
                    ((LatestTransActivity) context).overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
            });

        }

        void alertDialog(final Context context, final LatestRecyclerViewItem item) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("알림");
            builder.setMessage("해당 기록을 삭제하시겠습니까?");
            builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dbHelper = new DBHelper(context);
                    db = dbHelper.getWritableDatabase();
                    db.execSQL("DELETE FROM latestTrans WHERE id = '" + item.getId() + "';");
                    ((LatestTransActivity) context).onResume();
                    Toast.makeText(context, "삭제되었습니다!", Toast.LENGTH_LONG).show();
                }
            });
            builder.setNegativeButton("취소", null);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
}

