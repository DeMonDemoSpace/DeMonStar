package com.cr.app.act.mvp

import android.app.Activity
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseViewHolder
import com.cr.app.R
import com.cr.app.act.mvp.contract.CityContract
import com.cr.app.bean.City
import com.cr.app.data.Constants
import com.demon.baseframe.activity.BaseActivity
import com.demon.baselist.adapter.QuickAdapter
import com.demon.baselist.view.RecycleViewStateView
import com.demon.baseui.view.CustomSearchView
import com.demon.baseutil.status.StatusBarUtil
import com.demon.baseutil.status.StatusFontUtil
import kotlinx.android.synthetic.main.activity_city.*

class CityActivity : BaseActivity<CityContract.Presenter>(), CityContract.View {
    private var adapter: QuickAdapter<City>? = null

    override fun bindLayout(): Int = R.layout.activity_city

    override fun initCreate() {
        StatusBarUtil.setColorDiff(this, resources.getColor(R.color.white))
        StatusFontUtil.setStatusFont(this, true)

        searchView.setOnQueryListener(object : CustomSearchView.OnQueryListener {
            override fun onQueryTextSubmit(query: String) {
                mPresenter.search(query)
            }

            override fun onCancel() {
                finish()
            }
        })

        rvSearch.layoutManager = LinearLayoutManager(this)
        adapter = object : QuickAdapter<City>(R.layout.list_city, arrayListOf()) {
            override fun convert(helper: BaseViewHolder, item: City?) {
                item?.run {
                    var str = "$cnty-$adminArea-"
                    str += if (location == parentCity) {
                        location
                    } else {
                        "$parentCity-$location"
                    }
                    helper.setText(R.id.tvCity, str)
                    helper.itemView.setOnClickListener {
                        setResult(Activity.RESULT_OK, Intent().putExtra(Constants.LOCATION, location))
                        finish()
                    }
                }
            }
        }
        rvSearch.adapter = adapter


        mPresenter.topCity()

    }

    override fun citys(cityList: List<City>) {
        if (cityList.isNotEmpty()) {
            adapter!!.setNewData(cityList)
        } else {
            adapter!!.emptyView = RecycleViewStateView(mContext).setEmptyText("没有搜索到城市~").emptyView
            adapter!!.setNewData(null)
        }

    }

}
