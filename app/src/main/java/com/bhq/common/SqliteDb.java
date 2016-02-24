package com.bhq.common;

import android.content.Context;

import com.bhq.bean.BHQ_XHSJ;
import com.bhq.bean.BHQ_XHSJCJ;
import com.bhq.bean.BHQ_ZSK;
import com.bhq.bean.FJ_SCFJ;
import com.bhq.bean.RW_RW;
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
    public static boolean save(Context context, Object obj)
    {
        DbUtils db = DbUtils.create(context);
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
        DbUtils db = DbUtils.create(context);
        try
        {
            db.update(obj, "salt","telephone", "real_name", "user_name", "password","UserType","DepartId","isPatrol","SFSC");
        } catch (DbException e)
        {
            e.printStackTrace();
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static <T> boolean deleteRecord(Context context, Class<T> c, String firsttype, String secondType, String thirdtype)
    {
        DbUtils db = DbUtils.create(context);
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
        DbUtils db = DbUtils.create(context);
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
        DbUtils db = DbUtils.create(context);
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
        DbUtils db = DbUtils.create(context);
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
        DbUtils db = DbUtils.create(context);
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
        DbUtils db = DbUtils.create(context);
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
        DbUtils db = DbUtils.create(context);
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
        DbUtils db = DbUtils.create(context);
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
        DbUtils db = DbUtils.create(context);
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
        DbUtils db = DbUtils.create(context);
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
        DbUtils db = DbUtils.create(context);
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
        DbUtils db = DbUtils.create(context);
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
        DbUtils db = DbUtils.create(context);
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
        DbUtils db = DbUtils.create(context);
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
        DbUtils db = DbUtils.create(context);
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

    public static <T> Object getZSNR(Context context, Class<T> c, String ZSID)
    {
        DbUtils db = DbUtils.create(context);
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
        DbUtils db = DbUtils.create(context);
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
        DbUtils db = DbUtils.create(context);
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
        DbUtils db = DbUtils.create(context);
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
        DbUtils db = DbUtils.create(context);
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
        DbUtils db = DbUtils.create(context);
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
        DbUtils db = DbUtils.create(context);
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

    public static boolean MarkHasUpload(Context context, Object obj)// 这个方式可以
    {
        DbUtils db = DbUtils.create(context);
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
        DbUtils db = DbUtils.create(context);
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
        DbUtils db = DbUtils.create(context);
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
        DbUtils db = DbUtils.create(context);
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

    public static boolean setRenWuComplete(Context context, Object obj, String RWID, String RYID)// 这个方式可以
    {
        DbUtils db = DbUtils.create(context);
        try
        {
            db.update(obj, WhereBuilder.b("RWID", "=", RWID).and("RYID", "=", RYID), "SFWC", "WCSJ", "XGSJ");
        } catch (DbException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static <T> List<T> getNotUploadData(Context context, Class<T> c)
    {
        DbUtils db = DbUtils.create(context);
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

    public static <T> List<T> getBHQ_XHQKList(Context context, Class<T> c, String XHRY)
    {
        DbUtils db = DbUtils.create(context);
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
        DbUtils db = DbUtils.create(context);
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
        DbUtils db = DbUtils.create(context);
        List<T> list = null;
        try
        {
            list = db.findAll(Selector.from(c).where("ZSDL", "=", ZSDL).orderBy("CJSJ", true));
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
        DbUtils db = DbUtils.create(context);
        List<T> list = null;
        try
        {
            list = db.findAll(Selector.from(c).where("CJR", "=", CJR).and("ZYDL", "=", ZYDL).and("SFSC", "=", 0).orderBy("CJSJ", false));
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

    public static <T> List<T> getDics(Context context, Class<T> c)
    {
        DbUtils db = DbUtils.create(context);
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
        DbUtils db = DbUtils.create(context);
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

    public static <T> List<T> getEventList(Context context, Class<T> c, String SBR)
    {
        DbUtils db = DbUtils.create(context);
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

    public static List<RW_RW> getRenWuByRYID(Context context, String RYID)
    {
        DbUtils db = DbUtils.create(context);
        List<DbModel> dbModels = null;
        try
        {
            String sql = "SELECT  * FROM RW_RW LEFT OUTER JOIN RW_CYR ON RW_RW.RWID = RW_CYR.RWID WHERE RYID = " + RYID + " AND RW_CYR.SFSC='false' and HYSFQX='false' ORDER BY RWMC ASC";
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
            list.add(rw_RW);
        }
        return list;
    }

    public static <T> List<T> getRW_CYRList(Context context, Class<T> c, String RWID)
    {
        DbUtils db = DbUtils.create(context);
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
        DbUtils db = DbUtils.create(context);
        List<T> list = null;
        try
        {
            list = db.findAll(Selector.from(c).where("RWID ", "=", RWID).and("SFWC", "=", 1).and("SFSC", "=", "false"));
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
}
