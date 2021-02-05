package com.gtvt.relaxgo.base.framework.ui

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * Lớp quản lý hoạt động của một activity
 * @author Donly Conan
 * @since 03/02/2001
 */
abstract class SupportFragmentActivity(layoutId: Int) : AppCompatActivity(layoutId), Initialzation,
    SwitchFragment, FragmentManager.OnBackStackChangedListener {

    private lateinit var listRequest: MutableList<Fragment>

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

    override fun initialize(bundle: Bundle?) {
        super.initialize(bundle)
        listRequest = ArrayList<Fragment>()
        supportFragmentManager.addOnBackStackChangedListener(this)
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
        supportFragmentManager.fragments.last().apply {
            setTargetFragment(fragment, Int.MIN_VALUE)
        }
        startFragment(fragment, fragTaget.name)
    }


    override fun <T : Fragment> startFragmentForResult(
        requestCode: Int, fragTaget: Class<T>,
        bundle: Bundle?
    ) {
        val fragment = fragTaget.newInstance().apply { arguments = bundle }

        val preFragment = supportFragmentManager.fragments.last()
        listRequest.add(preFragment)
        preFragment.setTargetFragment(fragment, requestCode)
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

    override fun onBackStackChanged() {
        listRequest.removeAll { !supportFragmentManager.fragments.contains(it) }
    }




    override fun popBackStack(resultCode: Int, bundle: Bundle?) {
        supportFragmentManager.apply {
            popBackStackImmediate()

            val lastFragment = fragments.last() as BaseFragment
            val requestCode = lastFragment.targetRequestCode

            if (fragments.isEmpty()) {
                onBackPressed()
            } else if (requestCode != Int.MIN_VALUE) {
                lastFragment.onFragmentResult(requestCode, resultCode, bundle)
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

