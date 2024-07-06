package ankitsingh.smartpdfeditor.fragment.texttopdf;

import android.app.Activity;

import ankitsingh.smartpdfeditor.interfaces.Enhancer;
import ankitsingh.smartpdfeditor.model.TextToPDFOptions;

/**
 * The {@link Enhancers} represent a list of enhancers for the Text-to-PDF feature.
 */
public enum Enhancers {
    FONT_COLOR {
        @Override
        Enhancer getEnhancer(Activity activity, TextToPdfContract.View view,
                             TextToPDFOptions.Builder builder) {
            return new FontColorEnhancer(activity, builder);
        }
    },
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
    FONT_FAMILY {
        @Override
        Enhancer getEnhancer(Activity activity, TextToPdfContract.View view,
                             TextToPDFOptions.Builder builder) {
            return new FontFamilyEnhancer(activity, view, builder);
        }
    },
    FONT_SIZE {
        @Override
        Enhancer getEnhancer(Activity activity, TextToPdfContract.View view,
                             TextToPDFOptions.Builder builder) {
            return new FontSizeEnhancer(activity, view, builder);
        }
    },
    PAGE_COLOR {
        @Override
        Enhancer getEnhancer(Activity activity, TextToPdfContract.View view,
                             TextToPDFOptions.Builder builder) {
            return new PageColorEnhancer(activity, builder);
        }
    },
    PAGE_SIZE {
        @Override
        Enhancer getEnhancer(Activity activity, TextToPdfContract.View view,
                             TextToPDFOptions.Builder builder) {
            return new PageSizeEnhancer(activity);
        }
    },
    PASSWORD {
        @Override
        Enhancer getEnhancer(Activity activity, TextToPdfContract.View view,
                             TextToPDFOptions.Builder builder) {
            return new PasswordEnhancer(activity, view, builder);
        }
    };

    /**
     * @param activity The {@link Activity} context.
     * @param view     The {@link TextToPdfContract.View} that needs the enhancement.
     * @param builder  The builder for {@link TextToPDFOptions}.
     * @return An instance of the {@link Enhancer}.
     */
    abstract Enhancer getEnhancer(Activity activity, TextToPdfContract.View view,
                                  TextToPDFOptions.Builder builder);
}
