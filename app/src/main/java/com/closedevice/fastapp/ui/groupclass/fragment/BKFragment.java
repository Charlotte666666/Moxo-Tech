package com.closedevice.fastapp.ui.groupclass.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.closedevice.fastapp.AppContext;
import com.closedevice.fastapp.R;
import com.closedevice.fastapp.api.remote.ApiFactory;
import com.closedevice.fastapp.base.adapter.ListBaseAdapter;
import com.closedevice.fastapp.base.ui.BaseListFragment;
import com.closedevice.fastapp.gen.BkRecordDao;
import com.closedevice.fastapp.gen.DaoMaster;
import com.closedevice.fastapp.gen.DaoSession;
import com.closedevice.fastapp.model.record.BkRecord;
import com.closedevice.fastapp.model.response.bk.BKItem;
import com.closedevice.fastapp.router.Router;
import com.closedevice.fastapp.ui.BkInsideActivity;
import com.closedevice.fastapp.ui.groupclass.adapter.BKAdapter;
import com.trello.rxlifecycle.FragmentEvent;


import java.util.List;

import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.Context.MODE_PRIVATE;


public class BKFragment extends BaseListFragment<BKItem> {
    static final String TAG = "BKFragment";
    private BkRecordDao bkDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDbHelp();
    }

    @Override
    protected void sendRequest() {
        //获取info文件中的内容
        SharedPreferences sp = getActivity().getSharedPreferences("info", MODE_PRIVATE);
        String username = sp.getString("username","");
        ApiFactory.getsBKApi().getBKList(username).compose(this.bindUntilEvent(FragmentEvent.STOP))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mSubscriber);
    }

    @Override
    protected ListBaseAdapter getListAdapter() {

        BKAdapter adapter = new BKAdapter();
        return adapter;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.bk_menu, menu);//菜单文件
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.create_bk:
                Router.fillBkMsgClass(getActivity());
                break;
            case R.id.join_bk_bycode:
                Router.inputCodeClass(getActivity());
                break;
            case R.id.using_help:
                Router.inputCodeClass(getActivity());
                break;
            case R.id.common_problems:
                Router.inputCodeClass(getActivity());
                break;
        }

        return super.onOptionsItemSelected(item);
    }




    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        BKItem bkItem = mAdapter.getItem(position);
        if (bkItem != null) {
            Intent intent = new Intent(getActivity(), BkInsideActivity.class);
            startActivity(intent);
            //Router.showClassDetail(getActivity());
            //Router.showDetail(getActivity(), bkItem);

            BkRecord br = new BkRecord();
            br.setUsername(bkItem.getUsername());
            br.setStudy_aims(bkItem.getStudy_aims());
            br.setClass_type(bkItem.getClass_type());
            br.setClass_book(bkItem.getClass_book());
            br.setClass_name(bkItem.getClass_name());
            br.setCourse_name(bkItem.getCourse_name());
            br.setCover_address(bkItem.getCover_address());
            br.setExam_schedule(bkItem.getExam_schedule());
            br.setInvite_code(bkItem.getInvite_code());
            br.setSyllabus(bkItem.getSyllabus());
            br.setUsername(bkItem.getUsername());

            SharedPreferences sp =  AppContext.getInstance().getSharedPreferences("info", MODE_PRIVATE);

            SharedPreferences.Editor editor = sp.edit();
            editor.putString("code", bkItem.getInvite_code());
            editor.commit();
            Log.i(TAG,"-------------"+bkItem.getInvite_code());

            bkDao.insert(br);


        }
    }


    /*初始化数据库相关*/
    private void initDbHelp() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getActivity(), "bk-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        bkDao = daoSession.getBkRecordDao();
    }

}
