package com.bhq.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.bhq.R;
import com.bhq.app.AppContext;
import com.bhq.bean.Dictionary;
import com.bhq.bean.FJ_SCFJ;
import com.bhq.bean.LCXBH;
import com.bhq.bean.XBBean;
import com.bhq.bean.dt_manager_offline;
import com.bhq.common.BitmapHelper;
import com.bhq.common.SqliteDb;
import com.bhq.common.utils;
import com.bhq.widget.MyDialog;
import com.bhq.widget.MyDialog.CustomDialogListener;
import com.media.HomeFragmentActivity;
import com.media.MediaChooser;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author :hc-sima
 * @version :1.0
 * @createTime：2015-8-14 下午6:44:17
 * @description :
 */
@SuppressLint("NewApi")
@EActivity(R.layout.addsurvey)
public class Offline_AddSurvey extends Activity
{

    @ViewById
    ProgressBar pb_upload;
    //    @ViewById
//    TextView tv_xbh;
    @ViewById
    EditText et_mgqzs;
    @ViewById
    EditText et_pjsg;
    @ViewById
    EditText et_pjxj;
    @ViewById
    ImageButton imgbtn_addvideo;
    @ViewById
    ImageButton imgbtn_addpicture;
    @ViewById
    LinearLayout ll_picture;
    @ViewById
    LinearLayout ll_video;
    List<FJ_SCFJ> list_picture = new ArrayList<FJ_SCFJ>();
    List<FJ_SCFJ> list_video = new ArrayList<FJ_SCFJ>();
    List<FJ_SCFJ> list_allfj = new ArrayList<FJ_SCFJ>();
    dt_manager_offline dt_manager_offline;
    MyDialog myDialog;
    AppContext appContext;
    String SJLX = "";
    String SJLXMC = "";
    String SJID;
    List<Dictionary> list_dic;

    @ViewById
    Spinner firstSpinner;
    @ViewById
    Spinner secondSpinner;
    @ViewById
    Spinner thirdSpinner;
    @ViewById
    Spinner fourthSpinner;

    ArrayAdapter<String> provinceAdapter = null;  //省级适配器
    ArrayAdapter<String> cityAdapter = null;    //地级适配器
    ArrayAdapter<String> countyAdapter = null;    //县级适配器
    ArrayAdapter<String> XBHAdapter = null;    //县级适配器
    static int provincePosition = 3;
    private String[] mProvinceDatas;
    private Map<String, String[]> firstDatasMap = new HashMap<String, String[]>();
    private Map<String, String[]> secondDatasMap = new HashMap<String, String[]>();
    private Map<String, String[]> thirdDatasMap = new HashMap<String, String[]>();
    private String currentFirstName;
    private String currentSecondName;
    private String currentThirdName = "";


    @ViewById
    Button btn_upload;

    @Click
    void btn_upload()
    {
        pb_upload.setVisibility(View.VISIBLE);
        btn_upload.setVisibility(View.GONE);
        saveData();
    }

    @Click
    void imgbtn_addpicture()
    {
        Intent intent = new Intent(Offline_AddSurvey.this, HomeFragmentActivity.class);
        intent.putExtra("type", "picture");
        startActivity(intent);
    }

    @Click
    void imgbtn_addvideo()
    {
        Intent intent = new Intent(Offline_AddSurvey.this, HomeFragmentActivity.class);
        intent.putExtra("type", "video");
        startActivity(intent);
    }

    @Click
    void imgbtn_addluyin()
    {
        Intent intent = new Intent(Offline_AddSurvey.this, HomeFragmentActivity.class);
        intent.putExtra("type", "luyin");
        startActivity(intent);
    }

    @AfterViews
    void afterOncreate()
    {
//        initCityDatas();
        initLCXBHData();
        setSpinner();
        getDICS();
    }

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getActionBar().hide();
        SJID = java.util.UUID.randomUUID().toString();
        IntentFilter videoIntentFilter = new IntentFilter(MediaChooser.VIDEO_SELECTED_ACTION_FROM_MEDIA_CHOOSER);
        registerReceiver(videoBroadcastReceiver, videoIntentFilter);

        IntentFilter imageIntentFilter = new IntentFilter(MediaChooser.IMAGE_SELECTED_ACTION_FROM_MEDIA_CHOOSER);
        registerReceiver(imageBroadcastReceiver, imageIntentFilter);

        dt_manager_offline = (com.bhq.bean.dt_manager_offline) SqliteDb.getCurrentUser(Offline_AddSurvey.this, dt_manager_offline.class);
        appContext = (AppContext) getApplication();
    }

    ;

    BroadcastReceiver imageBroadcastReceiver = new BroadcastReceiver()// 植物（0为整体照，1为花照，2为果照，3为叶照）；动物（0为整体照，1为脚印照，2为粪便照）
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            List<String> list = intent.getStringArrayListExtra("list");
            for (int i = 0; i < list.size(); i++)
            {
                String FJBDLJ = list.get(i);
                ImageView imageView = new ImageView(Offline_AddSurvey.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(180, LayoutParams.MATCH_PARENT, 0);
                lp.setMargins(25, 4, 0, 4);
                imageView.setLayoutParams(lp);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                BitmapHelper.setImageView(Offline_AddSurvey.this, imageView, FJBDLJ);
                imageView.setTag(FJBDLJ);

                FJ_SCFJ fj_SCFJ = new FJ_SCFJ();
                String fjmc = FJBDLJ.substring(FJBDLJ.lastIndexOf("/") + 1, FJBDLJ.length());
                String fjlj = "/upload/" + utils.getToday() + "/" + fjmc;
                String LSTLJ = "/upload/" + utils.getToday() + "/" + "thumb_" + fjmc;
                String FJID = java.util.UUID.randomUUID().toString();

                fj_SCFJ.setFJID(FJID);
                fj_SCFJ.setGLID(SJID);
                fj_SCFJ.setGLBM("XHSJ");
                fj_SCFJ.setFJMC(fjmc);
                fj_SCFJ.setFJLJ(fjlj);
                fj_SCFJ.setLSTLJ(LSTLJ);
                fj_SCFJ.setSCR(dt_manager_offline.getid());
                fj_SCFJ.setSCSJ(utils.getTime());
                fj_SCFJ.setSCRXM(dt_manager_offline.getreal_name());
                fj_SCFJ.setFJLX("1");
                fj_SCFJ.setSFSC("0");
                fj_SCFJ.setSCZT("1");
                fj_SCFJ.setChange("0");
                fj_SCFJ.setFJBDLJ(FJBDLJ);
                fj_SCFJ.setFL("");
                fj_SCFJ.setISUPLOAD("0");

                list_picture.add(fj_SCFJ);
                list_allfj.add(fj_SCFJ);
                ll_picture.addView(imageView);

                imageView.setOnClickListener(new OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        final int index_zp = ll_picture.indexOfChild(v);
                        View dialog_layout = (LinearLayout) getLayoutInflater().inflate(R.layout.customdialog_callback, null);
                        myDialog = new MyDialog(Offline_AddSurvey.this, R.style.MyDialog, dialog_layout, "图片", "查看该图片?", "查看", "删除", new CustomDialogListener()
                        {
                            @Override
                            public void OnClick(View v)
                            {
                                switch (v.getId())
                                {
                                    case R.id.btn_sure:
                                        File file = new File(list_picture.get(index_zp).getFJBDLJ());
                                        Intent intent = new Intent(Intent.ACTION_VIEW);
                                        intent.setDataAndType(Uri.fromFile(file), "image/*");
                                        startActivity(intent);
                                        break;
                                    case R.id.btn_cancle:
                                        ll_picture.removeViewAt(index_zp);
                                        list_picture.remove(index_zp);
                                        myDialog.dismiss();
                                        break;
                                }
                            }
                        });
                        myDialog.show();
                    }
                });
            }
        }
    };
    BroadcastReceiver videoBroadcastReceiver = new BroadcastReceiver()// 植物（0为整体照，1为花照，2为果照，3为叶照）；动物（0为整体照，1为脚印照，2为粪便照）
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            List<String> list = intent.getStringArrayListExtra("list");
            for (int i = 0; i < list.size(); i++)
            {
                String FJBDLJ = list.get(i);
                ImageView imageView = new ImageView(Offline_AddSurvey.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(180, LayoutParams.MATCH_PARENT, 0);
                lp.setMargins(25, 4, 0, 4);
                imageView.setLayoutParams(lp);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setTag(FJBDLJ);
                imageView.setImageBitmap(BitmapHelper.getVideoThumbnail(FJBDLJ, 120, 120, MediaStore.Images.Thumbnails.MICRO_KIND));

                FJ_SCFJ fj_SCFJ = new FJ_SCFJ();
                String fjmc = FJBDLJ.substring(FJBDLJ.lastIndexOf("/") + 1, FJBDLJ.length());
                String fjlj = "/upload/" + utils.getToday() + "/" + fjmc;
                String LSTLJ = "/upload/" + utils.getToday() + "/" + "thumb_" + fjmc;
                String FJID = java.util.UUID.randomUUID().toString();

                fj_SCFJ.setFJID(FJID);
                fj_SCFJ.setGLID(SJID);
                fj_SCFJ.setGLBM("XHSJ");
                fj_SCFJ.setFJMC(fjmc);
                fj_SCFJ.setFJLJ(fjlj);
                fj_SCFJ.setLSTLJ(LSTLJ);
                fj_SCFJ.setSCR(dt_manager_offline.getid());
                fj_SCFJ.setSCSJ(utils.getTime());
                fj_SCFJ.setSCRXM(dt_manager_offline.getreal_name());
                fj_SCFJ.setFJLX("2");
                fj_SCFJ.setSFSC("0");
                fj_SCFJ.setSCZT("1");
                fj_SCFJ.setChange("0");
                fj_SCFJ.setFJBDLJ(FJBDLJ);
                fj_SCFJ.setFL("");
                fj_SCFJ.setISUPLOAD("0");

                list_video.add(fj_SCFJ);
                list_allfj.add(fj_SCFJ);
                ll_video.addView(imageView);

                imageView.setOnClickListener(new OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        final int index_zp = ll_video.indexOfChild(v);
                        View dialog_layout = (LinearLayout) getLayoutInflater().inflate(R.layout.customdialog_callback, null);
                        myDialog = new MyDialog(Offline_AddSurvey.this, R.style.MyDialog, dialog_layout, "图片", "查看该视频?", "查看", "删除", new CustomDialogListener()
                        {
                            @Override
                            public void OnClick(View v)
                            {
                                switch (v.getId())
                                {
                                    case R.id.btn_sure:
                                        File file = new File(list_video.get(index_zp).getFJBDLJ());
                                        Intent intent = new Intent(Intent.ACTION_VIEW);
                                        intent.setDataAndType(Uri.fromFile(file), "video/*");
                                        startActivity(intent);
                                        break;
                                    case R.id.btn_cancle:
                                        ll_video.removeViewAt(index_zp);
                                        list_video.remove(index_zp);
                                        myDialog.dismiss();
                                        break;
                                }
                            }
                        });
                        myDialog.show();
                    }
                });
            }
        }
    };

    private void saveData()
    {
        XBBean xbBean = new XBBean();
        xbBean.setID(SJID);
        xbBean.setXBH(firstSpinner.getSelectedItem().toString() + secondSpinner.getSelectedItem().toString() + thirdSpinner.getSelectedItem().toString());
        xbBean.setPJSG(et_pjsg.getText().toString());
        xbBean.setPJXJ(et_pjxj.getText().toString());
        xbBean.setMGQZS(et_mgqzs.getText().toString());
        xbBean.setX(appContext.getLOCATION_Y());
        xbBean.setY(appContext.getLOCATION_X());
        xbBean.setIsUpload("0");
        xbBean.setSFSC("0");
        boolean issuccess = SqliteDb.save(Offline_AddSurvey.this, xbBean);
        if (issuccess)
        {
            if (list_allfj.size() > 0)
            {
                boolean success = SqliteDb.saveAll(Offline_AddSurvey.this, list_allfj);
                if (success)
                {
                    Toast.makeText(Offline_AddSurvey.this, "保存成功！", Toast.LENGTH_SHORT).show();
                    finish();
                } else
                {
                    pb_upload.setVisibility(View.GONE);
                    btn_upload.setVisibility(View.VISIBLE);
                    Toast.makeText(Offline_AddSurvey.this, "数据保存失败", Toast.LENGTH_SHORT).show();
                }
            } else
            {
                Toast.makeText(Offline_AddSurvey.this, "保存成功！", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else
        {
            pb_upload.setVisibility(View.GONE);
            btn_upload.setVisibility(View.VISIBLE);
            Toast.makeText(Offline_AddSurvey.this, "上传失败", Toast.LENGTH_SHORT).show();
        }
    }


    private void getDICS()
    {
        list_dic = SqliteDb.getDics(Offline_AddSurvey.this, Dictionary.class);
        if (list_dic == null)
        {
            list_dic = new ArrayList<Dictionary>();
        }

    }


    /*
   * 设置下拉框
   */
    private void setSpinner()
    {
        //绑定适配器和值
        provinceAdapter = new ArrayAdapter<String>(Offline_AddSurvey.this, android.R.layout.simple_spinner_item, mProvinceDatas);
        firstSpinner.setAdapter(provinceAdapter);
        firstSpinner.setSelection(0, true);  //设置默认选中项，此处为默认选中第0个值
        currentFirstName = mProvinceDatas[0];

        cityAdapter = new ArrayAdapter<String>(Offline_AddSurvey.this, android.R.layout.simple_spinner_item, firstDatasMap.get(currentFirstName));
        secondSpinner.setAdapter(cityAdapter);
        secondSpinner.setSelection(0, true);  //默认选中第0个
        currentSecondName = firstDatasMap.get(currentFirstName)[0];

        countyAdapter = new ArrayAdapter<String>(Offline_AddSurvey.this, android.R.layout.simple_spinner_item, secondDatasMap.get(currentSecondName));
        thirdSpinner.setAdapter(countyAdapter);
        thirdSpinner.setSelection(0, true);
        currentThirdName = secondDatasMap.get(currentSecondName)[0];

        XBHAdapter = new ArrayAdapter<String>(Offline_AddSurvey.this, android.R.layout.simple_spinner_item, thirdDatasMap.get(currentThirdName));
        fourthSpinner.setAdapter(XBHAdapter);
        fourthSpinner.setSelection(0, true);

        //省级下拉框监听
        firstSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            // 表示选项被改变的时候触发此方法，主要实现办法：动态改变地级适配器的绑定值
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                provincePosition = position;    //记录当前省级序号，留给下面修改县级适配器时用
                currentFirstName = firstSpinner.getSelectedItem().toString();
                //position为当前省级选中的值的序号
                //将地级适配器的值改变为city[position]中的值
                cityAdapter = new ArrayAdapter<String>(Offline_AddSurvey.this, android.R.layout.simple_spinner_item, firstDatasMap.get(currentFirstName));
                // 设置二级下拉列表的选项内容适配器
                secondSpinner.setAdapter(cityAdapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            {

            }

        });


        //地级下拉监听
        secondSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                countyAdapter = new ArrayAdapter<String>(Offline_AddSurvey.this, android.R.layout.simple_spinner_item, secondDatasMap.get(firstDatasMap.get(currentFirstName)[position]));
                thirdSpinner.setAdapter(countyAdapter);
                currentSecondName = secondSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            {

            }
        });
         //地级下拉监听
        thirdSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                XBHAdapter = new ArrayAdapter<String>(Offline_AddSurvey.this, android.R.layout.simple_spinner_item, thirdDatasMap.get(secondDatasMap.get(currentSecondName)[position]));
                fourthSpinner.setAdapter(XBHAdapter);
                currentThirdName = thirdSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            {

            }
        });
    }

    /**
     * 解析整个省市区的Json对象，完成后释放Json对象的内存
     * ZC:总场；FC：分场；ZD：站点；LBH:林班号；XBH:小班号
     */
    private void initCityDatas()
    {
        mProvinceDatas = SqliteDb.getSLXBL(Offline_AddSurvey.this, LCXBH.class, "FC", "ZC", "英德");
        for (int i = 0; i < mProvinceDatas.length; i++)
        {
            String[] mCitiesDatas = SqliteDb.getSLXBL(Offline_AddSurvey.this, LCXBH.class, "ZD", "FC", mProvinceDatas[i]);
            for (int j = 0; j < mCitiesDatas.length; j++)
            {
                String[] mAreasDatas = SqliteDb.getSLXBL(Offline_AddSurvey.this, LCXBH.class, "LBH", "ZD", mCitiesDatas[j]);
                for (int k = 0; k < mAreasDatas.length; k++)
                {
                    String[] thirdDatas = SqliteDb.getSLXBL(Offline_AddSurvey.this, LCXBH.class, "XBH", "LBH", mAreasDatas[k]);
                    thirdDatasMap.put(mAreasDatas[k], thirdDatas);
                }
                secondDatasMap.put(mCitiesDatas[j], mAreasDatas);
            }
            firstDatasMap.put(mProvinceDatas[i], mCitiesDatas);
        }
    }

    private void initLCXBHData()
    {
        //第一级数据
        mProvinceDatas = new String[]{"七坡总场", "康宁分场"};

        //二级数据
        String[] tempdata = new String[]{"那丹站", "七坡站"};
        firstDatasMap.put(mProvinceDatas[0], tempdata);
        tempdata = new String[]{"康宁站", "那马站"};
        firstDatasMap.put(mProvinceDatas[1], tempdata);

        //三级数据
        tempdata = new String[]{"452002001"};
        secondDatasMap.put("那丹站", tempdata);
        tempdata = new String[]{"452002002"};
        secondDatasMap.put("七坡站", tempdata);
        tempdata = new String[]{"452002003"};
        secondDatasMap.put("康宁站", tempdata);
        tempdata = new String[]{"452002004"};
        secondDatasMap.put("那马站", tempdata);

        //四级数据
        tempdata = new String[]{"452002001001", "452002001002"};
        thirdDatasMap.put("452002001", tempdata);
        tempdata = new String[]{"452002002001", "452002002002"};
        thirdDatasMap.put("452002002", tempdata);
        tempdata = new String[]{"452002003001", "452002003002"};
        thirdDatasMap.put("452002003", tempdata);
        tempdata = new String[]{"452002004001", "452002004002"};
        thirdDatasMap.put("452002004", tempdata);

    }
}
