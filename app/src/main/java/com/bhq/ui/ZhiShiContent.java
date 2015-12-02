//package com.bhq.ui;
//
//import android.annotation.SuppressLint;
//import android.graphics.Bitmap;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.bhq.R;
//import com.bhq.bean.AppKnowledge;
//import com.lidroid.xutils.BitmapUtils;
//import com.lidroid.xutils.bitmap.BitmapCommonUtils;
//
//@SuppressLint({ "NewApi", "ValidFragment" })
//public class ZhiShiContent extends FragmentActivity implements OnClickListener
//{
//	private TextView tv_title, tv_time, tv_content, tv_titlebar;
//	ImageButton btn_previous;
//	private ImageView iv_ima;
//	AppKnowledge knowledge;
//	ListView lv_tonggao;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState)
//	{
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.tonggaocontent);
//		getActionBar().hide();
//		findView();
//		knowledge = getIntent().getParcelableExtra("AppKnowledge");
//		tv_titlebar.setText("知识库");
//		tv_title.setText(knowledge.getTitle().toString());
//		tv_time.setText(FormatTools.DateString2Date(knowledge.getInputDate().toString()));
//		tv_content.setText("        " + knowledge.getContent().toString());
//		iv_ima.setVisibility(View.GONE);
//		if (knowledge.getBDLJ() != null)
//		{
//			setPhotos(iv_ima, knowledge);
//		}
//		CustomApplication.getInstance().addActivity(this);
//	}
//
//	@Override
//	public void onClick(View v)
//	{
//		switch (v.getId())
//		{
//		case R.id.btn_previous:
//			finish();
//			break;
//
//		default:
//			break;
//		}
//	}
//
//	private void findView()
//	{
//		tv_title = (TextView) findViewById(R.id.tv_title);
//		findViewById(R.id.btn_previous).setOnClickListener(this);
//		;
//		tv_titlebar = (TextView) findViewById(R.id.tv_titlebar);
//		tv_time = (TextView) findViewById(R.id.tv_time);
//		tv_content = (TextView) findViewById(R.id.tv_content);
//		iv_ima = (ImageView) findViewById(R.id.iv_ima);
//	}
//
//	private void setPhotos(ImageView imageView, AppKnowledge appKnowledge)
//	{
//		int networkType = NetWorkStatus.getNetworkType(this);
//		if (networkType == 1)
//		{
//			setPhotosInServer(imageView, appKnowledge.getImgPath());
//		} else
//		{
//			setPhotosInSqlite(imageView, appKnowledge.getBDLJ());
//		}
//	}
//
//	private void setPhotosInServer(ImageView imageView, String ZPDZ)
//	{
//		BitmapUtils bitmapUtils = BitmapHelp.getBitmapUtils(getApplicationContext());
//		bitmapUtils.configDefaultLoadingImage(R.drawable.ic_launcher);
//		bitmapUtils.configDefaultLoadFailedImage(R.drawable.ic_launcher);
//		bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
//		bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils.getScreenSize(this).scaleDown(3));
//		bitmapUtils.display(imageView, ZPDZ);
//	}
//
//	private void setPhotosInSqlite(ImageView imageView, String ZPDZ)
//	{
//		ImageHelper.setImageView(imageView, ZPDZ);
//	}
// }
