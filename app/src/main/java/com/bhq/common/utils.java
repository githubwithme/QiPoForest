package com.bhq.common;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bhq.R;
import com.bhq.widget.MyDialog;
import com.bhq.widget.MyDialog.CustomDialogListener;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author :sima
 * @version :1.0
 * @createTime：2015-8-9 下午9:27:19
 * @description :常用工具类
 */
public class utils
{
    static MyDialog myDialog;

    public static Bitmap drawable2Bitmap(Drawable drawable)
    {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static String getTime()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        return str;
    }

    public static boolean isCurrentTimeBetWeen()
    {
        //当前时间
        Date curDate = new Date(System.currentTimeMillis());
        //指定时间段
        SimpleDateFormat format_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format_day = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date begin = null;
        java.util.Date end = null;
        try
        {
            String str_today = format_day.format(curDate);
            java.util.Date curtime = format_time.parse(str_today + " 08:00:00");
            begin = format_time.parse(str_today + " 08:30:00");
            end = format_time.parse(str_today + " 18:30:00");
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        if (curDate.after(begin) && curDate.before(end))
        {
            return true;
        } else
        {
            return false;
        }

    }

    public static String getHours()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        return str;
    }

    public static String getToday()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        return str;
    }

    public static String formatdate(String date)
    {
        String s = date.substring(6, date.lastIndexOf("+"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String str = formatter.format(s);
        return str;
    }

    public static String DateString2Date(String str)
    {
        if (str.equals("") || str.equals("null"))
        {
            return "";
        }
        String time = str.substring(6, str.length() - 7);
        Date date = new Date(Long.parseLong(time));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dt = format.format(date);
        return dt;
    }

    public static String DateString2Day(String str)
    {
        if (str.equals("") || str.equals("null"))
        {
            return "";
        }
        String time = str.substring(6, str.length() - 7);
        Date date = new Date(Long.parseLong(time));
        SimpleDateFormat format = new SimpleDateFormat("MM-dd");
        String dt = format.format(date);
        return dt;
    }

    public static String DateString2DateTime(String str)
    {
        if (str.equals("") || str.equals("null"))
        {
            return "";
        }
        String time = str.substring(6, str.length() - 7);
        Date date = new Date(Long.parseLong(time));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dt = format.format(date);
        return dt;
    }

    // public static String formatdate(String str1)
    // {
    // SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    // String str = formatter.format(str1);
    // return str;
    // }

    public static String getDayDifference(String time1, String time2)
    {
        String dt = new String();
        if (time1.equals("") || time1.equals("null") || time2.equals("") || time2.equals("null"))
        {
            return "";
        }
        try
        {
            SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date begin = dfs.parse(time1);
            java.util.Date end = dfs.parse(time2);

            if (end.before(begin))
            {
                dt = "已到期";
                return dt;
            }
            long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
            long day1 = between / (24 * 3600);
            dt = "距离" + day1 + "天";
        } catch (Exception e)
        {
        }
        return dt;
    }

    /**
     * 随机指定范围内N个不重复的数 最简单最基本的方法
     *
     * @param min 指定范围最小值
     * @param max 指定范围最大值
     * @param n   随机数个数
     */
    public static Double[] randomCommon(Double min, Double max, int n)
    {
        if (n > (max - min + 1) || max < min)
        {
            return null;
        }
        Double[] result = new Double[n];
        int count = 0;
        while (count < n)
        {
            Double num = (Math.random() * (max - min)) + min;
            boolean flag = true;
            for (int j = 0; j < n; j++)
            {
                if (num == result[j])
                {
                    flag = false;
                    break;
                }
            }
            if (flag)
            {
                result[count] = num;
                count++;
            }
        }
        return result;
    }

    /**
     * @return 该毫秒数转换为 * days * hours * minutes * seconds 后的格式
     * @author fy.zhang
     */
    public static String formatDuring(long mss)
    {
        String h = new String();
        String m = new String();
        String s = new String();
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        if (hours != 0)
        {
            if (hours < 10)
            {
                h = "0" + hours;
            }else
            {
                h=String.valueOf(hours);
            }
        } else
        {
            h = "00";
        }
        if (minutes != 0)
        {
            if (minutes < 10)
            {
                m = "0" + minutes;
            } else if (minutes < 60)
            {
                m = String.valueOf(minutes);
            }
        } else
        {
            m = "00";
        }
        if (seconds != 0)
        {
            if (seconds < 10)
            {
                s = "0" + seconds;
            } else
            {
                s = String.valueOf(seconds);
            }
        } else
        {
            s = "00";
        }
        return h + ":" + m + ":" + s;
    }

    public static int getStatusHeight(Context context)
    {

        int statusHeight = -1;
        try
        {
            Class clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return statusHeight;
    }

    public static int openGPSSettings(final Activity context)
    {
        // 获取位置管理服务
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER))
        {
            Toast.makeText(context, "GPS正常", Toast.LENGTH_SHORT).show();
            return 1;
        }
        View dialog_layout = (LinearLayout) context.getLayoutInflater().inflate(R.layout.customdialog_callback, null);
        myDialog = new MyDialog(context, R.style.MyDialog, dialog_layout, "gps设置", "GPS定位服务还没打开!进入gps设置界面?", "进入", "不，谢了！", new CustomDialogListener()
        {
            @Override
            public void OnClick(View v)
            {
                switch (v.getId())
                {
                    case R.id.btn_sure:
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        context.startActivityForResult(intent, 0); // 此为设置完成后返回到获取界面
                        myDialog.dismiss();
                        break;
                    case R.id.btn_cancle:
                        myDialog.dismiss();
                        break;
                }
            }
        });
        myDialog.show();
        return 0;
    }

    /**
     * 从assetts文件夹中读取json文件，然后转化为json对象
     *
     * @param jsonFileName json文件名
     */
    public static String parseJsonFile(Context context, String jsonFileName)
    {
        try
        {
            StringBuffer sb = new StringBuffer();
            InputStream is = context.getAssets().open(jsonFileName);
            int len = -1;
            byte[] buf = new byte[1024 * 1024];
            while ((len = is.read(buf)) != -1)
            {
                sb.append(new String(buf, 0, len, "utf-8"));
            }
            is.close();
            return sb.toString();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static String getRate(int a, int b)
    {
        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(0);
        String result = numberFormat.format((float) a / (float) b * 100);
        return result;
    }

    public static String getRateoffloat(float a, float b)
    {
        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(0);
        String result = numberFormat.format((float) a / (float) b * 100);
        return result;
    }

    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param mContext
     * @param serviceName 是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    public static boolean isServiceWork(Context mContext, String serviceName)
    {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningServiceInfo> myList = myAM.getRunningServices(40);
        if (myList.size() <= 0)
        {
            return false;
        }
        for (int i = 0; i < myList.size(); i++)
        {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName))
            {
                isWork = true;
                break;
            }
        }
        return isWork;
    }

    /**
     * 用来判断服务是否运行.
     *
     * @param className 判断的服务名字
     * @return true 在运行 false 不在运行
     */
    public static boolean isServiceRunning(Context mContext, String className)
    {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(30);
        if (!(serviceList.size() > 0))
        {
            return false;
        }
        for (int i = 0; i < serviceList.size(); i++)
        {
            if (serviceList.get(i).service.getClassName().equals(className) == true)
            {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     *
     * @param context
     * @return true 表示开启
     */
    public static final boolean isOPen(final Context context)
    {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network)
        {
            return true;
        }

        return false;
    }

    /**
     * 强制帮用户打开GPS
     *
     * @param context
     */
    public static final void openGPS(Context context)
    {
        Intent GPSIntent = new Intent();
        GPSIntent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
        GPSIntent.setData(Uri.parse("custom:3"));
        try
        {
            PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
        } catch (CanceledException e)
        {
            e.printStackTrace();
        }
    }

    public static boolean isConnect(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected())
        {
            // 改变背景或者 处理网络的全局变量
            return false;
        }
        return true;
    }

    // DES加密
    // public static String encode(byte[] key, byte[] iv, byte[] data) throws
    // Exception
    // {
    // Key deskey = null;
    // DESKeySpec spec = new DESKeySpec(key);
    // SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
    // deskey = keyFactory.generateSecret(spec);
    // IvParameterSpec ips = new IvParameterSpec(iv);
    // Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
    // cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
    // return new BASE64Encoder().encode(cipher.doFinal(data, 0, data.length));
    // }
}
