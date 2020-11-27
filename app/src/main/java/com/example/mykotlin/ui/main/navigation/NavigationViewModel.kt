package com.example.mykotlin.ui.main.navigation

import androidx.lifecycle.MutableLiveData
import com.example.mykotlin.base.BaseViewModel
import com.example.mykotlin.model.bean.Navigation

/**
 * Created by zlp on 2020/10/12 0012.
 */
class NavigationViewModel : BaseViewModel() {

    private val navigationRepository by lazy { NavigationRepository() }

    val navigations = MutableLiveData<List<Navigation>>()
    val refreshStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()

    fun getNavigations() {
        refreshStatus.value = true
        reloadStatus.value = false
        launch(
            block = {
                navigations.value = navigationRepository.getNavigation()
                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
                reloadStatus.value = navigations.value.isNullOrEmpty()
            }
        )
    }
}