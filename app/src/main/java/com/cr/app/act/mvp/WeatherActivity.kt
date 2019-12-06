package com.cr.app.act.mvp

import com.cr.app.R
import com.cr.app.act.mvp.contract.WeatherContract
import com.cr.app.bean.WeatherNow
import com.demon.baseframe.activity.BaseBarActivity
import kotlinx.android.synthetic.main.activity_weather.*

class WeatherActivity : BaseBarActivity<WeatherContract.Presenter>(), WeatherContract.View {

    override fun bindLayout(): Int = R.layout.activity_weather

    override fun initCreate() {

        mPresenter.getNormalWeather("广州")


        fabCity.setOnClickListener {

        }

    }

    override fun initTitle(): String {
        return "实况天气-广州"
    }

    override fun weather(now: WeatherNow) {
        etvFl.text = now.fl + "℃"
        etvTmp.text = now.tmp + "℃"
        etvCloud.text = now.cloud
        etvHum.text = now.hum
        etvPcpn.text = now.pcpn
        etvPres.text = now.pres
        etvVis.text = now.vis + "公里"
        etvWind_dir.text = now.windDir
        etvWind_sc.text = now.windSc
        etvWind_spd.text = now.windSpd
        etvCond_txt.text = now.condTxt
    }
}
