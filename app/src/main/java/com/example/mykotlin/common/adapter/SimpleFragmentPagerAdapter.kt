package com.example.mykotlin.common.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * Created by zlp on 2020/8/11 0011.
 */
open class SimpleFragmentPagerAdapter(
    fm: FragmentManager,
    private val mFragments: List<Fragment>,
    private val titles: List<CharSequence>? = null
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    init {
        require(!(titles != null && titles.size != mFragments.size)) {
            "Fragments and titles list size must match!"
        }
    }

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

    override fun getCount(): Int {
        return mFragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles?.get(position)
    }

    /**
     * 默认为位置，子类需要覆盖默认，才能保证对应位置的fragment可以改变成其他的fragment，
     * 不然同一位置一直都是最初添加的那个fragment，其他的添加不进去
     * {@link FragmentStatePagerAdapter#getItem(int)}
     */
    override fun getItemId(position: Int): Long {
        return mFragments[position].hashCode().toLong()
    }
}