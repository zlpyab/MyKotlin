package com.example.mykotlin.ui.main.home.hot

import androidx.lifecycle.MutableLiveData
import com.example.mykotlin.base.BaseViewModel
import com.example.mykotlin.common.bus.Bus
import com.example.mykotlin.common.bus.USER_COLLECT_UPDATE
import com.example.mykotlin.common.bus.USER_LOGIN_STATE_CHANGED
import com.example.mykotlin.common.loadmore.LoadMoreStatus
import com.example.mykotlin.common.simple.isLogin
import com.example.mykotlin.model.bean.Article
import com.example.mykotlin.model.store.UserInfoStore
import com.example.mykotlin.ui.common.CollectRepository
import com.example.mykotlin.util.SessionUtils
import com.example.mykotlin.util.Utils

/**
 * Created by zlp on 2020/8/12 0012.
 */
class HotViewModel : BaseViewModel() {

    companion object {
        const val INIT_PAGE = 0
    }

    private val hotRepository by lazy { HotRepository() }
    private val collectRepository by lazy { CollectRepository() }

    val articleList: MutableLiveData<MutableList<Article>> = MutableLiveData()
    val loadMoreStatus = MutableLiveData<LoadMoreStatus>()
    val refreshStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()

    private var page = INIT_PAGE


    fun refreshArticleList() {
        refreshStatus.value = true
        reloadStatus.value = false
        launch(
            block = {
                val topArticleListDeferred = async {
                    hotRepository.getTopArticleList()
                }
                val pageArticleListDeferred = async {
                    hotRepository.getArticleList(INIT_PAGE)
                }
                val topArticleList = topArticleListDeferred.await().apply {
                    forEach { it.top = true }
                }
                val pagination = pageArticleListDeferred.await()
                page = pagination.curPage
                articleList.value = mutableListOf<Article>().apply {
                    addAll(topArticleList)
                    addAll(pagination.datas)
                }
                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
                reloadStatus.value = page == INIT_PAGE
            }
        )
    }

    fun loadMoreArticleList() {
        loadMoreStatus.value = LoadMoreStatus.LOADING
        launch(
            block = {
                val pagination = hotRepository.getArticleList(page)
                page = pagination.curPage
                val currentList = articleList.value ?: mutableListOf()
                currentList.addAll(pagination.datas)
                articleList.value = currentList
                loadMoreStatus.value = if (pagination.offset >= pagination.total) {
                    LoadMoreStatus.END
                } else {
                    LoadMoreStatus.COMPLETE
                }
            },
            error = {
                loadMoreStatus.value = LoadMoreStatus.ERROR
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
                updateItemCollectState(id to false)
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
                updateItemCollectState(id to true)
            }
        )
    }

    /**
     * 更新列表收藏状态
     */
    fun updateListCollectState() {
        val list = articleList.value
        if (list.isNullOrEmpty()) return
        if (isLogin()) {
            var collectIds =UserInfoStore.getUserInfo()?.collectIds ?: return
            list.forEach { it.collect = collectIds.contains(it.id) }
        } else {
            list.forEach { it.collect = false }
        }
        articleList.value = list
    }


    /**
     * 更新item收藏状态
     */
    fun updateItemCollectState(target: Pair<Int, Boolean>) {
        var list = articleList.value
        var item = list?.find { it.id == target.first } ?: return
        item.collect = target.second
        articleList.value = list
    }
}