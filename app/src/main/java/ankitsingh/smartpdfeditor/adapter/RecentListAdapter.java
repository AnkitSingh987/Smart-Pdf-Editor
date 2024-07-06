package ankitsingh.smartpdfeditor.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import ankitsingh.smartpdfeditor.R;

public class RecentListAdapter extends RecyclerView.Adapter<RecentListAdapter.RecentItemViewHolder> {

    private final View.OnClickListener mOnClickListener;
    private List<String> mKeys;
    private List<Map<String, String>> mValues;

    public RecentListAdapter(View.OnClickListener listener) {
        this.mOnClickListener = listener;
    }
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

    /**
     * Updates the recent list
     *
     * @param keys       - list of all the feature viewId
     * @param recentList - list of the features
     */
    public void updateList(List<String> keys, List<Map<String, String>> recentList) {
        this.mKeys = keys;
        this.mValues = recentList;
    }

    @NonNull
    @Override
    public RecentItemViewHolder onCreateViewHolder(
            @NonNull final ViewGroup viewGroup, final int i) {
        View mView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_view_enhancement_option, viewGroup, false);
        return new RecentListAdapter.RecentItemViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(
            @NonNull final RecentItemViewHolder recentItemViewHolder, final int i) {

        recentItemViewHolder.name.setText(recentItemViewHolder.itemView.getContext().getString(
                Integer.parseInt(mValues.get(i).keySet().iterator().next())
        ));
        recentItemViewHolder.icon.setImageDrawable(ContextCompat.getDrawable(recentItemViewHolder.itemView.getContext(),
                Integer.parseInt(mValues.get(i).values().iterator().next())));

        recentItemViewHolder.itemView.setId(Integer.parseInt(mKeys.get(i)));
        recentItemViewHolder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class RecentItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.option_image)
        ImageView icon;
        @BindView(R.id.option_name)
        TextView name;

        MaterialCardView cardView;

        private RecentItemViewHolder(@NonNull final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            cardView = itemView.findViewById(R.id.container_card_view);
        }
    }
}
