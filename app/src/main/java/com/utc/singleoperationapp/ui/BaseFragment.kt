package com.gtvt.relaxgo.base.framework.ui

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.utc.singleoperationapp.ui.DirectInteraction

/**
 * Cài đặt các thiết lập mặc định cho một Fragment
 * @author Donly Conan
 * @since 04/02/2021
 */
abstract class BaseFragment(layoutId: Int) : Fragment(layoutId), Initialzation, DirectInteraction,
    SwitchFragment {

    private data class Result(var resultCode: Int, var bundle: Bundle?)

    private var result: Result? = null
    private var interaction: DirectInteraction? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Khởi tạo các cài đặt ban đầu cho fragment
        initialize(savedInstanceState)
        // Chạy trình đăng ký sự kiện
        register(savedInstanceState)
    }


    /***************************************************************************************************/

    override fun <T : Fragment> startFragment(@IdRes frameId: Int, cls: Class<T>, bundle: Bundle?) {
        val fragment = cls.newInstance().apply { arguments = bundle }
        startNewFragment(frameId, fragment, cls.name)
    }


    override fun <T : Fragment> startFragmentForResult(
        @IdRes frameId: Int,
        requestCode: Int,
        cls: Class<T>,
        bundle: Bundle?
    ) {
        // Tạo instance cho fragment
        val fragment = cls.newInstance().apply {
            arguments = bundle
            setTargetFragment(this@BaseFragment, requestCode)
        }
        startNewFragment(frameId, fragment, cls.name)
    }

    private fun startNewFragment(@IdRes frameId: Int, fragment: Fragment, tag: String) {
        requireActivity().supportFragmentManager.commit {
            replace(frameId, fragment, tag)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }


    override fun setResult(resultCode: Int, bundle: Bundle?) {
        super.setResult(resultCode, bundle)
        result = Result(resultCode, bundle)
    }

    override fun finish() {
        val sfm = requireActivity().supportFragmentManager
        if (result != null && targetFragment?.isDetached == false && targetFragment is BaseFragment) {
            val base = targetFragment as BaseFragment
            result?.apply {
                base.onFragmentResult(targetRequestCode, resultCode, bundle)
            }
        }
        sfm.popBackStack()
    }

    override fun send(hashCode: Int, code: Int, bundle: Bundle?) {
        super.send(hashCode, code, bundle)
        interaction?.receive(hashCode, code, bundle)
    }


    fun setDirectInteraction(interaction: DirectInteraction) {
        this.interaction = interaction
    }


}