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
        String sql_updateBHQ_XHXL_GJ = "alter table  BHQ_XHXL_GJ  add column XXZT NVARCHAR(10)";
        String sql_updateBHQ_XHQK_GJ = "alter table  BHQ_XHQK_GJ  add column altitude NVARCHAR(10),accuracy NVARCHAR(10),bearing NVARCHAR(10),speed NVARCHAR(10)";
        sqLiteDatabase.execSQL(sql_updateBHQ_XHXL_GJ);
        sqLiteDatabase.execSQL(sql_updateBHQ_XHQK_GJ);
    }
}
