package com.bhq.common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.bhq.bean.BHQ_XHQK;
import com.bhq.bean.BHQ_XHQK_GJ;
import com.bhq.bean.BHQ_XHQK_ZTCZ;
import com.bhq.bean.BHQ_XHSJ;
import com.bhq.bean.BHQ_XHSJCJ;
import com.bhq.bean.BHQ_XHXL;
import com.bhq.bean.BHQ_XHXL_GJ;
import com.bhq.bean.BHQ_ZSK;
import com.bhq.bean.BQH_XHRY;
import com.bhq.bean.Dictionary;
import com.bhq.bean.ExceptionInfo;
import com.bhq.bean.FJ_SCFJ;
import com.bhq.bean.RW_CYR;
import com.bhq.bean.RW_RW;
import com.bhq.bean.RW_YQB;
import com.bhq.bean.SLXBL;
import com.bhq.bean.dt_manager;
import com.bhq.bean.dt_manager_offline;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.SqlInfo;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.db.table.DbModel;
import com.lidroid.xutils.exception.DbException;

import java.util.ArrayList;
import java.util.List;

public class SqliteDb
{
    static SQLiteDatabase sqLiteDatabase = null;


    static DbUtils db = null;

    public static void InitDbutils(Context context)
    {
        CustomDbUpgradeListener customDbUpgradeListener = new CustomDbUpgradeListener();
        if (db == null)
        {
            db = DbUtils.create(context, "JHWG", 11, customDbUpgradeListener);
            sqLiteDatabase = db.getDatabase();
        }
    }

    /**
     * 方法1：检查某表列是否存在
     *
     * @param db
     * @param tableName  表名
     * @param columnName 列名
     * @return
     */
    public static boolean checkColumnExist1(SQLiteDatabase db, String tableName, String columnName)
    {
        boolean result = false;
        Cursor cursor = null;
        try
        {
            //查询一行
            cursor = db.rawQuery("SELECT * FROM " + tableName + " LIMIT 0", null);
            result = cursor != null && cursor.getColumnIndex(columnName) != -1;
        } catch (Exception e)
        {
//            Log.e(TAG, "checkColumnExists1..." + e.getMessage());
        } finally
        {
            if (null != cursor && !cursor.isClosed())
            {
                cursor.close();
            }
        }

        return result;
    }

    public static boolean save(Context context, Object obj)
    {
        InitDbutils(context);
        try
        {
            db.replace(obj);
        } catch (DbException e)
        {
            e.printStackTrace();
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean saveUserInfo(Context context, Object obj)
    {
        InitDbutils(context);
        try
        {
            db.update(obj, "salt", "telephone", "real_name", "user_name", "password", "UserType", "DepartId", "isPatrol", "SFSC");
        } catch (DbException e)
        {
            e.printStackTrace();
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean updateIsRead(Context context, Object obj)
    {
        InitDbutils(context);
        try
        {
            db.update(obj, "IsRead");
        } catch (DbException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static <T> boolean deleteRecord(Context context, Class<T> c, String firsttype, String secondType, String thirdtype)
    {
        InitDbutils(context);
        try
        {
            db.delete(c, WhereBuilder.b("firsttype", "=", firsttype).and("secondType", "=", secondType).and("thirdtype", "=", thirdtype));
        } catch (DbException e)
        {
            e.printStackTrace();
            String a = e.getMessage();
            return false;
        }
        return true;
    }

    public static <T> boolean deleteAllRecord(Context context, Class<T> c)
    {
        InitDbutils(context);
        try
        {
            db.deleteAll(c);
        } catch (DbException e)
        {
            e.printStackTrace();
            String a = e.getMessage();
            return false;
        }
        return true;
    }


    public static <T> boolean dropTable(Context context, Class<T> c)
    {
        InitDbutils(context);
        try
        {
            db.deleteAll(c);
        } catch (DbException e)
        {
            e.printStackTrace();
            String a = e.getMessage();
            return false;
        }
        return true;
    }

    public static <T> boolean dropDb(Context context)
    {
        InitDbutils(context);
        try
        {
            db.dropDb();
        } catch (DbException e)
        {
            e.printStackTrace();
            String a = e.getMessage();
            return false;
        }
        return true;
    }

    public static <T> boolean saveAll(Context context, List<T> list)
    {
        InitDbutils(context);
        db.configAllowTransaction(true);
        try
        {
            for (int i = 0; i < list.size(); i++)
            {
                db.replace(list.get(i));
            }
        } catch (DbException e)
        {
            String aa = e.getMessage();
            e.printStackTrace();
            return false;
        } finally
        {
            db.configAllowTransaction(false);
        }
        return true;
    }

    public static <T> List<T> getSelectRecord(Context context, Class<T> c, String firsttype, String secondType)
    {
        InitDbutils(context);
        List<T> list = null;
        try
        {
            list = db.findAll(Selector.from(c).where("firsttype", "=", firsttype).and("secondType", "=", secondType));
        } catch (DbException e)
        {
            e.printStackTrace();
        }
        if (null == list || list.isEmpty())
        {
            list = new ArrayList<T>();
        }
        return list;
    }

    public static <T> List<T> getSelectRecordByFirstType(Context context, Class<T> c, String firsttype)
    {
        InitDbutils(context);
        List<T> list = null;
        try
        {
            list = db.findAll(Selector.from(c).where("firsttype", "=", firsttype));
        } catch (DbException e)
        {
            e.printStackTrace();
        }
        if (null == list || list.isEmpty())
        {
            list = new ArrayList<T>();
        }
        return list;
    }

    public static <T> List<T> getSelectItem(Context context, Class<T> c, String firsttype, String secondType)
    {
        InitDbutils(context);
        List<T> list = null;
        try
        {
            list = db.findAll(Selector.from(c).where("firsttype", "=", firsttype).and("secondType", "=", secondType));
        } catch (DbException e)
        {
            e.printStackTrace();
        }
        if (null == list || list.isEmpty())
        {
            list = new ArrayList<T>();
        }
        return list;
    }

    public static <T> List<T> getSelectItemByFirstType(Context context, Class<T> c, String firsttype)
    {
        InitDbutils(context);
        List<T> list = null;
        try
        {
            list = db.findAll(Selector.from(c).where("firsttype", "=", firsttype));
        } catch (DbException e)
        {
            e.printStackTrace();
        }
        if (null == list || list.isEmpty())
        {
            list = new ArrayList<T>();
        }
        return list;
    }

    public static <T> List<T> getUserList(Context context, Class<T> c)
    {
        InitDbutils(context);
        List<T> list = null;
        try
        {
            list = db.findAll(Selector.from(c).where("onceUsed", "=", "1"));
        } catch (DbException e)
        {
            e.printStackTrace();
        }
        if (null == list || list.isEmpty())
        {
            list = new ArrayList<T>();
        }
        return list;
    }

    public static <T> Object getCurrentUser(Context context, Class<T> c)
    {
        InitDbutils(context);
        Object obj = null;
        try
        {
            obj = db.findFirst(Selector.from(c).where("isLogin", "=", "1"));
        } catch (DbException e)
        {
            e.printStackTrace();
        }
        return obj;
    }

    public static <T> Object getSalt(Context context, Class<T> c, String user_name)
    {
        InitDbutils(context);
        Object obj = null;
        try
        {
            obj = db.findFirst(Selector.from(c).where("user_name", "=", user_name));
        } catch (DbException e)
        {
            e.printStackTrace();
        }
        return obj;
    }

    public static <T> Object getBHQ_XHQK(Context context, Class<T> c, String XHID)
    {
        InitDbutils(context);
        Object obj = null;
        try
        {
            obj = db.findFirst(Selector.from(c).where("XHID", "=", XHID));
        } catch (DbException e)
        {
            e.printStackTrace();
        }
        return obj;
    }

    public static <T> Object login(Context context, Class<T> c, String userName, String password)
    {
        InitDbutils(context);
        Object obj = null;
        try
        {
            obj = db.findFirst(Selector.from(c).where("user_name", "=", userName).and("password", "=", password));
        } catch (DbException e)
        {
            String aa = e.getMessage();
            e.printStackTrace();
        }
        return obj;
    }

    public static <T> Object getAutoLoginUser(Context context, Class<T> c)
    {
        InitDbutils(context);
        Object obj = null;
        try
        {
            obj = db.findFirst(Selector.from(c).where("AutoLogin", "=", "1"));
        } catch (DbException e)
        {
            e.printStackTrace();
        }
        return obj;
    }

    public static <T> Object getZYDbyID(Context context, Class<T> c, String did)
    {
        InitDbutils(context);
        Object obj = null;
        try
        {
            obj = db.findFirst(Selector.from(c).where("LX", "=", "ZYD").and("DID", "=", did));
        } catch (DbException e)
        {
            e.printStackTrace();
        }
        return obj;
    }

    public static <T> Object getZSNR(Context context, Class<T> c, String ZSID)
    {
        InitDbutils(context);
        Object obj = null;
        try
        {
            obj = db.findFirst(Selector.from(c).where("ZSID", "=", ZSID));
        } catch (DbException e)
        {
            String aa = e.getMessage();
            e.printStackTrace();
        }
        return obj;
    }

    public static void existSystem(Context context, dt_manager dt_manager)// 这个方式可以
    {
        InitDbutils(context);
        try
        {
            db.update(dt_manager, "password");
        } catch (DbException e)
        {
            e.printStackTrace();
        }
    }

    public static void existSystemoffline(Context context, dt_manager_offline dt_manager_offline)// 这个方式可以
    {
        InitDbutils(context);
        try
        {
            db.update(dt_manager_offline, "AutoLogin", "isLogin");
        } catch (DbException e)
        {
            e.printStackTrace();
        }
    }

    public static boolean updateZSNR(Context context, BHQ_ZSK bhq_ZSK)// 这个方式可以
    {
        InitDbutils(context);
        try
        {
            db.update(bhq_ZSK, "ZSNR");
        } catch (DbException e)
        {
            String aa = e.getMessage();
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean deleteXXCJ(Context context, BHQ_XHSJCJ bhq_XHSJCJ)// 这个方式可以
    {
        InitDbutils(context);
        try
        {
            db.update(bhq_XHSJCJ, "SFSC");
        } catch (DbException e)
        {
            String aa = e.getMessage();
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean deleteXHSJ(Context context, BHQ_XHSJ bhq_XHSJ)// 这个方式可以
    {
        InitDbutils(context);
        try
        {
            db.update(bhq_XHSJ, "SFSC");
        } catch (DbException e)
        {
            String aa = e.getMessage();
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean deleteFJ(Context context, FJ_SCFJ fj_SCFJ)// 这个方式可以
    {
        InitDbutils(context);
        try
        {
            db.update(fj_SCFJ, "SFSC");
        } catch (DbException e)
        {
            String aa = e.getMessage();
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean deleteHaveUploadDAta(Context context)// 这个方式可以
    {
        InitDbutils(context);
        try
        {
            db.delete(FJ_SCFJ.class, WhereBuilder.b("ISUPLOAD", "=", 1));
            db.delete(BHQ_XHSJCJ.class, WhereBuilder.b("IsUpload", "=", 1));
            db.delete(BHQ_XHSJ.class, WhereBuilder.b("IsUpload", "=", 1));
            db.delete(BHQ_XHQK.class, WhereBuilder.b("IsUpload", "=", 1));
            db.delete(BHQ_XHQK_GJ.class, WhereBuilder.b("IsUpload", "=", 1));
            db.delete(BHQ_XHQK_ZTCZ.class, WhereBuilder.b("IsUpload", "=", 1));
            db.delete(FJ_SCFJ.class, WhereBuilder.b("ISUPLOAD", "=", 1));
        } catch (DbException e)
        {
            String aa = e.getMessage();
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean MarkHasUpload(Context context, Object obj)// 这个方式可以
    {
        InitDbutils(context);
        try
        {
            db.update(obj, "IsUpload");
        } catch (DbException e)
        {
            String aa = e.getMessage();
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean deletePhotos(Context context, Object obj)// 这个方式可以
    {
        InitDbutils(context);
        try
        {
            db.update(obj, "SFSC", "ISUPLOAD");
        } catch (DbException e)
        {
            String aa = e.getMessage();
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean changePassword(Context context, Object obj)// 这个方式可以
    {
        InitDbutils(context);
        try
        {
            db.update(obj, "password", "IsUpload");
        } catch (DbException e)
        {
            String aa = e.getMessage();
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean deleteCJXX(Context context, Object obj)// 这个方式可以
    {
        InitDbutils(context);
        try
        {
            db.update(obj, "SFSC", "ISUPLOAD");
        } catch (DbException e)
        {
            String aa = e.getMessage();
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean setRenWuComplete_ZRR(Context context, Object obj, String RWID, String RYID)// 这个方式可以
    {
        InitDbutils(context);
        try
        {
            db.update(obj, WhereBuilder.b("RWID", "=", RWID), "RWSFJS", "RWJSSJ", "XGSJ", "IsUpload");
        } catch (DbException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean setRenWuComplete(Context context, Object obj, String RWID, String RYID)// 这个方式可以
    {
        InitDbutils(context);
        try
        {
            RW_RW RW_RW = db.findFirst(Selector.from(RW_RW.class).where("RWID", "=", RWID));
            db.update(obj, WhereBuilder.b("RWID", "=", RWID).and("RYID", "=", RYID), "SFWC", "WCSJ", "XGSJ", "IsUpload");
            RW_RW RW_RW1 = db.findFirst(Selector.from(RW_RW.class).where("RWID", "=", RWID));
            RW_RW RW_RW2 = db.findFirst(Selector.from(RW_RW.class).where("RWID", "=", RWID));
        } catch (DbException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static String getAllGJ()
    {
        StringBuilder builder = new StringBuilder();
        String StrSql = "SELECT * FROM BHQ_XHQK_GJ where IsUpload = 0 ";
        Cursor cc = null;
        try
        {
            cc = db.execQuery(StrSql);
        } catch (DbException e)
        {
            e.printStackTrace();
        }
        if (cc != null)//已存在
        {
            builder.append("[");
            int count = cc.getCount();
            for (int i = 0; i < count; i++)
            {
                builder.append("{");
                cc.moveToPosition(i);
                int ColumnCount = cc.getColumnCount();
                for (int j = 0; j < ColumnCount; j++)
                {
                    String ColumnName = cc.getColumnName(j);
                    String ColumnValue = cc.getString(j);
                    builder.append("\"" + ColumnName + "\"" + ":" + "\"" + ColumnValue + "\"" + ",");
                }
                builder.replace(builder.length() - 1, builder.length(), "");
                builder.append("},");
            }
            builder.replace(builder.length() - 1, builder.length(), "");
            builder.append("]");
        } else //不存在
        {

        }
        db.close();
        return builder.toString();
    }

    public static <T> List<T> getNotUploadData(Context context, Class<T> c)
    {
        InitDbutils(context);
        List<T> list = null;
        try
        {
            list = db.findAll(Selector.from(c).where("IsUpload", "=", "0"));
        } catch (DbException e)
        {
            e.printStackTrace();
        }
        if (null == list || list.isEmpty())
        {
            list = new ArrayList<T>();
        }
        return list;
    }

    public static <T> List<T> getNotUploadData_GJ_Limit(Context context, Class<T> c)
    {
        InitDbutils(context);
        List<T> list = null;
        try
        {
            list = db.findAll(Selector.from(c).where("IsUpload", "=", "0").limit(500));
        } catch (DbException e)
        {
            e.printStackTrace();
        }
        if (null == list || list.isEmpty())
        {
            list = new ArrayList<T>();
        }
        return list;
    }

    public static int getNotUploadData_Size_GJ(Context context)
    {
        InitDbutils(context);
        StringBuffer builder = new StringBuffer();
        try
        {
            boolean isexist = db.tableIsExist(BHQ_XHQK_GJ.class);
            if (isexist)
            {
                String sql = "SELECT  COUNT(*) AS COUNT FROM BHQ_XHQK_GJ  WHERE IsUpload = ?";
                String args = "0";
                Cursor mCursor = sqLiteDatabase.rawQuery(sql, new String[]{args});
                if (mCursor != null)
                {
                    mCursor.moveToFirst();
                    int size = mCursor.getInt(mCursor.getColumnIndex("COUNT"));
                    return size;
                } else
                {
                    return 0;
                }
            } else
            {
                return 0;
            }

        } catch (JSONException e)
        {
            String a = e.getMessage();
            e.printStackTrace();
        } catch (DbException e)
        {
            e.printStackTrace();
        }

        return 0;
    }

    public static int getNotUploadData_Size(Context context)
    {
        InitDbutils(context);
        boolean isexist = false;
        String sql = "";
        Cursor mCursor = null;
        int allsize = 0;
        InitDbutils(context);
        StringBuffer builder = new StringBuffer();
        try
        {
            //用户表未同步数据
            isexist = db.tableIsExist(dt_manager_offline.class);
            if (isexist)
            {
                sql = "SELECT  COUNT(*) AS COUNT FROM dt_manager_offline  WHERE IsUpload = ?";
                mCursor = sqLiteDatabase.rawQuery(sql, new String[]{"0"});
                if (mCursor != null)
                {
                    mCursor.moveToFirst();
                    int size = mCursor.getInt(mCursor.getColumnIndex("COUNT"));
                    allsize = allsize + size;
                }
            }

            //状态表未同步数据
            isexist = db.tableIsExist(BHQ_XHQK_ZTCZ.class);
            if (isexist)
            {
                sql = "SELECT  COUNT(*) AS COUNT FROM BHQ_XHQK_ZTCZ  WHERE IsUpload = ?";
                mCursor = sqLiteDatabase.rawQuery(sql, new String[]{"0"});
                if (mCursor != null)
                {
                    mCursor.moveToFirst();
                    int size = mCursor.getInt(mCursor.getColumnIndex("COUNT"));
                    allsize = allsize + size;
                }
            }

            //信息采集表未同步数据
            isexist = db.tableIsExist(BHQ_XHSJCJ.class);
            if (isexist)
            {
                sql = "SELECT  COUNT(*) AS COUNT FROM BHQ_XHSJCJ  WHERE IsUpload = ?";
                mCursor = sqLiteDatabase.rawQuery(sql, new String[]{"0"});
                if (mCursor != null)
                {
                    mCursor.moveToFirst();
                    int size = mCursor.getInt(mCursor.getColumnIndex("COUNT"));
                    allsize = allsize + size;
                }
            }

            //事件表未同步数据
            isexist = db.tableIsExist(BHQ_XHSJ.class);
            if (isexist)
            {
                sql = "SELECT  COUNT(*) AS COUNT FROM BHQ_XHSJ  WHERE IsUpload = ?";
                mCursor = sqLiteDatabase.rawQuery(sql, new String[]{"0"});
                if (mCursor != null)
                {
                    mCursor.moveToFirst();
                    int size = mCursor.getInt(mCursor.getColumnIndex("COUNT"));
                    allsize = allsize + size;
                }
            }

            //情况表未同步数据
            isexist = db.tableIsExist(BHQ_XHQK.class);
            if (isexist)
            {
                sql = "SELECT  COUNT(*) AS COUNT FROM BHQ_XHQK  WHERE IsUpload = ?";
                mCursor = sqLiteDatabase.rawQuery(sql, new String[]{"0"});
                if (mCursor != null)
                {
                    mCursor.moveToFirst();
                    int size = mCursor.getInt(mCursor.getColumnIndex("COUNT"));
                    allsize = allsize + size;
                }
            }

            //轨迹表未同步数据
            isexist = db.tableIsExist(BHQ_XHQK_GJ.class);
            if (isexist)
            {
                sql = "SELECT  COUNT(*) AS COUNT FROM BHQ_XHQK_GJ  WHERE IsUpload = ?";
                mCursor = sqLiteDatabase.rawQuery(sql, new String[]{"0"});
                if (mCursor != null)
                {
                    mCursor.moveToFirst();
                    int size = mCursor.getInt(mCursor.getColumnIndex("COUNT"));
                    allsize = allsize + size;
                }
            }

            //附件表未同步数据
            isexist = db.tableIsExist(FJ_SCFJ.class);
            if (isexist)
            {
                sql = "SELECT  COUNT(*) AS COUNT FROM FJ_SCFJ  WHERE IsUpload = ?";
                mCursor = sqLiteDatabase.rawQuery(sql, new String[]{"0"});
                if (mCursor != null)
                {
                    mCursor.moveToFirst();
                    int size = mCursor.getInt(mCursor.getColumnIndex("COUNT"));
                    allsize = allsize + size;
                }
            }

            //任务人员表未同步数据
            isexist = db.tableIsExist(RW_CYR.class);
            if (isexist)
            {
                sql = "SELECT  COUNT(*) AS COUNT FROM RW_CYR  WHERE IsUpload = ?";
                mCursor = sqLiteDatabase.rawQuery(sql, new String[]{"0"});
                if (mCursor != null)
                {
                    mCursor.moveToFirst();
                    int size = mCursor.getInt(mCursor.getColumnIndex("COUNT"));
                    allsize = allsize + size;
                }
            }

            //任务表未同步数据
            isexist = db.tableIsExist(RW_RW.class);
            if (isexist)
            {
                sql = "SELECT  COUNT(*) AS COUNT FROM RW_RW  WHERE IsUpload = ?";
                mCursor = sqLiteDatabase.rawQuery(sql, new String[]{"0"});
                if (mCursor != null)
                {
                    mCursor.moveToFirst();
                    int size = mCursor.getInt(mCursor.getColumnIndex("COUNT"));
                    allsize = allsize + size;
                }
            }

        } catch (JSONException e)
        {
            String a = e.getMessage();
            e.printStackTrace();
        } catch (DbException e)
        {
            e.printStackTrace();
        }

        return allsize;
    }

    public static String getNotUploadData_GJ(Context context)
    {
        InitDbutils(context);
        StringBuffer builder = new StringBuffer();
        try
        {
            String sql = "SELECT  * FROM BHQ_XHQK_GJ  WHERE IsUpload = ?";
            String args = "0";
            Cursor mCursor = sqLiteDatabase.rawQuery(sql, new String[]{args});
            if (mCursor != null)
            {
                mCursor.moveToFirst();
                builder.append("[");
                while (mCursor.isAfterLast() == false)
                {
                    builder.append("{" + "\"" + "GJID" + "\"" + ":" + mCursor.getString(mCursor.getColumnIndex("GJID")) + "," + "\"" + "XHID" + "\"" + ":" + mCursor.getString(mCursor.getColumnIndex("XHID")) + "," + "\"" + "X" + "\"" + ":" + mCursor.getString(mCursor.getColumnIndex("X")) + "," + "\"" + "Y" + "\"" + ":" + mCursor.getString(mCursor.getColumnIndex("Y")) + "," + "\"" + "JLSJ" + "\"" + ":" + mCursor.getString(mCursor.getColumnIndex("JLSJ")) + "," + "\"" + "IsUpload" + "\"" + ":" + mCursor.getString(mCursor.getColumnIndex("IsUpload")) + "," + "\"" + "intervaldistance" + "\"" + ":" + mCursor.getString(mCursor.getColumnIndex("intervaldistance")) + "," + "\"" + "altitude" + "\"" + ":" + mCursor.getString(mCursor.getColumnIndex("altitude")) + "," + "accuracy" + "\"" + ":" + mCursor.getString(mCursor.getColumnIndex("accuracy")) + "," + "bearing" + "\"" + ":" + mCursor.getString(mCursor.getColumnIndex("bearing")) + "," + "speed" + "\"" + ":" + mCursor.getString(mCursor.getColumnIndex("speed")) + "},");
                    mCursor.moveToNext();
                }
                builder.replace(0, builder.length() - 1, "");
                builder.append("]");
            } else
            {
                return "";
            }
        } catch (JSONException e)
        {
            String a = e.getMessage();
            e.printStackTrace();
        }

        return builder.toString();
    }

    public static BHQ_XHXL getXHLX(Context context, String XLID)
    {
        InitDbutils(context);
        BHQ_XHXL bhq_xhxl = null;
        try
        {
            bhq_xhxl = db.findFirst(Selector.from(BHQ_XHXL.class).where("XLID", "=", XLID).and("XXZT", "=", "0"));
        } catch (DbException e)
        {
            e.printStackTrace();
        }

        return bhq_xhxl;
    }

    public static List<BHQ_XHXL_GJ> getXHXL_GJ(Context context, String XLID)
    {
        InitDbutils(context);
        List<BHQ_XHXL_GJ> list = null;
        try
        {
            list = db.findAll(Selector.from(BHQ_XHXL_GJ.class).where("XLID", "=", XLID).and("XXZT", "=", "0"));
            if (list == null)
            {
                list = new ArrayList<>();
            }
        } catch (DbException e)
        {
            e.printStackTrace();
        }

        return list;
    }

    public static List<String> getAllXHLXID(Context context, String userid)
    {
        InitDbutils(context);
        List<String> list_xlid = new ArrayList<>();
        try
        {
            BQH_XHRY bqh_xhry = db.findFirst(Selector.from(BQH_XHRY.class).where("XHRYID", "=", userid));
            String[] rxl = new String[]{};
            String[] zxl = new String[]{};
            String[] yxl = new String[]{};
            if (bqh_xhry != null)
            {
                String str_rxl = bqh_xhry.getXHRXL();
                String str_zxl = bqh_xhry.getXHZXL();
                String str_yxl = bqh_xhry.getXHYXL();

                if (!str_rxl.equals(""))
                {
                    rxl = bqh_xhry.getXHRXL().split(",");
                }
                if (!str_zxl.equals(""))
                {
                    zxl = bqh_xhry.getXHZXL().split(",");
                }
                if (!str_yxl.equals(""))
                {
                    yxl = bqh_xhry.getXHYXL().split(",");
                }

                for (int i = 0; i < rxl.length; i++)
                {
                    list_xlid.add(rxl[i]);
                }
                for (int i = 0; i < zxl.length; i++)
                {
                    list_xlid.add(zxl[i]);
                }
                for (int i = 0; i < yxl.length; i++)
                {
                    list_xlid.add(yxl[i]);
                }

            }

        } catch (DbException e)
        {
            e.printStackTrace();
        }

        return list_xlid;
    }

    public static <T> List<T> getBHQ_XHQKList(Context context, Class<T> c, String XHRY)
    {
        InitDbutils(context);
        List<T> list = null;
        try
        {
            list = db.findAll(Selector.from(c).where("XHRY", "=", XHRY).orderBy("XHKSSJ", true));
        } catch (DbException e)
        {
            String aa = e.getMessage();
            e.printStackTrace();
        }
        if (null == list || list.isEmpty())
        {
            list = new ArrayList<T>();
        }
        return list;
    }

    public static <T> List<T> GetBHQ_XHQK_GJList(Context context, Class<T> c, String XHID)
    {
        InitDbutils(context);
        List<T> list = null;
        try
        {
            list = db.findAll(Selector.from(c).where("XHID", "=", XHID).orderBy("JLSJ", false));
        } catch (DbException e)
        {
            e.printStackTrace();
        }
        if (null == list || list.isEmpty())
        {
            list = new ArrayList<T>();
        }
        return list;
    }

    public static <T> List<T> getKnowledgeList(Context context, Class<T> c, String ZSDL)
    {
        InitDbutils(context);
        List<T> list = null;
        try
        {
            list = db.findAll(Selector.from(c).where("ZSDL", "=", ZSDL).and("XXZT", "=", "0").orderBy("CJSJ", true));
        } catch (DbException e)
        {
            e.printStackTrace();
        }
        if (null == list || list.isEmpty())
        {
            list = new ArrayList<T>();
        }
        return list;
    }

    public static <T> List<T> getCJXXList(Context context, Class<T> c, String CJR, String ZYDL)
    {
        InitDbutils(context);
        List<T> list = null;
        try
        {
            list = db.findAll(Selector.from(c).where("CJR", "=", CJR).and("ZYDL", "=", ZYDL).and("SFSC", "=", 0).orderBy("CJSJ", true));
        } catch (DbException e)
        {
            e.printStackTrace();
        }
        if (null == list || list.isEmpty())
        {
            list = new ArrayList<T>();
        }
        return list;
    }

    public static <T> String[] getSLXBL(Context context, Class<T> c, String needfields, String fields, String values)
    {
        String[] str = null;
        InitDbutils(context);
        List<DbModel> list = null;
        try
        {
            list = db.findDbModelAll(Selector.from(c).select("distinct " + needfields).where(fields, "=", values));
        } catch (DbException e)
        {
            e.printStackTrace();
        }
        if (list != null)
        {
            str = new String[list.size()];
            for (int i = 0; i < list.size(); i++)
            {
                String a= list.get(i).getString(needfields).toString();
                str[i] = list.get(i).getString(needfields).toString();
            }
        }
        return str;
    }

    public static <T> List<T> getDics(Context context, Class<T> c)
    {
        InitDbutils(context);
        List<T> list = null;
        try
        {
            list = db.findAll(Selector.from(c));
        } catch (DbException e)
        {
            e.printStackTrace();
        }
        if (null == list || list.isEmpty())
        {
            list = new ArrayList<T>();
        }
        return list;
    }

    public static <T> List<T> getFJ_SCFJList(Context context, Class<T> c, String GLID, String FJLX)
    {
        InitDbutils(context);
        List<T> list = null;
        try
        {
            list = db.findAll(Selector.from(c).where("GLID", "=", GLID).and("FJLX", "=", FJLX).and("SFSC", "=", "0"));
        } catch (DbException e)
        {
            e.printStackTrace();
        }
        if (null == list || list.isEmpty())
        {
            list = new ArrayList<T>();
        }
        return list;
    }

    public static <T> List<T> getAllFJ_SCFJ_HaveUpload(Context context)
    {
        InitDbutils(context);
        List<T> list = null;
        try
        {
            list = db.findAll(Selector.from(FJ_SCFJ.class).where("ISUPLOAD", "=", 1));
        } catch (DbException e)
        {
            e.printStackTrace();
        }
        if (null == list || list.isEmpty())
        {
            list = new ArrayList<T>();
        }
        return list;
    }

    public static <T> List<T> getXBList(Context context, Class<T> c)
    {
        InitDbutils(context);
        List<T> list = null;
        try
        {
            list = db.findAll(Selector.from(c).orderBy("DCSJ", true));
        } catch (DbException e)
        {
            e.printStackTrace();
        }
        if (null == list || list.isEmpty())
        {
            list = new ArrayList<T>();
        }
        return list;
    }

    public static <T> List<T> getEventList(Context context, Class<T> c, String SBR)
    {
        InitDbutils(context);
        List<T> list = null;
        try
        {
            list = db.findAll(Selector.from(c).where("SBR", "=", SBR).and("SFSC", "=", "0").orderBy("SBSJ", true));
        } catch (DbException e)
        {
            e.printStackTrace();
        }
        if (null == list || list.isEmpty())
        {
            list = new ArrayList<T>();
        }
        return list;
    }

    // public static <T> List<T> getTaskList(Context context, Class<T> c, String
    // RYID)
    // {
    // DbUtils db = DbUtils.create(context);
    // List<T> list = null;
    // List<RW_CYR> list_RW_CYR = null;
    // try
    // {
    // SELECT * FROM RW_RW LEFT
    // OUTER JOIN RW_CYR ON RW_RW.RWID = RW_CYR.RWID
    // WHERE RYID=#RYID# AND RW_CYR.SFSC=0 and HYSFQX=0 or ZRR =#RYID# ORDER BY
    // RWMC ASC
    // list_RW_CYR = db.findAll(Selector.from(RW_CYR.class).where("RYID", "=",
    // RYID));
    // } catch (DbException e)
    // {
    // e.printStackTrace();
    // }
    // if (null == list || list.isEmpty())
    // {
    // list = new ArrayList<T>();
    // }
    // return list;
    // }
    public static List<RW_RW> getCompleteRenWuByRYID(Context context, String RYID)
    {
        InitDbutils(context);
        List<DbModel> dbModels = null;
        try
        {
            String sql = "SELECT  * FROM RW_RW LEFT OUTER JOIN RW_CYR ON RW_RW.RWID = RW_CYR.RWID WHERE RYID = " + RYID + " AND RW_CYR.SFSC='false' and RW_CYR.SFWC='true' and HYSFQX='false' ORDER BY XGSJ desc";
            SqlInfo sqlInfo = new SqlInfo(sql);
            dbModels = db.findDbModelAll(sqlInfo); // 自定义sql查询
            if (dbModels == null)
            {
                dbModels = new ArrayList<DbModel>();
            }
        } catch (DbException e)
        {
            e.printStackTrace();
            List<RW_RW> list = new ArrayList<RW_RW>();
            return list;
        }
        List<RW_RW> list = new ArrayList<RW_RW>();
        for (int i = 0; i < dbModels.size(); i++)
        {
            RW_RW rw_RW = new RW_RW();
            rw_RW.setCJR(dbModels.get(i).getString("CJR"));
            rw_RW.setCJRXM(dbModels.get(i).getString("CJRXM"));
            rw_RW.setCJSJ(dbModels.get(i).getString("CJSJ"));
            rw_RW.setHYQXSJ(dbModels.get(i).getString("HYQXSJ"));
            rw_RW.setHYSFQX(dbModels.get(i).getString("HYSFQX"));
            rw_RW.setQXR(dbModels.get(i).getString("QXR"));
            rw_RW.setQXRXM(dbModels.get(i).getString("QXRXM"));
            rw_RW.setRWID(dbModels.get(i).getString("RWID"));
            rw_RW.setRWJSSJ(dbModels.get(i).getString("RWJSSJ"));
            rw_RW.setRWMC(dbModels.get(i).getString("RWMC"));
            rw_RW.setRWMS(dbModels.get(i).getString("RWMS"));
            rw_RW.setRWSFJS(dbModels.get(i).getString("RWSFJS"));
            rw_RW.setSFSC(dbModels.get(i).getString("SFSC"));
            rw_RW.setWRJZSJ(dbModels.get(i).getString("WRJZSJ"));
            rw_RW.setWRKSSJ(dbModels.get(i).getString("WRKSSJ"));
            rw_RW.setWRTXSJ(dbModels.get(i).getString("WRTXSJ"));
            rw_RW.setXGR(dbModels.get(i).getString("XGR"));
            rw_RW.setXGRXM(dbModels.get(i).getString("XGRXM"));
            rw_RW.setXGSJ(dbModels.get(i).getString("XGSJ"));
            rw_RW.setZCRXM(dbModels.get(i).getString("ZCRXM"));
            rw_RW.setZRR(dbModels.get(i).getString("ZRR"));
            rw_RW.setZYD(dbModels.get(i).getString("ZYD"));
            rw_RW.setIsRead(dbModels.get(i).getString("IsRead"));
            list.add(rw_RW);
        }

        try
        {
            String sql = "SELECT  * FROM RW_RW WHERE ZRR = " + RYID + " AND SFSC='false' and RWSFJS='true' and HYSFQX='false' ORDER BY XGSJ desc";
            SqlInfo sqlInfo = new SqlInfo(sql);
            dbModels = db.findDbModelAll(sqlInfo); // 自定义sql查询
            if (dbModels == null)
            {
                dbModels = new ArrayList<DbModel>();
            }
        } catch (DbException e)
        {
            e.printStackTrace();
            return list;
        }

        for (int i = 0; i < dbModels.size(); i++)
        {
            RW_RW rw_RW = new RW_RW();
            rw_RW.setCJR(dbModels.get(i).getString("CJR"));
            rw_RW.setCJRXM(dbModels.get(i).getString("CJRXM"));
            rw_RW.setCJSJ(dbModels.get(i).getString("CJSJ"));
            rw_RW.setHYQXSJ(dbModels.get(i).getString("HYQXSJ"));
            rw_RW.setHYSFQX(dbModels.get(i).getString("HYSFQX"));
            rw_RW.setQXR(dbModels.get(i).getString("QXR"));
            rw_RW.setQXRXM(dbModels.get(i).getString("QXRXM"));
            rw_RW.setRWID(dbModels.get(i).getString("RWID"));
            rw_RW.setRWJSSJ(dbModels.get(i).getString("RWJSSJ"));
            rw_RW.setRWMC(dbModels.get(i).getString("RWMC"));
            rw_RW.setRWMS(dbModels.get(i).getString("RWMS"));
            rw_RW.setRWSFJS(dbModels.get(i).getString("RWSFJS"));
            rw_RW.setSFSC(dbModels.get(i).getString("SFSC"));
            rw_RW.setWRJZSJ(dbModels.get(i).getString("WRJZSJ"));
            rw_RW.setWRKSSJ(dbModels.get(i).getString("WRKSSJ"));
            rw_RW.setWRTXSJ(dbModels.get(i).getString("WRTXSJ"));
            rw_RW.setXGR(dbModels.get(i).getString("XGR"));
            rw_RW.setXGRXM(dbModels.get(i).getString("XGRXM"));
            rw_RW.setXGSJ(dbModels.get(i).getString("XGSJ"));
            rw_RW.setZCRXM(dbModels.get(i).getString("ZCRXM"));
            rw_RW.setZRR(dbModels.get(i).getString("ZRR"));
            rw_RW.setZYD(dbModels.get(i).getString("ZYD"));
            rw_RW.setIsRead(dbModels.get(i).getString("IsRead"));
            list.add(rw_RW);
        }
        return list;
    }

    public static List<RW_RW> getRenWuByRYID(Context context, String RYID)
    {
        InitDbutils(context);
        List<DbModel> dbModels = null;
        try
        {
            String sql = "SELECT  * FROM RW_RW LEFT OUTER JOIN RW_CYR ON RW_RW.RWID = RW_CYR.RWID WHERE RYID = " + RYID + " AND RW_CYR.SFSC='false' and RW_CYR.SFWC='false' and HYSFQX='false' ORDER BY XGSJ desc";
            SqlInfo sqlInfo = new SqlInfo(sql);
            dbModels = db.findDbModelAll(sqlInfo); // 自定义sql查询
            if (dbModels == null)
            {
                dbModels = new ArrayList<DbModel>();
            }
        } catch (DbException e)
        {
            e.printStackTrace();
            List<RW_RW> list = new ArrayList<RW_RW>();
            return list;
        }
        List<RW_RW> list = new ArrayList<RW_RW>();
        for (int i = 0; i < dbModels.size(); i++)
        {
            RW_RW rw_RW = new RW_RW();
            rw_RW.setCJR(dbModels.get(i).getString("CJR"));
            rw_RW.setCJRXM(dbModels.get(i).getString("CJRXM"));
            rw_RW.setCJSJ(dbModels.get(i).getString("CJSJ"));
            rw_RW.setHYQXSJ(dbModels.get(i).getString("HYQXSJ"));
            rw_RW.setHYSFQX(dbModels.get(i).getString("HYSFQX"));
            rw_RW.setQXR(dbModels.get(i).getString("QXR"));
            rw_RW.setQXRXM(dbModels.get(i).getString("QXRXM"));
            rw_RW.setRWID(dbModels.get(i).getString("RWID"));
            rw_RW.setRWJSSJ(dbModels.get(i).getString("RWJSSJ"));
            rw_RW.setRWMC(dbModels.get(i).getString("RWMC"));
            rw_RW.setRWMS(dbModels.get(i).getString("RWMS"));
            rw_RW.setRWSFJS(dbModels.get(i).getString("RWSFJS"));
            rw_RW.setSFSC(dbModels.get(i).getString("SFSC"));
            rw_RW.setWRJZSJ(dbModels.get(i).getString("WRJZSJ"));
            rw_RW.setWRKSSJ(dbModels.get(i).getString("WRKSSJ"));
            rw_RW.setWRTXSJ(dbModels.get(i).getString("WRTXSJ"));
            rw_RW.setXGR(dbModels.get(i).getString("XGR"));
            rw_RW.setXGRXM(dbModels.get(i).getString("XGRXM"));
            rw_RW.setXGSJ(dbModels.get(i).getString("XGSJ"));
            rw_RW.setZCRXM(dbModels.get(i).getString("ZCRXM"));
            rw_RW.setZRR(dbModels.get(i).getString("ZRR"));
            rw_RW.setZYD(dbModels.get(i).getString("ZYD"));
            rw_RW.setIsRead(dbModels.get(i).getString("IsRead"));
            list.add(rw_RW);
        }

        try
        {
            String sql = "SELECT  * FROM RW_RW WHERE ZRR = " + RYID + " AND SFSC='false' and  RWSFJS='false' and HYSFQX='false' ORDER BY XGSJ desc";
            SqlInfo sqlInfo = new SqlInfo(sql);
            dbModels = db.findDbModelAll(sqlInfo); // 自定义sql查询
            if (dbModels == null)
            {
                dbModels = new ArrayList<DbModel>();
            }
        } catch (DbException e)
        {
            e.printStackTrace();
            return list;
        }

        for (int i = 0; i < dbModels.size(); i++)
        {
            RW_RW rw_RW = new RW_RW();
            rw_RW.setCJR(dbModels.get(i).getString("CJR"));
            rw_RW.setCJRXM(dbModels.get(i).getString("CJRXM"));
            rw_RW.setCJSJ(dbModels.get(i).getString("CJSJ"));
            rw_RW.setHYQXSJ(dbModels.get(i).getString("HYQXSJ"));
            rw_RW.setHYSFQX(dbModels.get(i).getString("HYSFQX"));
            rw_RW.setQXR(dbModels.get(i).getString("QXR"));
            rw_RW.setQXRXM(dbModels.get(i).getString("QXRXM"));
            rw_RW.setRWID(dbModels.get(i).getString("RWID"));
            rw_RW.setRWJSSJ(dbModels.get(i).getString("RWJSSJ"));
            rw_RW.setRWMC(dbModels.get(i).getString("RWMC"));
            rw_RW.setRWMS(dbModels.get(i).getString("RWMS"));
            rw_RW.setRWSFJS(dbModels.get(i).getString("RWSFJS"));
            rw_RW.setSFSC(dbModels.get(i).getString("SFSC"));
            rw_RW.setWRJZSJ(dbModels.get(i).getString("WRJZSJ"));
            rw_RW.setWRKSSJ(dbModels.get(i).getString("WRKSSJ"));
            rw_RW.setWRTXSJ(dbModels.get(i).getString("WRTXSJ"));
            rw_RW.setXGR(dbModels.get(i).getString("XGR"));
            rw_RW.setXGRXM(dbModels.get(i).getString("XGRXM"));
            rw_RW.setXGSJ(dbModels.get(i).getString("XGSJ"));
            rw_RW.setZCRXM(dbModels.get(i).getString("ZCRXM"));
            rw_RW.setZRR(dbModels.get(i).getString("ZRR"));
            rw_RW.setZYD(dbModels.get(i).getString("ZYD"));
            rw_RW.setIsRead(dbModels.get(i).getString("IsRead"));
            list.add(rw_RW);
        }
        return list;
    }

    public static <T> List<T> getRW_CYRList(Context context, Class<T> c, String RWID)
    {
        InitDbutils(context);
        List<T> list = null;
        try
        {
            list = db.findAll(Selector.from(c).where("RWID", "=", RWID));
        } catch (DbException e)
        {
            e.printStackTrace();
        }
        if (null == list || list.isEmpty())
        {
            list = new ArrayList<T>();
        }
        return list;
    }

    public static <T> List<T> getYWCRWRY(Context context, Class<T> c, String RWID)
    {
        InitDbutils(context);
        List<T> list = null;
        try
        {
            list = db.findAll(Selector.from(c).where("RWID ", "=", RWID).and("SFWC", "=", "true").and("SFSC", "=", "false"));
        } catch (DbException e)
        {
            e.printStackTrace();
        }
        if (null == list || list.isEmpty())
        {
            list = new ArrayList<T>();
        }
        return list;
    }

    public static void updatedt_manager_offline(Context context, String table, String id, String values)
    {
        InitDbutils(context);
        ContentValues cv = new ContentValues();
        cv.put("BDLJ", values);
        sqLiteDatabase.update(table, cv, "BDLJ = ?", new String[]{id});
    }

    public static boolean deleteExceptionInfo(Context context, String exceptionid)
    {
        DbUtils db = DbUtils.create(context);
        try
        {
            db.delete(ExceptionInfo.class, WhereBuilder.b("exceptionid", "=", exceptionid));
        } catch (DbException e)
        {
            return false;
        }
        return true;
    }

    public static <T> List<T> getExceptionInfo(Context context)
    {
//        DbUtils db = DbUtils.create(context);
        InitDbutils(context);
        List<T> list = null;
        try
        {
            list = db.findAll(Selector.from(ExceptionInfo.class));
        } catch (DbException e)
        {
            e.printStackTrace();
        }
        return list;
    }

    public static void updateZSK(Context context, String table, String ZDID, String values)
    {
        InitDbutils(context);
        ContentValues cv = new ContentValues();
        cv.put("ZSNR", values);
        sqLiteDatabase.update(table, cv, "ZSID = ?", new String[]{ZDID});
    }

    public static void insertData(Context context, String classname, String[] columnnames, JSONArray jsonArray_Rows)
    {
        InitDbutils(context);
//        StringBuffer buff = new StringBuffer();;
//        for (int i = 0; i <columnnames.length; i++)
//        {
//            if (columnnames[i].equals(primarykey))
//            {
//                buff.append(columnnames[i]+" text primary key  not null"+",");
//            }else
//            {
//                buff.append(columnnames[i]+" text"+",");
//            }
//        }
//        buff.replace(buff.length() - 1, buff.length(), "");
//        String sql_createtable="create table  if not exists "+classname+"("+buff+")";
//        sqLiteDatabase.execSQL(sql_createtable);
        if (jsonArray_Rows.size() != 0)
        {
            StringBuffer columnn = new StringBuffer();
            ;
            for (int i = 0; i < columnnames.length; i++)
            {
                columnn.append(columnnames[i] + ",");
            }
            columnn.replace(columnn.length() - 1, columnn.length(), "");
            for (int i = 0; i < jsonArray_Rows.size(); i++)
            {
                StringBuffer values = new StringBuffer();
                for (int j = 0; j < columnnames.length; j++)
                {
                    values.append("\"" + jsonArray_Rows.getJSONArray(i).getString(j) + "\"" + ",");
                }
                values.replace(values.length() - 1, values.length(), "");
                String sql = "replace into " + classname + "(" + columnn + ")values(" + values + ")";
                try
                {
                    sqLiteDatabase.execSQL(sql);
                } catch (JSONException e)
                {
                    String a = e.getMessage();
                    e.printStackTrace();
                }
            }
        }
    }

    public static void insertUserData(Context context, String classname, String[] columnnames, JSONArray jsonArray_Rows)
    {
        InitDbutils(context);
        if (jsonArray_Rows.size() != 0)
        {
            StringBuffer columnn = new StringBuffer();
            for (int i = 0; i < columnnames.length; i++)
            {
                columnn.append(columnnames[i] + ",");
            }
            columnn.replace(columnn.length() - 1, columnn.length(), "");
            for (int i = 0; i < jsonArray_Rows.size(); i++)
            {
                StringBuffer values = new StringBuffer();
                String sql_count = "select count(*) from " + classname + " where id =?";
                String args = jsonArray_Rows.getJSONArray(i).getString(0);
                Cursor cursor = sqLiteDatabase.rawQuery("select count(*) from " + classname + " where id =?", new String[]{jsonArray_Rows.getJSONArray(i).getString(0)});
                cursor.moveToFirst();
                String c = cursor.getString(0);
                int count = Integer.valueOf(c);
                if (count > 0)//已经存在就暂不处理
                {
//                    for (int j = 0; j < columnnames.length; j++)
//                    {
//                        values.append(columnnames[j] + "=" + "\"" + jsonArray_Rows.getJSONArray(i).getString(j) + "\"" + ",");
//                    }
//                    values.replace(values.length() - 1, values.length(), "");
//                    String sql_update = "update " + classname + " set " + values + " where  RWCYID =" + "\"" + jsonArray_Rows.getJSONArray(i).getString(0) + "\"";
//                    sqLiteDatabase.execSQL(sql_update);
                } else
                {
                    for (int j = 0; j < columnnames.length; j++)
                    {
                        values.append("\"" + jsonArray_Rows.getJSONArray(i).getString(j) + "\"" + ",");
                    }
                    values.replace(values.length() - 1, values.length(), "");
                    String sql_insert = "insert into " + classname + "(" + columnn + ")values(" + values + ")";
                    sqLiteDatabase.execSQL(sql_insert);
                }
            }
        }
    }

    public static void insertRW_CYRData(Context context, String classname, String[] columnnames, JSONArray jsonArray_Rows)
    {
        InitDbutils(context);
        if (jsonArray_Rows.size() != 0)
        {
            StringBuffer columnn = new StringBuffer();
            ;
            for (int i = 0; i < columnnames.length; i++)
            {
                columnn.append(columnnames[i] + ",");
            }
            columnn.replace(columnn.length() - 1, columnn.length(), "");
            for (int i = 0; i < jsonArray_Rows.size(); i++)
            {
                StringBuffer values = new StringBuffer();
                String sql_count = "select count(*) from " + classname + " where RWCYID =?";
                String args = jsonArray_Rows.getJSONArray(i).getString(0);
                Cursor cursor = sqLiteDatabase.rawQuery("select count(*) from " + classname + " where RWCYID =?", new String[]{jsonArray_Rows.getJSONArray(i).getString(0)});
                cursor.moveToFirst();
                String c = cursor.getString(0);
                int count = Integer.valueOf(c);
                if (count > 0)
                {
                    for (int j = 0; j < columnnames.length; j++)
                    {
                        values.append(columnnames[j] + "=" + "\"" + jsonArray_Rows.getJSONArray(i).getString(j) + "\"" + ",");
                    }
                    values.replace(values.length() - 1, values.length(), "");
                    String sql_update = "update " + classname + " set " + values + " where  RWCYID =" + "\"" + jsonArray_Rows.getJSONArray(i).getString(0) + "\"";
                    sqLiteDatabase.execSQL(sql_update);
                } else
                {
                    for (int j = 0; j < columnnames.length; j++)
                    {
                        values.append("\"" + jsonArray_Rows.getJSONArray(i).getString(j) + "\"" + ",");
                    }
                    values.replace(values.length() - 1, values.length(), "");
                    String sql_insert = "insert into " + classname + "(" + columnn + ")values(" + values + ")";
                    sqLiteDatabase.execSQL(sql_insert);
                }
            }
        }
    }

    public static void insertRW_RWData(Context context, String classname, String[] columnnames, JSONArray jsonArray_Rows)
    {
        InitDbutils(context);
        if (jsonArray_Rows.size() != 0)
        {
            StringBuffer columnn = new StringBuffer();
            ;
            for (int i = 0; i < columnnames.length; i++)
            {
                columnn.append(columnnames[i] + ",");
            }
            columnn.replace(columnn.length() - 1, columnn.length(), "");
            for (int i = 0; i < jsonArray_Rows.size(); i++)
            {
                StringBuffer values = new StringBuffer();
                ;


                String sql_count = "select count(*) from " + classname + " where RWID =?";
                String args = jsonArray_Rows.getJSONArray(i).getString(0);
                Cursor cursor = sqLiteDatabase.rawQuery(sql_count, new String[]{args});
                cursor.moveToFirst();
                String c = cursor.getString(0);
                int count = Integer.valueOf(c);
                if (count > 0)
                {
                    for (int j = 0; j < columnnames.length; j++)
                    {
                        values.append(columnnames[j] + "=" + "\"" + jsonArray_Rows.getJSONArray(i).getString(j) + "\"" + ",");
                    }
                    values.replace(values.length() - 1, values.length(), "");
                    String sql_update = "update " + classname + " set " + values + " where  RWID =" + "\"" + jsonArray_Rows.getJSONArray(i).getString(0) + "\"";
                    sqLiteDatabase.execSQL(sql_update);
                } else
                {
                    for (int j = 0; j < columnnames.length; j++)
                    {
                        values.append("\"" + jsonArray_Rows.getJSONArray(i).getString(j) + "\"" + ",");
                    }
                    values.replace(values.length() - 1, values.length(), "");
                    String sql_insert = "insert into " + classname + "(" + columnn + ")values(" + values + ")";

                    try
                    {
                        sqLiteDatabase.execSQL(sql_insert);
                    } catch (JSONException e)
                    {
                        String a = e.getMessage();
                        e.printStackTrace();
                    }
                }
//                values.replace(values.length()-1,values.length(),"");
//                String sql="replace into "+ classname+"("+columnn+")values("+values+")";
//                try
//                {
//                    sqLiteDatabase.execSQL(sql);
//                } catch (JSONException e)
//                {
//                    String a=e.getMessage();
//                    e.printStackTrace();
//                }
            }
        }
    }

    public static void createTable(Context context)
    {
        InitDbutils(context);
        try
        {
            db.createTableIfNotExist(BHQ_ZSK.class);
            db.createTableIfNotExist(dt_manager_offline.class);
            db.createTableIfNotExist(RW_YQB.class);
            db.createTableIfNotExist(RW_RW.class);
            db.createTableIfNotExist(RW_CYR.class);
            db.createTableIfNotExist(Dictionary.class);
            db.createTableIfNotExist(BHQ_XHXL.class);
            db.createTableIfNotExist(BHQ_XHXL_GJ.class);
            db.createTableIfNotExist(BQH_XHRY.class);
            db.createTableIfNotExist(SLXBL.class);
        } catch (DbException e)
        {
            e.printStackTrace();
        }
//        String sql_BHQ_ZSK="create table  if not exists BHQ_ZSK(ZSID text primary key  not null,ZSBT text,ZSDL text,ZSXL text,imgurl text,ZSZY text,CJR text,CJSJ text,CJRXM text,XGR text,XGSJ text,XGRXM text,XXZT text,ZSNR text,BDLJ text)";
//        String sql_dt_manager_offline="create table  if not exists dt_manager_offline(id text primary key  not null,role_id text,role_type text,user_name text,password text,salt text,real_name text,telephone text,email text,is_lock text,add_time text,wxNum text,agentId text,reg_ip text,qq text,province text,city text,county text,remark text,sort_id text,agentLevel text,DepartId text,Phone text,Address text,UserType text,isPatrol text,UserPhoto text,Change text,SFSC text,XGSJ text,AutoLogin text,onceUsed text,BDLJ text,isLogin text,IsUpload text)";
//        String sql_RW_YQB="create table  if not exists RW_YQB(id text ,YQID text primary key  not null,RWID text,RWCYID text,YQSJ text,YQSM text,YQSQSJ text,SFPZ text,Change text)";
//        String sql_RW_RW="create table  if not exists RW_RW(RWID text primary key  not null,RWMC text,WRKSSJ text,WRJZSJ text,WRTXSJ text,ZYD text,ZRR text,ZCRXM text,RWMS text,HYSFQX text,HYQXSJ text,QXR text,QXRXM text,RWSFJS text,RWJSSJ text,CJSJ text,CJR text,CJRXM text,XGSJ text,XGR text,XGRXM text,SFSC text,Change text)";
//        String sql_RW_CYR="create table  if not exists RW_CYR(id text,RWCYID text primary key  not null,RWID text,RYID text,RYXM text,SFWC text,WCSJ text,SFYSQYQ text,XGSJ text,Change text,SFSC text)";
//        String sql_Dictionary="create table  if not exists Dictionary(id text ,DID text,NAME text,SORT text,LX text,PID text)";
//        sqLiteDatabase.execSQL(sql_BHQ_ZSK);
//        sqLiteDatabase.execSQL(sql_dt_manager_offline);
//        sqLiteDatabase.execSQL(sql_RW_YQB);
//        sqLiteDatabase.execSQL(sql_RW_RW);
//        sqLiteDatabase.execSQL(sql_RW_CYR);
//        sqLiteDatabase.execSQL(sql_Dictionary);
    }

    public static void updateRW_CYR(Context context, String RWCYID)// 这个方式可以
    {
        InitDbutils(context);
        String sql_update = "update RW_CYR  set IsUpload=1 where  RWCYID =" + "\"" + RWCYID + "\"";
        sqLiteDatabase.execSQL(sql_update);
    }

    public static void updateRW_RW(Context context, String RWID)// 这个方式可以
    {
        InitDbutils(context);
        String sql_update = "update RW_RW  set IsUpload=1 where  RWID =" + "\"" + RWID + "\"";
        sqLiteDatabase.execSQL(sql_update);
    }
}
