package com.example.mykotlin.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * Created by zlp on 2020/7/28 0028.
 */
open abstract class BaseFragment : Fragment() {

    var isVisable = false //是否可见= false
    var isInit = false //是否加载过= false

    var mActivity: Activity? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = getContext() as Activity?
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(initLayout(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        isInit = true
        setLoad()
    }

    private fun setLoad() {
        if (isVisable && isInit) {
            setLazy()
            isInit = false
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisable = isVisibleToUser;
        setLoad()
    }

    //初始布局
    abstract fun initLayout(): Int

    //找控件
    abstract fun initView(view: View)

    //数据操作哦
    abstract fun setLazy()

}