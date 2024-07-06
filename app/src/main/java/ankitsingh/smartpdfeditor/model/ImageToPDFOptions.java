package ankitsingh.smartpdfeditor.model;

import java.util.ArrayList;

public class ImageToPDFOptions extends PDFOptions {
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
    private String mQualityString;
    private ArrayList<String> mImagesUri;
    private int mMarginTop = 0;
    private int mMarginBottom = 0;
    private int mMarginRight = 0;
    private int mMarginLeft = 0;
    private String mImageScaleType;
    private String mPageNumStyle;
    private String mMasterPwd;

    public ImageToPDFOptions() {
        super();
        setPasswordProtected(false);
        setWatermarkAdded(false);
        setBorderWidth(0);
    }

    public ImageToPDFOptions(String mFileName, String mPageSize, boolean mPasswordProtected,
                             String mPassword, String mQualityString, int mBorderWidth,
                             String masterPwd, ArrayList<String> mImagesUri,
                             boolean mWatermarkAdded, Watermark mWatermark, int pageColor) {
        super(mFileName, mPageSize, mPasswordProtected, mPassword, mBorderWidth, mWatermarkAdded, mWatermark,
                pageColor);
        this.mQualityString = mQualityString;
        this.mImagesUri = mImagesUri;
        this.mMasterPwd = masterPwd;
    }

    public String getQualityString() {
        return mQualityString;
    }

    public void setQualityString(String mQualityString) {
        this.mQualityString = mQualityString;
    }

    public ArrayList<String> getImagesUri() {
        return mImagesUri;
    }

    public void setImagesUri(ArrayList<String> mImagesUri) {
        this.mImagesUri = mImagesUri;
    }

    public void setMargins(int top, int bottom, int right, int left) {
        mMarginTop = top;
        mMarginBottom = bottom;
        mMarginRight = right;
        mMarginLeft = left;
    }

    public int getMarginTop() {
        return mMarginTop;
    }

    public int getMarginBottom() {
        return mMarginBottom;
    }

    public int getMarginRight() {
        return mMarginRight;
    }

    public int getMarginLeft() {
        return mMarginLeft;
    }

    public String getImageScaleType() {
        return mImageScaleType;
    }

    public void setImageScaleType(String mImageScaleType) {
        this.mImageScaleType = mImageScaleType;
    }

    public String getPageNumStyle() {
        return mPageNumStyle;
    }

    public void setPageNumStyle(String mPageNumStyle) {
        this.mPageNumStyle = mPageNumStyle;
    }

    public String getMasterPwd() {
        return mMasterPwd;
    }

    public void setMasterPwd(String pwd) {
        this.mMasterPwd = pwd;
    }
}
