package com.example.mykotlin.ui.main.system.pager

import androidx.lifecycle.MutableLiveData
import com.chad.library.adapter.base.loadmore.BaseLoadMoreView
import com.chad.library.adapter.base.loadmore.LoadMoreStatus
import com.example.mykotlin.base.BaseViewModel
import com.example.mykotlin.common.bus.Bus
import com.example.mykotlin.common.bus.USER_COLLECT_UPDATE
import com.example.mykotlin.common.simple.isLogin
import com.example.mykotlin.http.HttpManager
import com.example.mykotlin.model.bean.Article
import com.example.mykotlin.model.store.UserInfoStore
import com.example.mykotlin.ui.common.CollectRepository
import kotlinx.coroutines.Job

/**
 * Created by zlp on 2020/10/13 0013.
 */
class SystemPagerViewModel : BaseViewModel() {


    companion object {
        const val INITIAL_PAGE = 0
    }


    private val systemPagerRepository by lazy { SystemPagerRepository() }
    private val collectRepository by lazy { CollectRepository() }


    var articleList = MutableLiveData<MutableList<Article>>()
    var refreshStatus = MutableLiveData<Boolean>()
    var loadMoreStatus = MutableLiveData<LoadMoreStatus>()
    var reloadStatus = MutableLiveData<Boolean>()

    private var id = -1
    private var refreshJob: Job? = null
    private var page = INITIAL_PAGE

    fun refreshArticleList(cid: Int) {
        if (cid != id) {
            cancelJob(refreshJob)
            id = cid
            articleList.value = mutableListOf()
        }
        refreshStatus.value = true
        reloadStatus.value = false
        launch(
            block = {
                val pagination = systemPagerRepository.getArticleListByCid(INITIAL_PAGE, cid)
                page = pagination.curPage
                articleList.value = pagination.datas.toMutableList()
                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
                reloadStatus.value = articleList.value?.isEmpty()
            }
        )
    }

    fun loadMoreArticleList(cid: Int) {
        loadMoreStatus.value = LoadMoreStatus.Loading
        launch(
            block = {
                val pagination = systemPagerRepository.getArticleListByCid(page, cid)
                page = pagination.curPage
                var currentList = articleList.value ?: mutableListOf()
                currentList.addAll(pagination.datas)
                articleList.value = currentList
                loadMoreStatus.value = if (pagination.offset >= pagination.total) {
                    LoadMoreStatus.End
                } else {
                    LoadMoreStatus.Complete
                }
            },
            error = {
                LoadMoreStatus.Fail
            }
        )
    }

    fun collect(id: Int) {
        launch(
            block = {
                collectRepository.collect(id)
                UserInfoStore.addCollectId(id)
                Bus.post(USER_COLLECT_UPDATE,id to  true)
            },
            error = {
                Bus.post(USER_COLLECT_UPDATE,id to false)
            }
        )
    }

    fun unCollect(id: Int){
         launch(
             block = {
                 collectRepository.unCollect(id)
                 UserInfoStore.removeCollectId(id)
                 Bus.post(USER_COLLECT_UPDATE,id to false)
             },
             error = {
                 Bus.post(USER_COLLECT_UPDATE,id to true)
             }
         )
    }

    /**
     * 更新列表收藏状态
     */
    fun updateListCollectState(){
        var list = articleList.value ?: return
        if (isLogin()){
         var collectIds = UserInfoStore.getUserInfo()?.collectIds ?:return
            list.forEach { it.collect = collectIds.contains(it.id) }
        }else{
           list.forEach { it.collect = false }
        }
        articleList.value = list
    }

    /**
     * 更新item收藏状态
     */
     fun updateItemCollectState(pair: Pair<Int, Boolean>) {
        var list = articleList.value
        var item = list?.find { it.id == pair.first } ?: return
        item.collect = pair.second
        articleList.value = list
    }
}