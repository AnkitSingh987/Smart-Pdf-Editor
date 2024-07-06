package ankitsingh.smartpdfeditor.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ankitsingh.smartpdfeditor.R;
import ankitsingh.smartpdfeditor.model.WhatsNew;

public class WhatsNewAdapter extends RecyclerView.Adapter<WhatsNewAdapter.WhatsNewViewHolder> {

    private final Context mContext;
    private final List<WhatsNew> mWhatsNewsList;
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
    public WhatsNewAdapter(Context context, ArrayList<WhatsNew> mWhatsNewsList) {
        this.mContext = context;
        this.mWhatsNewsList = mWhatsNewsList;
    }

    @NonNull
    @Override
    public WhatsNewViewHolder onCreateViewHolder(@NonNull ViewGroup mParent, int viewType) {
        View mView = LayoutInflater.from(mParent.getContext())
                .inflate(R.layout.item_whats_new, mParent, false);
        return new WhatsNewAdapter.WhatsNewViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull WhatsNewViewHolder holder, int position) {
        holder.tvDescription.setText(mWhatsNewsList.get(position).getDescription());
        holder.tvHeading.setText(mWhatsNewsList.get(position).getTitle());
        if (!mWhatsNewsList.get(position).getIcon().equals("")) {
            Resources resources = mContext.getResources();
            final int resourceId = resources.getIdentifier(mWhatsNewsList.get(position).getIcon(),
                    "drawable", mContext.getPackageName());
            holder.icon.setBackgroundResource(resourceId);
        }
    }

    @Override
    public int getItemCount() {
        return mWhatsNewsList.size();
    }

    class WhatsNewViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView tvHeading;
        @BindView(R.id.description)
        TextView tvDescription;
        @BindView(R.id.icon)
        ImageView icon;

        WhatsNewViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
