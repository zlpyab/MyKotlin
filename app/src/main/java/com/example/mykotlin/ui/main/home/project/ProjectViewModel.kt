package com.example.mykotlin.ui.main.home.project

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

/**
 * Created by zlp on 2020/8/17 0017.
 */
class ProjectViewModel : BaseViewModel() {

    companion object {
        const val INIT_CHECKED = 0
        const val INIT_PAGE = 1
    }

    private val projectRepository by lazy { ProjectRepository() }
    private val collectRepository by lazy { CollectRepository() }

    val categoryList: MutableLiveData<MutableList<Category>> = MutableLiveData()
    val articleList: MutableLiveData<MutableList<Article>> = MutableLiveData()
    val checkedCategory = MutableLiveData<Int>()
    val refreshState = MutableLiveData<Boolean>()
    val loadMoreState = MutableLiveData<LoadMoreStatus>()
    val reloadListState = MutableLiveData<Boolean>()
    val reloadViewState = MutableLiveData<Boolean>()


    var page = INIT_PAGE

    fun getCategoryList() {
        refreshState.value = true
        reloadViewState.value = false
        launch(
            block = {
                val categories = projectRepository.getCategoryList()
                val checkedPosition = INIT_CHECKED
                val cid = categories[checkedPosition].id
                val pagination = projectRepository.getProjectList(INIT_PAGE, cid)

                page = pagination.curPage
                categoryList.value = categories
                checkedCategory.value = checkedPosition
                articleList.value = pagination.datas.toMutableList()
                refreshState.value = false

            },
            error = {
                refreshState.value = false
                reloadViewState.value = true
            }
        )
    }

    fun refreshProjectList(checkedPosition: Int = checkedCategory.value ?: INIT_CHECKED) {
        refreshState.value = true
        reloadListState.value = false
        if (checkedPosition != checkedCategory.value) {
            articleList.value = mutableListOf()
            checkedCategory.value = checkedPosition
        }
        launch(
            block = {
                val categories = categoryList.value ?: return@launch
                val cid = categories[checkedPosition].id
                val pagination = projectRepository.getProjectList(INIT_PAGE, cid)

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

    fun loadMoreProjectList() {
        loadMoreState.value = LoadMoreStatus.LOADING
        launch(
            block = {
                val categories = categoryList.value ?: return@launch
                val checkedPosition = checkedCategory.value ?: return@launch
                val cid = categories[checkedPosition].id
                val pagination = projectRepository.getProjectList(page + 1, cid)

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

    fun updateListCollect(){
        val list = articleList.value
        if (list.isNullOrEmpty()) return
        if (isLogin()){
            val collectIds = UserInfoStore.getUserInfo()?.collectIds ?: return
            list.forEach {  it.collect = collectIds.contains(it.id) }
        }else{
            list.forEach { it.collect  = false }
        }
        articleList.value = list
    }

    fun updateItemCollect(target: Pair<Int, Boolean>) {
        val list = articleList.value ?: return
        val item = list?.find { target.first == it.id } ?: return
        item.collect = target.second
        articleList.value = list
    }

}