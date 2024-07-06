package ankitsingh.smartpdfeditor.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ankitsingh.smartpdfeditor.R;
import ankitsingh.smartpdfeditor.model.PreviewImageOptionItem;

public class PreviewImageOptionsAdapter extends RecyclerView.Adapter<PreviewImageOptionsAdapter.ViewHolder> {
    private final ArrayList<PreviewImageOptionItem> mOptions;
    private final Context mContext;
    private final OnItemClickListener mOnItemClickListener;
    /*
     * This file is part of MyApplication.
     *
     * MyApplication is free software: you can redistribute it and/or modify
     * it under the terms of the GNU General Public License as published by
     * the Free Software Foundation, either version 3 of the License, or
     * (at your option) any later version.
     *
     * MyApplication is distributed in the hope that it will be useful,
     * but WITHOUT ANY WARRANTY; without even the implied warranty of
     * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
     * GNU General Public License for more details.
     *
     * You should have received a copy of the GNU General Public License
     * along with MyApplication. If not, see <https://www.gnu.org/licenses/>.
     */
    public PreviewImageOptionsAdapter(OnItemClickListener onItemClickListener,
                                      ArrayList<PreviewImageOptionItem> optionItems, Context context) {
        mOnItemClickListener = onItemClickListener;
        mOptions = optionItems;
        mContext = context;
    }

    @NonNull
    @Override
    public PreviewImageOptionsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_preview_image_options,
                parent, false);
        return new PreviewImageOptionsAdapter.ViewHolder(view);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull PreviewImageOptionsAdapter.ViewHolder holder, int position) {
        int imageId = mOptions.get(position).getOptionImageId();
        holder.imageView.setImageDrawable(mContext.getDrawable(imageId));
        holder.textView.setText(mOptions.get(position).getOptionName());
    }

    @Override
    public int getItemCount() {
        return mOptions.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView imageView;
        final TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.option_image);
            textView = itemView.findViewById(R.id.option_name);
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mOnItemClickListener.onItemClick(getAdapterPosition());
        }
    }
}
