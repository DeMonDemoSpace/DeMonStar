package com.cr.app.act.mvp

import com.cr.app.R
import com.demon.baseframe.activity.BaseActivity
import com.demon.baseframe.model.BasePresenterInfc
import com.demon.baseui.view.CustomSearchView
import com.demon.baseutil.ToastUtil
import kotlinx.android.synthetic.main.activity_city.*

class CityActivity : BaseActivity<BasePresenterInfc>() {

    override fun bindLayout(): Int = R.layout.activity_city

    override fun initCreate() {
        searchView.setOnQueryListener(object : CustomSearchView.OnQueryListener {
            override fun onQueryTextSubmit(query: String?) {
                ToastUtil.showToast(mContext, query)
            }

            override fun onCancel() {
                finish()
            }
        })
    }

}
