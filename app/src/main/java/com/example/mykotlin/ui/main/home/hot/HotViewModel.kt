package com.example.mykotlin.ui.main.home.hot

import androidx.lifecycle.MutableLiveData
import com.example.mykotlin.base.BaseViewModel
import com.example.mykotlin.common.loadmore.LoadMoreStatus
import com.example.mykotlin.model.bean.Article

/**
 * Created by zlp on 2020/8/12 0012.
 */
class HotViewModel : BaseViewModel() {

    companion object {
        const val INIT_PAGE = 0
    }

    private val hotRepository by lazy { HotRepository() }

    val articleList: MutableLiveData<MutableList<Article>> = MutableLiveData()
    val loadMoreStatus = MutableLiveData<LoadMoreStatus>()
    val refreshStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()

    var page = INIT_PAGE


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
}