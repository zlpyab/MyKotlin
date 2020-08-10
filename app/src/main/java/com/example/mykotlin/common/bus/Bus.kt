package com.example.mykotlin.common.bus

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.jeremyliao.liveeventbus.LiveEventBus
import java.security.Key

/**
 * Created by zlp on 2020/8/10 0010.
 */
object Bus {

    /**
     * 发布LiveDataEventBus消息
     */
    inline fun <reified T> post(key : String ,value: T){
        LiveEventBus.get(key,T::class.java).post(value)
    }

    /**
     * 订阅LiveDataEventBus消息
     * @param key 键
     * @param owner 生命周期owner
     * @param observer 观察者
     */
   inline fun <reified T> observe(key: String,owner : LifecycleOwner,observer: Observer<T>){
       LiveEventBus.get(key,T::class.java).observe(owner,observer)
   }

    /**
     * 应用进程生命周期内订阅LiveDataEventBus消息
     * @param Key 渠道
     * @param observer 观察者
     */
    inline fun <reified T> observeForever(key: String, observer: Observer<T>) {
        LiveEventBus.get(key, T::class.java).observeForever(observer)
    }

    /**
     * 订阅粘性LiveDataEventBus消息
     * @param Key 渠道
     * @param owner 生命周期owner
     * @param observer 观察者
     */
    inline fun <reified T> observeSticky(
        key: String,
        owner: LifecycleOwner,
        observer: Observer<T>
    ) {
        LiveEventBus.get(key, T::class.java).observeSticky(owner, observer)
    }

    /**
     * 应用进程生命周期内订阅粘性LiveDataEventBus消息
     * @param Key 渠道
     * @param observer 观察者
     */
    inline fun <reified T> observeStickyForever(
        Key: String,
        observer: Observer<T>
    ) {
        LiveEventBus.get(Key, T::class.java).observeStickyForever(observer)
    }
}