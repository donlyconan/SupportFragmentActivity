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

    companion object {
        val TAG_INIT_FRAGMENT = "init_fragment"
        val FLAG_SINGLE_TOP = Intent.FLAG_ACTIVITY_SINGLE_TOP
        val FLAG_CLEAR_TOP = Intent.FLAG_ACTIVITY_CLEAR_TOP
        val FLAG_CLEAR_TASK = Intent.FLAG_ACTIVITY_CLEAR_TASK
        val FLAG_NEW_TASK = Intent.FLAG_ACTIVITY_NEW_TASK
    }

    private val registerRequest: ArrayList<Fragment> by lazy { ArrayList() }
    private val interactiveChannel: HashMap<Int, DirectInteraction> by lazy { HashMap() }


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
//
//    override fun send(hashCode: Int, code: Int, bundle: Bundle?) {
//        super.send(hashCode, code, bundle)
//        val interaction =
//            supportFragmentManager.findFragmentByTag(hashCode.toString()) as? DirectInteraction
//        interaction?.receive(hashCode, code, bundle)
//    }
//
//
//    override fun receive(hashCode: Int, code: Int, bundle: Bundle?) {
//        super.receive(hashCode, code, bundle)
//        val interaction =
//            supportFragmentManager.findFragmentByTag(hashCode.toString()) as? DirectInteraction
//        interaction?.send(hashCode, code, bundle)
//    }


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

