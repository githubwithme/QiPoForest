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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bhq.R;
import com.bhq.app.AppContext;
import com.bhq.bean.Dictionary;
import com.bhq.bean.FJ_SCFJ;
import com.bhq.bean.XBBean;
import com.bhq.bean.dt_manager_offline;
import com.bhq.common.BitmapHelper;
import com.bhq.common.SqliteDb;
import com.bhq.common.utils;
import com.bhq.widget.MyDialog;
import com.bhq.widget.MyDialog.CustomDialogListener;
import com.media.HomeFragmentActivity;
import com.media.MediaChooser;
import com.wheel.OneWheel;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    @ViewById
    TextView tv_xbh;
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

    @Click
    void tv_xbh()
    {
        String[] firstItemid = new String[]{};
        String[] firstItemData = new String[]{};
        List<String> list_id = new ArrayList<String>();
        List<String> list_name = new ArrayList<String>();
        for (int i = 0; i < list_dic.size(); i++)
        {
            if (list_dic.get(i).getLX().equals("SJLX"))
            {
                list_id.add(list_dic.get(i).getDID());
                list_name.add(list_dic.get(i).getNAME());
            }
        }
        firstItemid = new String[list_id.size()];
        firstItemData = new String[list_id.size()];
        for (int i = 0; i < list_id.size(); i++)
        {
            firstItemid[i] = list_id.get(i);
            firstItemData[i] = list_name.get(i);
        }
        if (list_id.size() != 0)
        {
            OneWheel.showWheel(this, firstItemid, firstItemData, tv_xbh);
        } else
        {
            Toast.makeText(Offline_AddSurvey.this, "数据获取异常，请重试！", Toast.LENGTH_SHORT).show();
        }
    }

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
        xbBean.setXBH(tv_xbh.getText().toString());
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
//	private void uploadData()
//	{
//		if ((Bundle) tv_type.getTag() != null)
//		{
//			SJLX = ((Bundle) tv_type.getTag()).getString("FI");
//			SJLXMC = ((Bundle) tv_type.getTag()).getString("FN");
//		}
//		BHQ_XHSJ bhq_XHSJ = new BHQ_XHSJ();
//		bhq_XHSJ.setSJID(SJID);
//		bhq_XHSJ.setSJLX(SJLX);
//		bhq_XHSJ.setSJLXMC(SJLXMC);
//		bhq_XHSJ.setSJMS(et_sjms.getText().toString());
//		bhq_XHSJ.setCLQK(et_clqk.getText().toString());
//		bhq_XHSJ.setSBR(dt_manager_offline.getid());
//		bhq_XHSJ.setSBRXM(dt_manager_offline.getreal_name());
//		bhq_XHSJ.setSBSJ(utils.getTime());
//		bhq_XHSJ.setSSBHZ(dt_manager_offline.getDepartId());
//		bhq_XHSJ.setLCZT("1");
//		bhq_XHSJ.setX(appContext.getLOCATION_Y());
//		bhq_XHSJ.setY(appContext.getLOCATION_X());
//		bhq_XHSJ.setXHID(appContext.getXHID());
//		bhq_XHSJ.setIsUpload("0");
//		bhq_XHSJ.setSFSC("0");
//		boolean issuccess = SqliteDb.save(Offline_AddSurvey.this, bhq_XHSJ);
//		if (issuccess)
//		{
//			if (list_allfj.size() > 0)
//			{
//				boolean success = SqliteDb.saveAll(Offline_AddSurvey.this, list_allfj);
//				if (success)
//				{
//					Toast.makeText(Offline_AddSurvey.this, "保存成功！", Toast.LENGTH_SHORT).show();
//					finish();
//				} else
//				{
//					pb_upload.setVisibility(View.GONE);
//					btn_upload.setVisibility(View.VISIBLE);
//					Toast.makeText(Offline_AddSurvey.this, "数据保存失败", Toast.LENGTH_SHORT).show();
//				}
//			} else
//			{
//				Toast.makeText(Offline_AddSurvey.this, "保存成功！", Toast.LENGTH_SHORT).show();
//				finish();
//			}
//		} else
//		{
//			pb_upload.setVisibility(View.GONE);
//			btn_upload.setVisibility(View.VISIBLE);
//			Toast.makeText(Offline_AddSurvey.this, "上传失败", Toast.LENGTH_SHORT).show();
//		}
//
//	}

    private void getDICS()
    {
        list_dic = SqliteDb.getDics(Offline_AddSurvey.this, Dictionary.class);
        if (list_dic == null)
        {
            list_dic = new ArrayList<Dictionary>();
        }

    }
}
