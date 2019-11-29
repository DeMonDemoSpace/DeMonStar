package com.cr.app.act;

import com.cr.app.R;
import com.cr.app.adapter.MainAdapter;
import com.cr.app.bean.MainBean;
import com.demon.baseframe.activity.BaseBarActivity;
import com.demon.baseframe.model.BasePresenterInfc;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;

public class MainActivity extends BaseBarActivity<BasePresenterInfc> {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private MainAdapter adapter;
    private List<MainBean> beanList = new ArrayList<>();

    @Override
    protected int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initCreate() {
        beanList.add(new MainBean(true, "BaseFrame"));
        beanList.add(new MainBean("MVP"));
        beanList.add(new MainBean(true, "BaseList"));
        beanList.add(new MainBean("普通列表"));
        beanList.add(new MainBean("分组列表"));
        beanList.add(new MainBean("拖拽，滑动列表"));
        beanList.add(new MainBean("多类型列表"));
        beanList.add(new MainBean(true, "BaseUI"));
        beanList.add(new MainBean(true, "BaseUtil"));

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new MainAdapter(beanList);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public String initTitle() {
        return "功能展示";
    }

}
