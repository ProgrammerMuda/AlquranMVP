package data.io.co.alquranmvp.base;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.BaseAdapter;

import butterknife.ButterKnife;
import butterknife.Unbinder;


import data.io.co.alquranmvp.R;
import data.io.co.alquranmvp.database.DatabaseContract;

public abstract  class BaseActivity<PT extends BasePresenter> extends AppCompatActivity {

    public PT mPresenter;
    public  String LOAD_INDONESIA = DatabaseContract.LOAD_TERJEMEMAHAN_INDONESIA;
    public String LOAD_ENGLISH = DatabaseContract.LOAD_TERJEMEMAHAN_ENGLISH;

    public  abstract  PT initPresenter();

    private Unbinder unbinder;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        unbinder = ButterKnife.bind(this);
        mPresenter = initPresenter();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (mPresenter != null){
            mPresenter.detach();
        }

    }

    public void startActivity(Class target){
        startActivity(new Intent(this, target));
    }
}
