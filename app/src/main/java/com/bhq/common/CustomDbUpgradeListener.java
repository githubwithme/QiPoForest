package com.bhq.common;

import android.database.sqlite.SQLiteDatabase;

import com.lidroid.xutils.DbUtils;

/**
 * Created by ${hmj} on 2016/3/16.
 */
public class CustomDbUpgradeListener implements  DbUtils.DbUpgradeListener
{

    @Override
    public void onUpgrade(DbUtils dbUtils, int i, int i1)
    {

        SQLiteDatabase sqLiteDatabase= dbUtils.getDatabase();
        boolean isexist=SqliteDb.checkColumnExist1(sqLiteDatabase, "BHQ_XHXL_GJ", "XXZT");
        boolean isexist_IsRead=SqliteDb.checkColumnExist1(sqLiteDatabase,"RW_RW","IsRead");
        if (!isexist)
        {
            String sql_updateBHQ_XHXL_GJ = "alter table  BHQ_XHXL_GJ  add column XXZT NVARCHAR(10)";
            sqLiteDatabase.execSQL(sql_updateBHQ_XHXL_GJ);
        }
        if (!isexist_IsRead)
        {
            String sql_updateBHQ_XHXL_GJ = "alter table  RW_RW  add column IsRead NVARCHAR(10)";
            sqLiteDatabase.execSQL(sql_updateBHQ_XHXL_GJ);
        }
//        boolean isexist_altitude=SqliteDb.checkColumnExist1(sqLiteDatabase,"BHQ_XHQK_GJ","altitude");
//        boolean isexist_accuracy=SqliteDb.checkColumnExist1(sqLiteDatabase,"BHQ_XHQK_GJ","accuracy");
//        boolean isexist_bearing=SqliteDb.checkColumnExist1(sqLiteDatabase,"BHQ_XHQK_GJ","bearing");
//        boolean isexist_speed=SqliteDb.checkColumnExist1(sqLiteDatabase,"BHQ_XHQK_GJ","speed");
//        if (!isexist_altitude)
//        {
//            String sql = "alter table  BHQ_XHQK_GJ  add column altitude NVARCHAR(10)";
//            sqLiteDatabase.execSQL(sql);
//        }
//        if (!isexist_accuracy)
//        {
//            String sql = "alter table  BHQ_XHQK_GJ  add column accuracy NVARCHAR(10)";
//            sqLiteDatabase.execSQL(sql);
//        }
//        if (!isexist_bearing)
//        {
//            String sql = "alter table  BHQ_XHQK_GJ  add column bearing NVARCHAR(10)";
//            sqLiteDatabase.execSQL(sql);
//        }
//        if (!isexist_speed)
//        {
//            String sql = "alter table  BHQ_XHQK_GJ  add column speed NVARCHAR(10)";
//            sqLiteDatabase.execSQL(sql);
//        }
//        String sql_updateBHQ_XHQK_GJ = "alter table  BHQ_XHQK_GJ  add column altitude NVARCHAR(10),accuracy NVARCHAR(10),bearing NVARCHAR(10),speed NVARCHAR(10)";
//        sqLiteDatabase.execSQL(sql_updateBHQ_XHQK_GJ);
    }
}
