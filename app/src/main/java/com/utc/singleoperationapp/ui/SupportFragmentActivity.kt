package com.gtvt.relaxgo.base.framework.ui

import android.os.Bundle
import android.util.Log
import androidx.annotation.AnimRes
import androidx.annotation.AnimatorRes
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import java.util.*
import kotlin.collections.HashMap

/**
 * Lớp quản lý hoạt động của một activity
 * @author Donly Conan
 * @since 03/02/2001
 */
abstract class SupportFragmentActivity(layoutId: Int) : AppCompatActivity(layoutId), Initialzation,
    SwitchFragment {

    private val mapRequest: HashMap<Int, Int> by lazy { HashMap<Int, Int>(10) }
    private val stackFragment: Stack<Fragment> by lazy { Stack<Fragment>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Các khởi tạo truyền thống
        initialize(savedInstanceState)

        // Kiểm tra và đăng ký sự kiện nếu sự kiện chưa được đăng ký
        if (checkOrRegisterPermisstion()) {
            runOnIfHasPermission(savedInstanceState)
        }

        // Đăng ký các sự kiên liên quan tới ViewModel
        register(savedInstanceState)

        // Đăng ký fragment đầu tiên
        if (savedInstanceState == null) {
            startFragment(onCreateFirstFragment(), savedInstanceState)
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (checkOrRegisterPermisstion()) {
            runOnIfHasPermission()
        }
    }

    override fun <T : Fragment> startFragment(fragTaget: Class<T>, bundle: Bundle?) {
        super.startFragment(fragTaget, bundle)
        val fragment = fragTaget.newInstance().apply {
            arguments = bundle
        }
        startFragment(fragment, fragTaget.name)
    }


    override fun <T : Fragment> startFragmentForResult(
        requestCode: Int, fragTaget: Class<T>,
        bundle: Bundle?
    ) {
        val preStart = supportFragmentManager.fragments.last()
        mapRequest.put(preStart.hashCode(), requestCode)

        val fragment = fragTaget.newInstance().apply {
            arguments = bundle
        }
        startFragment(fragment, fragTaget.name)
    }


    private fun startFragment(fragment: Fragment, tag: String?) {
        supportFragmentManager.apply {
            beginTransaction()
                .add(getIdOfFrameLayout(), fragment, tag)
                .replace(getIdOfFrameLayout(), fragment, tag)
                .addToBackStack(tag)
                .commit()
        }
    }


    override fun popBackStack(resultCode: Int, bundle: Bundle?) {
        supportFragmentManager.apply {
            if (fragments.size >= 1) {
                popBackStackImmediate()
                val lastFragment = fragments.last() as BaseFragment
                val requestCode = mapRequest.get(lastFragment.hashCode())

                if (fragments.isEmpty()) {
                    onBackPressed()
                } else if (requestCode != null) {
                    lastFragment.onFragmentResult(requestCode, resultCode, bundle)
                    mapRequest.remove(lastFragment.hashCode())
                }
            } else {
                onBackPressed()
            }
        }
    }


    /**
     * Các sự kiện đăng ký với model
     * @return Unit
     */
    open fun register(savedInstanceState: Bundle?) {}


    /**
     * Khởi chạy các hoạt động khi có đủ quyền truy cập
     * @return Unit
     */
    open fun runOnIfHasPermission(savedInstanceState: Bundle? = null) {}


    /**
     * Kiểm tra các quyền cơ bản sau khi khởi tạo
     * @return Unit
     */
    open fun checkOrRegisterPermisstion(): Boolean = true

    /**
     * Trả về kết quả là id của frameLayout cần đăng ký hoạt động
     * @return Int
     */
    @IdRes
    abstract fun getIdOfFrameLayout(): Int

    /**
     * Đăng ký fragment đầu tiên hiển thị trên giao diện
     * @return BaseFragment
     */
    abstract fun onCreateFirstFragment(): BaseFragment
}

