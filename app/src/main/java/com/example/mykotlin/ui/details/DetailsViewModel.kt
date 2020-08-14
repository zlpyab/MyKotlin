package com.example.mykotlin.ui.details

import androidx.lifecycle.MutableLiveData
import com.example.mykotlin.base.BaseViewModel
import com.example.mykotlin.common.simple.isLogin
import com.example.mykotlin.model.store.UserInfoStore
import com.example.mykotlin.ui.common.CollectRepository
import com.example.mykotlin.util.SessionUtils

/**
 * Created by zlp on 2020/8/13 0013.
 */
class DetailsViewModel : BaseViewModel() {

    private val collectRepository by lazy { CollectRepository() }

    var collect = MutableLiveData<Boolean>()

    fun collect(id: Int) {
        launch(
            block = {
                collectRepository.collect(id)
                UserInfoStore.addCollectId(id)
                collect.value = true
            },
            error = {
                collect.value = false
            }
        )
    }


    fun unCollect(id: Int) {
        launch(
            block = {
                collectRepository.unCollect(id)
                UserInfoStore.removeCollectId(id)
                collect.value = false
            },
            error = {
                collect.value = true
            }
        )
    }

    fun updateCollectState(id: Int) {
        collect.value = if (isLogin()) {
            UserInfoStore.getUserInfo()?.collectIds?.contains(id)
        } else {
            false
        }
    }
}