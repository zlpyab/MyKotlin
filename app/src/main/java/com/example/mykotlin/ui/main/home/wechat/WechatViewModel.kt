package com.example.mykotlin.ui.main.home.wechat

import androidx.lifecycle.MutableLiveData
import com.example.mykotlin.base.BaseViewModel
import com.example.mykotlin.common.bus.Bus
import com.example.mykotlin.common.bus.USER_COLLECT_UPDATE
import com.example.mykotlin.common.loadmore.LoadMoreStatus
import com.example.mykotlin.common.simple.isLogin
import com.example.mykotlin.model.bean.Article
import com.example.mykotlin.model.bean.Category
import com.example.mykotlin.model.store.UserInfoStore
import com.example.mykotlin.ui.common.CollectRepository
import java.net.IDN

/**
 * Created by zlp on 2020/8/17 0017.
 */
class WechatViewModel : BaseViewModel() {

    companion object {
        const val INIT_CHECKED = 0
        const val INIT_PAGE = 1
    }

    private val wechateRepository by lazy { WechatRepository() }
    private val collectRepository by lazy { CollectRepository() }

    var page = INIT_PAGE

    val articleList: MutableLiveData<MutableList<Article>> = MutableLiveData()
    val categoryList: MutableLiveData<MutableList<Category>> = MutableLiveData()
    val refreshState = MutableLiveData<Boolean>()
    val checkedCategory = MutableLiveData<Int>()
    val loadMoreState = MutableLiveData<LoadMoreStatus>()
    val reloadListState = MutableLiveData<Boolean>()
    val reloadViewState = MutableLiveData<Boolean>()


    fun getWechatCategory() {
        refreshState.value = true
        reloadViewState.value = false
        launch(
            block = {
                val categories = wechateRepository.getWechatCategories()
                val checkedPosition = INIT_CHECKED
                val id = categories[checkedPosition].id
                val pagination = wechateRepository.getWechatArticelList(INIT_PAGE, id)

                page = pagination.curPage
                categoryList.value = categories
                articleList.value = pagination.datas.toMutableList()
                checkedCategory.value = checkedPosition
                refreshState.value = false

            },
            error = {
                refreshState.value = false
                reloadViewState.value = true
            }
        )
    }

    fun refreshWechatArticleList(checkedPosition: Int = checkedCategory.value ?: INIT_CHECKED) {
        refreshState.value = true
        reloadListState.value = false
        if (checkedPosition != checkedCategory.value) {
            articleList.value = mutableListOf()
            checkedCategory.value = checkedPosition
        }
        launch(
            block = {
                val categories = categoryList.value ?: return@launch
                val id = categories[checkedPosition].id
                val pagination = wechateRepository.getWechatArticelList(INIT_PAGE, id)

                page = pagination.curPage
                articleList.value = pagination.datas.toMutableList()
                refreshState.value = false
            },
            error = {
                refreshState.value = false
                reloadListState.value = articleList.value?.isEmpty()
            }
        )
    }

    fun loadMoreWechatArticleList() {
        loadMoreState.value = LoadMoreStatus.LOADING
        launch(
            block = {
                val categories = categoryList.value ?: return@launch
                val checkedPosition = checkedCategory.value ?: return@launch
                val id = categories[checkedPosition].id
                val pagination = wechateRepository.getWechatArticelList(page + 1, id)

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

    fun updateListCollect() {
        val list = articleList.value
        if (list.isNullOrEmpty()) return
        if (isLogin()) {
            val collectIds = UserInfoStore.getUserInfo()?.collectIds ?: return
            list.forEach { it.collect = collectIds.contains(it.id) }
            list.forEach { it.collect = collectIds.contains(it.id) }
        } else {
            list.forEach { it.collect = false }
        }
        articleList.value = list
    }

    fun updateItemCollect(target: Pair<Int, Boolean>) {
        val list = articleList.value ?: return
        val item = list?.find { it.id == target.first } ?: return
        item.collect = target.second
        articleList.value = list
    }
}