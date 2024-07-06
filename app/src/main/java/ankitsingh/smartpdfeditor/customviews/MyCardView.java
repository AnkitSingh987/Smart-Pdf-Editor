package ankitsingh.smartpdfeditor.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ankitsingh.smartpdfeditor.R;

public class MyCardView extends LinearLayout {
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
    ImageView icon;
    TextView text;

    /**
     * Initiates custom card view
     *
     * @param context - context
     */
    public MyCardView(@NonNull Context context) {
        super(context);
        init();
    }

    /**
     * Initiates custom card view with attributes
     *
     * @param context - context
     * @param attrs   - attributes set
     */
    public MyCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    /**
     * Initiates custom card view with attributes set
     *
     * @param context      - context
     * @param attrs        - attributes set
     * @param defStyleAttr - attribute style
     */
    public MyCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    /**
     * Initialize card view with no attribute sets
     */
    private void init() {
        inflate(getContext(), R.layout.item_view_enhancement_option, this);
        this.text = findViewById(R.id.option_name);
        this.icon = findViewById(R.id.option_image);
    }

    /**
     * Initialize card view with attributes received
     *
     * @param attrs - attribute set
     */
    private void init(AttributeSet attrs) {
        inflate(getContext(), R.layout.item_view_enhancement_option, this);

        TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.MyCardView);

        this.text = findViewById(R.id.option_name);
        this.icon = findViewById(R.id.option_image);

        this.text.setText(a.getString(R.styleable.MyCardView_option_text));
        Drawable drawable = a.getDrawable(R.styleable.MyCardView_option_icon);
        this.icon.setImageDrawable(drawable);

        a.recycle();
    }
}
