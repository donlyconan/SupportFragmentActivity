package com.gtvt.relaxgo.base.framework.ui

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

/**
 * Lớp quản lý hoạt
 * @author Donly Conan
 * @since 03/02/2001
 */
abstract class SupportFragmentActivity(layoutId: Int) : AppCompatActivity(layoutId), Initialzation,
    SwitchFragment {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Các khởi tạo truyền thống
        initialize(savedInstanceState)

        // Đăng ký các sự kiên liên quan tới ViewModel
        register(savedInstanceState)

        // Kiểm tra và đăng ký sự kiện nếu sự kiện chưa được đăng ký
        if (checkOrRegisterPermisstion()) {
            runOnIfHasPermission(savedInstanceState)
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
        supportFragmentManager.apply {
            beginTransaction().replace(
                getIdOfFrameLayout(),
                fragTaget,
                bundle,
                fragTaget.simpleName
            ).addToBackStack(fragTaget.name)
                .commit()
        }
    }

    override fun popBackStack() {
        supportFragmentManager.apply {
            popBackStack()
            val lastFragment = fragments.last()
            if (fragments.isEmpty() || lastFragment == null) {
                onBackPressed()
            } else {
//                lastFragment.onActivityResult(r)
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

}