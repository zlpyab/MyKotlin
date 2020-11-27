package com.example.mykotlin.ui.main.discovery

import androidx.lifecycle.MutableLiveData
import com.example.mykotlin.base.BaseViewModel
import com.example.mykotlin.model.bean.Article
import com.example.mykotlin.model.bean.BannerInfo
import com.example.mykotlin.model.bean.HotWords
import com.example.mykotlin.model.bean.Webkits
import com.youth.banner.Banner
import kotlinx.coroutines.delay

/**
 * Created by zlp on 2020/10/13 0013.
 */
class DiscoveryViewModel : BaseViewModel() {

    private val discoveryRepository by lazy { DiscoveryRepository() }

    val banners = MutableLiveData<List<BannerInfo>>()
    val hotWords = MutableLiveData<List<HotWords>>()
    val webKits = MutableLiveData<List<Webkits>>()
    val refreshStates = MutableLiveData<Boolean>()
    val reloadStates = MutableLiveData<Boolean>()

    fun getData() {
        refreshStates.value = true
        reloadStates.value = false
        launch(
            block = {

                banners.value = discoveryRepository.getBanners()
                hotWords.value = discoveryRepository.getHotWords()
                webKits.value = discoveryRepository.getWebkit()

                refreshStates.value = false
            },

            error = {
                refreshStates.value = false
                reloadStates.value =
                    banners.value.isNullOrEmpty() && hotWords.value.isNullOrEmpty() && webKits.value.isNullOrEmpty()
            }
        )
    }
}