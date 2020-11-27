package com.example.mykotlin.ui.main.system

import androidx.lifecycle.MutableLiveData
import com.example.mykotlin.base.BaseViewModel
import com.example.mykotlin.model.bean.Category

/**
 * Created by zlp on 2020/8/17 0017.
 */
class SystemViewModel : BaseViewModel() {

    private val systemRepository by lazy { SystemRepository() }

    val categories = MutableLiveData<MutableList<Category>>()
    val loadingStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()


    fun getCategory() {
        loadingStatus.value = true
        reloadStatus.value = false
        launch(
            block = {
                categories.value = systemRepository.getSystemCategory()
                loadingStatus.value = false
            },
            error = {
                loadingStatus.value = false
                reloadStatus.value = true
            }
        )
    }
}