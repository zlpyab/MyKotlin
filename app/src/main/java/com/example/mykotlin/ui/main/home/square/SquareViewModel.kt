package com.example.mykotlin.ui.main.home.square

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
class SquareViewModel : BaseViewModel() {

    companion object {
        const val INIT_PAGE = 0
    }

    private val squareRepository by lazy { SquareRepository() }
    private val collectRepository by lazy { CollectRepository() }

    private var page = INIT_PAGE

    var articleList: MutableLiveData<MutableList<Article>> = MutableLiveData()
    var refreshState = MutableLiveData<Boolean>()
    var reloadState = MutableLiveData<Boolean>()
    var loadMoreState = MutableLiveData<LoadMoreStatus>()

    fun refreshListData() {
        refreshState.value = true
        reloadState.value = false
        launch(
            block = {
                val pagination = squareRepository.getSquareListData(INIT_PAGE)
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

    fun loadMoreListData() {
        loadMoreState.value = LoadMoreStatus.LOADING
        launch(
            block = {
                var pagination = squareRepository.getSquareListData(page)
                page = pagination.curPage
                var currentList = articleList.value ?: mutableListOf()
                currentList.addAll(pagination.datas)

                articleList.value = currentList
                loadMoreState.value = if (pagination.offset >= pagination.total){
                    LoadMoreStatus.END
                }else{
                    LoadMoreStatus.COMPLETE
                }
            },
            error = {
                 LoadMoreStatus.ERROR
            }
        )
    }

    fun collect(id : Int){
        launch(
            block = {
                collectRepository.collect(id)
                UserInfoStore.addCollectId(id)
                Bus.post(USER_COLLECT_UPDATE,id to  true)
            },
            error = {
                Bus.post(USER_COLLECT_UPDATE,id to  false)
            }
        )
    }

    fun unCollect(id : Int){
        launch(
            block = {
                collectRepository.unCollect(id)
                UserInfoStore.removeCollectId(id)
                Bus.post(USER_COLLECT_UPDATE,id to  false)
            },
            error = {
                Bus.post(USER_COLLECT_UPDATE,id to  true)
            }
        )
    }

    fun updateListCollect(){
        val list = articleList.value
        if (list.isNullOrEmpty()) return
        if (isLogin()){
            list.forEach {
                val collectIds = UserInfoStore.getUserInfo()?.collectIds ?: return
                it.collect = collectIds.contains(it.id)
            }
        }else{
            list.forEach { it.collect = false }
        }
    }
    fun updateItemCollect(target : Pair<Int,Boolean>){
        val list = articleList.value
        val item = list?.find { it.id == target.first } ?: return
        item.collect = target.second
        articleList.value = list
    }

}