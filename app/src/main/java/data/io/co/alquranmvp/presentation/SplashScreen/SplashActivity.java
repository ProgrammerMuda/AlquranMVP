package data.io.co.alquranmvp.presentation.SplashScreen;

import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import butterknife.BindView;
import data.io.co.alquranmvp.R;
import data.io.co.alquranmvp.Utils.PreferenceApp;
import data.io.co.alquranmvp.base.BaseActivity;
import data.io.co.alquranmvp.presentation.listSurah.ListSurahActivity;

public class SplashActivity extends BaseActivity<SplashscreenPresenter> implements SplashscreenView {
    @BindView(R.id.layoutLoadingInfo)
    LinearLayout mLayoutLoadingInfo;

    @BindView(R.id.progressInfo)
    ProgressBar mProgressInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (PreferenceApp.isFirstLaunch()) {
            mPresenter.startPrepareData();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(ListSurahActivity.class);
                    finish();
                }
            }, 3000);
        }

    }

    @Override
    public SplashscreenPresenter initPresenter() {
        return new SplashscreenPresenter(this);    }

    @Override
    public void onPrepare() {

        mLayoutLoadingInfo.setVisibility(View.VISIBLE);


    }

    @Override
    public void onProgress(int progress) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mProgressInfo.setProgress(progress, true);
        } else {
            mProgressInfo.setProgress(progress);
        }

    }

    @Override
    public void onSuccess() {

        PreferenceApp.hasFirstLaunch();
        startActivity(ListSurahActivity.class);
        finish();

    }
}
