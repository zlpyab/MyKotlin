package com.example.mykotlin.viewModel

import androidx.lifecycle.MutableLiveData
import com.example.mykotlin.base.BaseViewModel
import com.example.mykotlin.model.bean.AdvertiseBean
import com.example.mykotlin.model.bean.LoginBean
import com.example.mykotlin.http.HttpManager
import com.example.mykotlin.util.CommonUtils
import com.example.mykotlin.util.SessionUtils
import com.example.mykotlin.util.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by zlp on 2020/7/27 0027.
 */
class LoginViewModel : BaseViewModel() {

    val loginData = MutableLiveData<LoginBean>()
    val advertiseData = MutableLiveData<AdvertiseBean>()

    fun login(phone: String, psw: String) {

        launch {
            val res = withContext(Dispatchers.IO) {
                HttpManager.instance.service.login(phone, CommonUtils.getMD5(psw), 1)
            }
            if (res.errcode == 0) {
                SessionUtils.setCellPhone(phone)
                SessionUtils.setLoginKey(res.data!!.loginkey)
                loginData.value = res.data
            } else {
                Utils.showToast(res.info)
            }
        }
    }

    fun downSplash(){
       launch {
           val res = withContext(Dispatchers.IO){
               HttpManager.instance.service.getAdvertise()
           }
           if (res.errcode == 0){
               advertiseData.value = res.data
           }else{
               Utils.showToast(res.info)
           }
       }
    }
}