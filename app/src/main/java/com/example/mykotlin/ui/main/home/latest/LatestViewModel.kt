package com.example.mykotlin.ui.main.home.latest

import androidx.lifecycle.MutableLiveData
import com.example.mykotlin.base.BaseViewModel
import com.example.mykotlin.common.bus.Bus
import com.example.mykotlin.common.bus.USER_COLLECT_UPDATE
import com.example.mykotlin.common.loadmore.LoadMoreStatus
import com.example.mykotlin.common.simple.isLogin
import com.example.mykotlin.model.bean.Article
import com.example.mykotlin.model.store.UserInfoStore
import com.example.mykotlin.ui.common.CollectRepository

/**
 * Created by zlp on 2020/8/14 0014.
 */
class LatestViewModel : BaseViewModel() {

    private val latestRepository by lazy { LatestRepository() }
    private val collectRepository by lazy { CollectRepository() }

    companion object {
        const val INIT_PAGE = 0
    }

    var page = INIT_PAGE

    var articleList: MutableLiveData<MutableList<Article>> = MutableLiveData()
    val refreshState = MutableLiveData<Boolean>()
    val reloadState = MutableLiveData<Boolean>()
    val loadMoreState = MutableLiveData<LoadMoreStatus>()

    fun refreshLatestListData() {
        refreshState.value = true
        reloadState.value = false
        launch(
            block = {
                val pagination = latestRepository.getLatestListData(INIT_PAGE)
                page = pagination.curPage
                articleList.value = pagination.datas.toMutableList()
                refreshState.value = false
            },
            error = {
                refreshState.value = false
                reloadState.value = page == INIT_PAGE
            }
        )
    }

    fun loadMoreLatestListData() {
        loadMoreState.value = LoadMoreStatus.LOADING
        launch(
            block = {
                val pagination = latestRepository.getLatestListData(page)
                page = pagination.curPage
                val currentList = articleList.value ?: mutableListOf()
                currentList.addAll(pagination.datas)

                articleList.value = currentList
                loadMoreState.value = if (pagination.offset >= pagination.total) {
                    LoadMoreStatus.END
                } else {
                    LoadMoreStatus.COMPLETE
                }
            },
            error = {
                LoadMoreStatus.ERROR
            }
        )
    }

    fun collect(id: Int) {
        launch(
            block = {
                collectRepository.collect(id)
                UserInfoStore.addCollectId(id)
                Bus.post(USER_COLLECT_UPDATE, id to true)
            },
            error = {
                Bus.post(USER_COLLECT_UPDATE, id to false)
            }
        )
    }

    fun unCollect(id: Int) {
        launch(
            block = {
                collectRepository.unCollect(id)
                UserInfoStore.removeCollectId(id)
                Bus.post(USER_COLLECT_UPDATE, id to false)
            },
            error = {
                Bus.post(USER_COLLECT_UPDATE, id to true)
            }
        )
    }

    fun updateLatestListData() {
        val list = articleList.value
        if (list.isNullOrEmpty()) return
        if (isLogin()) {
            list.forEach {
                var collectIds = UserInfoStore.getUserInfo()?.collectIds ?: return
                it.collect = collectIds.contains(it.id)
            }
        } else {
            list.forEach { it.collect = false }
        }
        articleList.value = list
    }

    fun updateItemLatestData(target: Pair<Int, Boolean>) {
        val list = articleList.value
        var item = list?.find { it.id == target.first } ?: return
        item.collect = target.second
        articleList.value = list
    }

}