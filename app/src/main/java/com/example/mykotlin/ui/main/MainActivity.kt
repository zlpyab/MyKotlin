package com.example.mykotlin.ui.main

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.ViewPropertyAnimator
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import com.example.mykotlin.R
import com.example.mykotlin.base.BaseActivity
import com.example.mykotlin.common.simple.ScrollToTop
import com.example.mykotlin.ui.main.discovery.DiscoveryFragment
import com.example.mykotlin.ui.main.home.HomeFragment
import com.example.mykotlin.ui.main.mine.MineFragment
import com.example.mykotlin.ui.main.navigation.NavigationFragment
import com.example.mykotlin.ui.main.system.SystemFragment
import com.example.mykotlin.util.Utils
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 主页
 */
class MainActivity : BaseActivity() {

    private lateinit var mFragments: Map<Int, Fragment>
    private var bottomNavigationViewAnimtor: ViewPropertyAnimator? = null
    private var currentBottomNavagtionState = true
    private var previousTimeMillis = 0L

    override fun getLayoutId() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ImmersionBar.with(this)
            .statusBarColor(R.color.color_ffffff)
            .fitsSystemWindows(true)
            .statusBarDarkFont(true).init()
        mFragments = mapOf(
            R.id.home to createFragment(HomeFragment::class.java),
            R.id.system to createFragment(SystemFragment::class.java),
            R.id.discovery to createFragment(DiscoveryFragment::class.java),
            R.id.navigation to createFragment(NavigationFragment::class.java),
            R.id.mine to createFragment(MineFragment::class.java)
        )

        bottomNavigationView.run {
            setOnNavigationItemSelectedListener {menuItem->
                showFragment(menuItem.itemId)
                true
            }
            setOnNavigationItemReselectedListener {menuItem->
                val fragment = mFragments[menuItem.itemId]
                if (fragment is ScrollToTop){
                    fragment.scrollToTop()
                }

            }
        }

        if (null == savedInstanceState){
            var intiItemId = R.id.home
            bottomNavigationView.selectedItemId = intiItemId
            showFragment(intiItemId)
        }
    }


    private fun createFragment(clazz: Class<out Fragment>): Fragment {
        var fragment = supportFragmentManager.fragments.find { it.javaClass == clazz }
        if (null == fragment) {
            fragment = when (clazz) {
                HomeFragment::class.java -> HomeFragment.newInstance()
                SystemFragment::class.java -> SystemFragment.newInstance()
                DiscoveryFragment::class.java -> DiscoveryFragment.newInstance()
                NavigationFragment::class.java -> NavigationFragment.newInstance()
                MineFragment::class.java -> MineFragment.newInstance()
                else -> throw IllegalArgumentException("argument ${clazz.simpleName} is illegal")
            }
        }
        return fragment
    }

    private fun showFragment(itemId: Int) {
        val currentFragment = supportFragmentManager.fragments.find {
            it.isVisible && it in mFragments.values
        }
        val targetFragment = mFragments[itemId]
        supportFragmentManager.beginTransaction().apply {
            currentFragment?.let {
                if (it.isVisible) hide(it)
            }
            targetFragment?.let {
                if (it.isAdded) show(it) else add(R.id.container,it)
            }
        }.commit()
    }


    fun animateBottomNavigationView(show:Boolean){
        if (currentBottomNavagtionState == show){
            return
        }
        if (null != bottomNavigationViewAnimtor){
            bottomNavigationViewAnimtor?.cancel()
            bottomNavigationView.clearAnimation()
        }
        currentBottomNavagtionState = show
        val targetY = if (show) 0F else bottomNavigationView.measuredHeight.toFloat()
        val duration = if (show) 225L else 175L
        bottomNavigationViewAnimtor = bottomNavigationView.animate()
            .translationY(targetY)
            .setDuration(duration)
            .setInterpolator(LinearOutSlowInInterpolator())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    bottomNavigationViewAnimtor = null
                }
            })
    }

    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - previousTimeMillis < 2000){
            super.onBackPressed()
        }else{
            Utils.showToast(getString(R.string.click_onece_exit))
            previousTimeMillis = currentTime
        }
    }

    override fun onDestroy() {
        bottomNavigationViewAnimtor?.cancel()
        bottomNavigationView.clearAnimation()
        bottomNavigationViewAnimtor = null
        super.onDestroy()
    }
}