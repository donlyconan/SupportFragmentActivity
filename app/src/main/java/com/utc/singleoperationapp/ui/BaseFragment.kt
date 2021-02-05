package com.gtvt.relaxgo.base.framework.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import java.lang.RuntimeException

/**
 * Cài đặt các thiết lập mặc định cho một Fragment
 * @author Donly Conan
 * @since 04/02/2021
 */
abstract class BaseFragment(layoutId: Int) : Fragment(layoutId), Initialzation, SwitchFragment {

    lateinit var switch: SwitchFragment

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initialize(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity is SwitchFragment) {
            switch = activity as SwitchFragment
        } else {
            throw RuntimeException("Lỗi! Lớp không tương thích.")
        }
    }


    override fun <T : Fragment> startFragment(fragTaget: Class<T>, bundle: Bundle?) {
        switch.startFragment(fragTaget, bundle)
    }


    override fun <T : Fragment> startFragmentForResult(
        requestCode: Int,
        fragTaget: Class<T>,
        bundle: Bundle?
    ) {
        switch.startFragmentForResult(requestCode, fragTaget, bundle)
    }


    override fun popBackStack(resultCode: Int, bundle: Bundle?) {
        switch.popBackStack(resultCode, bundle)
    }


    override fun setOnSwitchFragment(switch: SwitchFragment) {
        this.switch = switch
    }
}