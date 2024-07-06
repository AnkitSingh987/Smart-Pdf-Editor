package ankitsingh.smartpdfeditor.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ankitsingh.smartpdfeditor.R;
import ankitsingh.smartpdfeditor.util.FeedbackUtils;

public class AboutUsFragment extends Fragment {

    private Activity mActivity;
    private FeedbackUtils mFeedbackUtils;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            mActivity = (Activity) context;
        } else {
            throw new RuntimeException(context.toString() + " must be an instance of Activity");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about_us, container, false);
        ButterKnife.bind(this, rootView);

        // Set the version text
        try {
            PackageInfo packageInfo = mActivity.getPackageManager().getPackageInfo(mActivity.getPackageName(), 0);
            TextView versionText = rootView.findViewById(R.id.version_value);
            String version = getString(R.string.version_text) + " " + packageInfo.versionName;
            versionText.setText(version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        mFeedbackUtils = new FeedbackUtils(mActivity);
        return rootView;
    }

    @OnClick(R.id.layout_email)
    public void sendMail() {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "developerankitsingh09@gmail.com", null));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.feedback_subject));
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.feedback_text));
        startActivity(Intent.createChooser(intent, "Send Email"));
    }

    @OnClick(R.id.layout_website)
    void openWeb() {
        openWebPage("https://www.facebook.com/Ankitkumarravi.r");
    }

    @OnClick(R.id.layout_slack)
    void joinSlack() {
        openWebPage("https://play.google.com/store/apps/dev?id=5292833580796257820");
    }

    @OnClick(R.id.layout_github)
    void githubRepo() {
        openWebPage("https://github.com/AnkitSingh987/Smart-Pdf-Editor");
    }
    @OnClick(R.id.layout_contri)
    void contributorsList() {
        openWebPage("https://github.com/AnkitSingh987/Smart-Pdf-Editor/graphs/contributors");
    }

    @OnClick(R.id.layout_playstore)
    void openPlaystore() {
        openWebPage("https://play.google.com/store/apps/details?id=ankitsingh.smartpdfeditor");
    }

    @OnClick(R.id.layout_privacy)
    void privacyPolicy() {
        openWebPage("https://sites.google.com/view/smartpdfeditor/home");
    }

    @OnClick(R.id.layout_license)
    void license() {
        openWebPage("https://github.com/AnkitSingh987/Smart-Pdf-Editor/blob/my-new-branch/LICENSE.md");
    }

    @OnClick(R.id.layout_youtube)
    void openYoutube() {
        openWebPage("https://youtube.com/shorts/xhje6aWtxXI?si=lh9qmt3HP3AZmF6e");
    }

    @OnClick(R.id.layout_instGRm)
    void openMoreApps() {
        openWebPage("https://www.instagram.com/ankitkumar.ravi/");
    }

    private void openWebPage(String url) {
        if (mActivity == null) {
            Toast.makeText(getContext(), "Activity is not attached", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(mActivity, "Unable to open web page", Toast.LENGTH_SHORT).show();
        }
    }
}
