package moe.htc.deadlinelovers.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ViewPagerAdapter(supportFragmentManager: FragmentManager, private val map: Map<String, Fragment>) : FragmentStatePagerAdapter(supportFragmentManager) {

    private val titles = map.keys.toList()
    private val fragments = map.values.toList()

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = map.size

    override fun getPageTitle(position: Int): CharSequence? = titles[position]
}