package com.gtvt.relaxgo.base.framework.ui

import android.content.Intent
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import com.utc.singleoperationapp.test.FirstFragment
import com.utc.singleoperationapp.ui.DirectInteraction

/**
 * Lớp quản lý hoạt động của một activity
 * @author Donly Conan
 * @since 03/02/2001
 */
abstract class SupportFragmentActivity(layoutId: Int) : AppCompatActivity(layoutId), Initialzation {


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


    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStackImmediate()
        } else {
            super.onBackPressed()
        }
    }

    /**********************************************************************************************/


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

}

