package ankitsingh.smartpdfeditor.activity;
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

import static ankitsingh.smartpdfeditor.util.Constants.IS_WELCOME_ACTIVITY_SHOWN;
import static ankitsingh.smartpdfeditor.util.Constants.LAUNCH_COUNT;
import static ankitsingh.smartpdfeditor.util.Constants.THEME_BLACK;
import static ankitsingh.smartpdfeditor.util.Constants.THEME_DARK;
import static ankitsingh.smartpdfeditor.util.Constants.THEME_SYSTEM;
import static ankitsingh.smartpdfeditor.util.Constants.THEME_WHITE;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import ankitsingh.smartpdfeditor.R;
import ankitsingh.smartpdfeditor.fragment.ImageToPdfFragment;
import ankitsingh.smartpdfeditor.interfaces.DialogCallbacks;
import ankitsingh.smartpdfeditor.providers.fragmentmanagement.FragmentManagement;
import ankitsingh.smartpdfeditor.util.Constants;
import ankitsingh.smartpdfeditor.util.DialogUtils;
import ankitsingh.smartpdfeditor.util.DirectoryUtils;
import ankitsingh.smartpdfeditor.util.FeedbackUtils;
import ankitsingh.smartpdfeditor.util.PermissionsUtils;
import ankitsingh.smartpdfeditor.util.ThemeUtils;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String WRITE_STORAGE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private FeedbackUtils mFeedbackUtils;
    private NavigationView mNavigationView;
    private SharedPreferences mSharedPreferences;
    private SparseIntArray mFragmentSelectedMap;
    private FragmentManagement mFragmentManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeUtils.getInstance().setThemeApp(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mNavigationView = findViewById(R.id.nav_view);

        setThemeOnActivityExclusiveComponents();

        Toolbar toolbar = findViewById(R.id.toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.app_name, R.string.app_name);

        //Replaced setDrawerListener with addDrawerListener because it was deprecated.
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        initializeValues();

        setXMLParsers();
        Fragment fragment = mFragmentManagement.checkForAppShortcutClicked();

        handleReceivedImagesIntent(fragment);

        displayFeedback();

        openWelcomeActivity();
    }

    private void setXMLParsers() {
        System.setProperty("org.apache.poi.javax.xml.stream.XMLInputFactory",
                "com.fasterxml.aalto.stax.InputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLOutputFactory",
                "com.fasterxml.aalto.stax.OutputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLEventFactory",
                "com.fasterxml.aalto.stax.EventFactoryImpl");
    }

    private void displayFeedback() {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int count = mSharedPreferences.getInt(LAUNCH_COUNT, 0);
        if (count > 0 && count % 15 == 0) {
            mFeedbackUtils.rateUs();
        }
        if (count != -1) {
            mSharedPreferences.edit().putInt(LAUNCH_COUNT, count + 1).apply();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (PermissionsUtils.getInstance().isStoragePermissionGranted(this)) {
            DirectoryUtils.makeAndClearTemp();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        if (actionBar != null)
            actionBar.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favourites, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_favourites_item) {
            setTitle(R.string.favourites);
            mFragmentManagement.favouritesFragmentOption();
        }
        return super.onOptionsItemSelected(item);
    }

    private void openWelcomeActivity() {
        if (!mSharedPreferences.getBoolean(IS_WELCOME_ACTIVITY_SHOWN, false)) {
            Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
            mSharedPreferences.edit().putBoolean(IS_WELCOME_ACTIVITY_SHOWN, true).apply();
            startActivity(intent);
        }
    }

    private void initializeValues() {
        mFeedbackUtils = new FeedbackUtils(this);
        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.setCheckedItem(R.id.nav_home);

        mFragmentManagement = new FragmentManagement(this, mNavigationView);
        setTitleMap();
    }

    private void handleReceivedImagesIntent(Fragment fragment) {
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (type == null || !type.startsWith("image/"))
            return;

        if (Intent.ACTION_SEND_MULTIPLE.equals(action)) {
            handleSendMultipleImages(intent, fragment); // Handle multiple images
        } else if (Intent.ACTION_SEND.equals(action)) {
            handleSendImage(intent, fragment); // Handle single image
        }
    }

    private void handleSendImage(Intent intent, Fragment fragment) {
        Uri uri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
        ArrayList<Uri> imageUris = new ArrayList<>();
        imageUris.add(uri);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(getString(R.string.bundleKey), imageUris);
        fragment.setArguments(bundle);
    }

    private void handleSendMultipleImages(Intent intent, Fragment fragment) {
        ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
        if (imageUris != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(getString(R.string.bundleKey), imageUris);
            fragment.setArguments(bundle);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            boolean shouldExit = mFragmentManagement.handleBackPressed();
            if (shouldExit)
                super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        setTitleFragment(mFragmentSelectedMap.get(item.getItemId()));
        return mFragmentManagement.handleNavigationItemSelected(item.getItemId());
    }

    public void setNavigationViewSelection(int id) {
        mNavigationView.setCheckedItem(id);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!PermissionsUtils.getInstance().isStoragePermissionGranted(this)) {
            if (Build.VERSION.SDK_INT >= 30) { // Above Android 11
                requestStoragePermission_API30AndAbove(false);
            } else { // Below Android 11
                mPermissionLauncher.launch(WRITE_STORAGE_PERMISSION);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private void openOSSettingsForPermissionRequest_API30AndAbove() {
        startActivity(
                new Intent()
                        .setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                        .setData(
                                Uri.fromParts(
                                        "package",
                                        MainActivity.this.getPackageName(),
                                        null)
                        )
        );
    }

    private void openOSSettingsForPermissionRequest_BelowAPI30() {
        mPermissionFromSettingLauncher.launch(
                new Intent()
                        .setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        .setData(Uri.fromParts("package", MainActivity.this.getPackageName(), null))
        );
    }

    private void requestStoragePermission_API30AndAbove(boolean giveExplanation) {
        DialogUtils.showChoiceDialog(
                this,
                giveExplanation ?
                        R.string.manage_storage_permission_denied_alert_dialog_title :
                        R.string.manage_storage_permission_alert_dialog_title,
                giveExplanation ?
                        R.string.manage_storage_permission_denied_alert_dialog_message :
                        DialogUtils.EMPTY_STRING,
                giveExplanation ?
                        R.string.manage_storage_permission_denied_alert_dialog_positive_button_label :
                        R.string.manage_storage_permission_alert_dialog_positive_button_label,
                giveExplanation ?
                        R.string.manage_storage_permission_denied_alert_dialog_negative_button_label :
                        R.string.manage_storage_permission_alert_dialog_negative_button_label,
                false,
                new DialogCallbacks() {
                    @RequiresApi(api = Build.VERSION_CODES.R)
                    public void onPositiveButtonClick() {
                        // On Allow button clicked
                        // On Re-try button clicked
                        openOSSettingsForPermissionRequest_API30AndAbove();
                    }

                    @RequiresApi(api = Build.VERSION_CODES.R)
                    public void onNegativeButtonClick() {
                        // On I'm sure button clicked
                        if (giveExplanation) finish();
                            // On Deny button clicked
                        else requestStoragePermission_API30AndAbove(true);
                    }

                    public void onNeutralButtonClick() {
                    }
                });
    }

    private void explainPermissionRequestAfterDenial_BelowAPI30() {
        boolean neverAskAgainChecked = !shouldShowRequestPermissionRationale(WRITE_STORAGE_PERMISSION);
        DialogUtils.showChoiceDialog(
                MainActivity.this,
                R.string.manage_storage_permission_denied_alert_dialog_title,
                R.string.manage_storage_permission_denied_alert_dialog_message,
                neverAskAgainChecked ?
                        R.string.manage_storage_permission_denied_with_never_ask_alert_dialog_positive_button_label :
                        R.string.manage_storage_permission_denied_alert_dialog_positive_button_label,
                R.string.manage_storage_permission_denied_alert_dialog_negative_button_label,
                false,
                new DialogCallbacks() {
                    public void onPositiveButtonClick() {
                        // On Allow from setting button clicked
                        if (neverAskAgainChecked) openOSSettingsForPermissionRequest_BelowAPI30();
                            // On Re-try button clicked
                    }

                    public void onNegativeButtonClick() {
                        // On I'm sure button clicked
                        finish();
                    }

                    public void onNeutralButtonClick() {
                    }
                });

    }

    private final ActivityResultLauncher<String> mPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(),
                    isGranted -> {
                        if (!isGranted) explainPermissionRequestAfterDenial_BelowAPI30();
                    });

    private final ActivityResultLauncher<Intent> mPermissionFromSettingLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            });


    public void convertImagesToPdf(ArrayList<Uri> imageUris) {
        Fragment fragment = new ImageToPdfFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(getString(R.string.bundleKey), imageUris);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
    }

    private void setTitleMap() {
        mFragmentSelectedMap = new SparseIntArray();
        mFragmentSelectedMap.append(R.id.nav_home, R.string.app_name);
        mFragmentSelectedMap.append(R.id.nav_camera, R.string.images_to_pdf);
        mFragmentSelectedMap.append(R.id.nav_qrcode, R.string.qr_barcode_pdf);
        mFragmentSelectedMap.append(R.id.nav_add_text, R.string.add_text);
        mFragmentSelectedMap.append(R.id.nav_gallery, R.string.viewFiles);
        mFragmentSelectedMap.append(R.id.nav_merge, R.string.merge_pdf);
        mFragmentSelectedMap.append(R.id.nav_split, R.string.split_pdf);
        mFragmentSelectedMap.append(R.id.nav_text_to_pdf, R.string.text_to_pdf);
        mFragmentSelectedMap.append(R.id.nav_history, R.string.history);
        mFragmentSelectedMap.append(R.id.nav_add_password, R.string.add_password);
        mFragmentSelectedMap.append(R.id.nav_remove_password, R.string.remove_password);
        mFragmentSelectedMap.append(R.id.nav_about, R.string.about_us);
        mFragmentSelectedMap.append(R.id.nav_settings, R.string.settings);
        mFragmentSelectedMap.append(R.id.nav_extract_images, R.string.extract_images);
        mFragmentSelectedMap.append(R.id.nav_pdf_to_images, R.string.pdf_to_images);
        mFragmentSelectedMap.append(R.id.nav_remove_pages, R.string.remove_pages);
        mFragmentSelectedMap.append(R.id.nav_rearrange_pages, R.string.reorder_pages);
        mFragmentSelectedMap.append(R.id.nav_compress_pdf, R.string.compress_pdf);
        mFragmentSelectedMap.append(R.id.nav_add_images, R.string.add_images);
        mFragmentSelectedMap.append(R.id.nav_remove_duplicate_pages, R.string.remove_duplicate_pages);
        mFragmentSelectedMap.append(R.id.nav_invert_pdf, R.string.invert_pdf);
        mFragmentSelectedMap.append(R.id.nav_add_watermark, R.string.add_watermark);
        mFragmentSelectedMap.append(R.id.nav_zip_to_pdf, R.string.zip_to_pdf);
        mFragmentSelectedMap.append(R.id.nav_rotate_pages, R.string.rotate_pages);
        mFragmentSelectedMap.append(R.id.nav_excel_to_pdf, R.string.excel_to_pdf);
        mFragmentSelectedMap.append(R.id.nav_faq, R.string.faqs);
    }

    private void setTitleFragment(int title) {
        if (title != 0)
            setTitle(title);
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

    private void setThemeOnActivityExclusiveComponents() {
        RelativeLayout toolbarBackgroundLayout = findViewById(R.id.toolbar_background_layout);
        MaterialCardView content = findViewById(R.id.content);
        SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String themeName = mSharedPreferences.getString(Constants.DEFAULT_THEME_TEXT,
                Constants.DEFAULT_THEME);
        switch (themeName) {
            case THEME_WHITE:
                toolbarBackgroundLayout.setBackgroundResource(R.drawable.toolbar_bg);
                content.setCardBackgroundColor(getResources().getColor(R.color.lighter_gray));
                mNavigationView.setBackgroundResource(R.color.white);
                break;
            case THEME_BLACK:
                toolbarBackgroundLayout.setBackgroundResource(R.color.black);
                content.setCardBackgroundColor(getResources().getColor(R.color.black));
                mNavigationView.setBackgroundResource(R.color.black);
                mNavigationView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                mNavigationView.setItemIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                mNavigationView.setItemBackgroundResource(R.drawable.navigation_item_selected_bg_selector_dark);
                break;
            case THEME_DARK:
                toolbarBackgroundLayout.setBackgroundResource(R.color.colorBlackAltLight);
                content.setCardBackgroundColor(getResources().getColor(R.color.colorBlackAlt));
                mNavigationView.setBackgroundResource(R.color.colorBlackAlt);
                mNavigationView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                mNavigationView.setItemIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                mNavigationView.setItemBackgroundResource(R.drawable.navigation_item_selected_bg_selector_dark);
                break;
            case THEME_SYSTEM:
            default:
                if ((this.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES) {
                    toolbarBackgroundLayout.setBackgroundResource(R.color.colorBlackAltLight);
                    content.setCardBackgroundColor(getResources().getColor(R.color.colorBlackAlt));
                    mNavigationView.setBackgroundResource(R.color.colorBlackAlt);
                    mNavigationView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                    mNavigationView.setItemIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                    mNavigationView.setItemBackgroundResource(R.drawable.navigation_item_selected_bg_selector_dark);
                } else {
                    toolbarBackgroundLayout.setBackgroundResource(R.drawable.toolbar_bg);
                    content.setCardBackgroundColor(getResources().getColor(R.color.lighter_gray));
                    mNavigationView.setBackgroundResource(R.color.white);
                }
        }
    }
}
