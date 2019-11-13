package com.demon.baseui.list;

import androidx.recyclerview.widget.ItemTouchHelper;

/**
 * @author DeMon
 * @date 2017/11/24
 * @description 用于支持RecyclerView手势滑动的辅助类
 */

public class MyItemTouchHelper extends ItemTouchHelper {
    public MyItemTouchHelper(MyItemTouchHelperCallback.MyItemTouchCallBackListener listener) {
        super(new MyItemTouchHelperCallback(listener));
    }
}
