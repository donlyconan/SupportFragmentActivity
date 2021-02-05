package com.gtvt.relaxgo.base.framework.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

/**
 * Cài đặt các thiết lập mặc định cho một Fragment
 * @author Donly Conan
 * @since 04/02/2021
 */
abstract class BaseFragment(layoutId: Int) : Fragment(layoutId), Initialzation,
    View.OnClickListener, SwitchFragment {

    lateinit var switchFragment: SwitchFragment

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initialize(savedInstanceState)
    }


    override fun onClick(v: View?) {}

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity is SwitchFragment) {
            switchFragment = activity as SwitchFragment
        }
    }

    override fun <T : Fragment> startFragment(fragTaget: Class<T>, bundle: Bundle?) =
        switchFragment.startFragment(fragTaget, bundle)

    override fun popBackStack() = switchFragment.popBackStack()

    override fun setOnSwitchFragment(switch: SwitchFragment) {
        this.switchFragment = switch
    }
}