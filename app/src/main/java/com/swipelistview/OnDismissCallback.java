package com.swipelistview;

import android.widget.AbsListView;

/**
 * Created by ${shuwenouwan} on 2015/12/217:05.
 * email：1655815015@qq.com
 * description:
 */
public interface OnDismissCallback
{
    void onDismiss(AbsListView listView, int[] reverseSortedPositions);
}
