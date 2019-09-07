package com.example.sampletest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sampletest.R;
import com.example.sampletest.model.Row;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ketan on 06,September,2019
 */
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.CustomViewHolder> {
    private final List<Row> mRowList;
    private final Context mContext;

    /**
     * @param mContext
     * @param mRowList
     */
    public DataAdapter(Context mContext, List<Row> mRowList) {
        this.mContext = mContext;
        this.mRowList = mRowList;
    }

    /**
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row_data, null);
        return new CustomViewHolder(itemLayoutView);
    }

    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        Row rowData = mRowList.get(position);

        //here set data from model

        if (rowData.getTitle() != null && !rowData.getTitle().isEmpty()) {
            holder.txtTitleView.setText(rowData.getTitle());
        } else {
            holder.txtTitleView.setText("No Title");
        }

        if (rowData.getDescription() != null && !rowData.getDescription().isEmpty()) {
            holder.txtDescriptionView.setText(rowData.getDescription());
        } else {
            holder.txtDescriptionView.setText("No Description");
        }

        /**
         * here set image form url. if url is empty or null then default image display from local for now
         */

        if (rowData.getImageHref() != null && !rowData.getImageHref().isEmpty()) {
            Picasso.get().load(rowData.getImageHref())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .fit()
                    .into(holder.imgHrefDisplayView);
        } else {
            holder.imgHrefDisplayView.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.ic_launcher));
        }
    }

    /**
     * @return here return the list
     */
    @Override
    public int getItemCount() {
        return mRowList.size();
    }

    /**
     * here initialize different views
     */
    public class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitleView, txtDescriptionView;
        ImageView imgHrefDisplayView;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitleView = (TextView) itemView.findViewById(R.id.txt_title);
            txtDescriptionView = (TextView) itemView.findViewById(R.id.txt_description);
            imgHrefDisplayView = (ImageView) itemView.findViewById(R.id.img_href_display);

        }
    }
}
