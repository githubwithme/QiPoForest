package com.bhq.common;

import android.database.sqlite.SQLiteDatabase;

import com.bhq.bean.BHQ_XHQK_GJ;
import com.bhq.bean.BHQ_XHXL_GJ;
import com.bhq.bean.RW_RW;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;

/**
 * Created by ${hmj} on 2016/3/16.
 */
public class CustomDbUpgradeListener implements  DbUtils.DbUpgradeListener
{

    @Override
    public void onUpgrade(DbUtils dbUtils, int i, int i1)
    {
        boolean tableexist=false;
        boolean columnexist=false;
        SQLiteDatabase sqLiteDatabase= dbUtils.getDatabase();
        try
        {
            tableexist=dbUtils.tableIsExist(BHQ_XHXL_GJ.class);//BHQ_XHXL_GJ表
            if (tableexist)
            {
                columnexist=SqliteDb.checkColumnExist1(sqLiteDatabase, "BHQ_XHXL_GJ", "XXZT");
                if (!columnexist)
                {
                    String sql = "alter table  BHQ_XHXL_GJ  add column XXZT NVARCHAR(10)";
                    sqLiteDatabase.execSQL(sql);
                }
            }
            tableexist=dbUtils.tableIsExist(RW_RW.class);//RW_RW表
            if (tableexist)
            {
                columnexist=SqliteDb.checkColumnExist1(sqLiteDatabase,"RW_RW","IsRead");
                if (!columnexist)
                {
                    String sql= "alter table  RW_RW  add column IsRead NVARCHAR(10)";
                    sqLiteDatabase.execSQL(sql);
                }
            }
            tableexist=dbUtils.tableIsExist(BHQ_XHQK_GJ.class);//BHQ_XHQK_GJ表
            if (tableexist)
            {
                columnexist=SqliteDb.checkColumnExist1(sqLiteDatabase,"BHQ_XHQK_GJ","altitude");
                if (!columnexist)
                {
                    String sql = "alter table  BHQ_XHQK_GJ  add column altitude NVARCHAR(10)";
                    sqLiteDatabase.execSQL(sql);
                }
            }
            tableexist=dbUtils.tableIsExist(BHQ_XHQK_GJ.class);//BHQ_XHQK_GJ表
            if (tableexist)
            {
                columnexist=SqliteDb.checkColumnExist1(sqLiteDatabase,"BHQ_XHQK_GJ","accuracy");
                if (!columnexist)
                {
                    String sql = "alter table  BHQ_XHQK_GJ  add column accuracy NVARCHAR(10)";
                    sqLiteDatabase.execSQL(sql);
                }
            }
            tableexist=dbUtils.tableIsExist(BHQ_XHQK_GJ.class);//BHQ_XHQK_GJ表
            if (tableexist)
            {
                columnexist=SqliteDb.checkColumnExist1(sqLiteDatabase,"BHQ_XHQK_GJ","bearing");
                if (!columnexist)
                {
                    String sql = "alter table  BHQ_XHQK_GJ  add column bearing NVARCHAR(10)";
                    sqLiteDatabase.execSQL(sql);
                }
            }
            tableexist=dbUtils.tableIsExist(BHQ_XHQK_GJ.class);//BHQ_XHQK_GJ表
            if (tableexist)
            {
                columnexist=SqliteDb.checkColumnExist1(sqLiteDatabase,"BHQ_XHQK_GJ","speed");
                if (!columnexist)
                {
                    String sql = "alter table  BHQ_XHQK_GJ  add column speed NVARCHAR(10)";
                    sqLiteDatabase.execSQL(sql);
                }
            }
        } catch (DbException e)
        {
            e.printStackTrace();
            return;
        }

    }
}
