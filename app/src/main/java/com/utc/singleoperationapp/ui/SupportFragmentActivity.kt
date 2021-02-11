package com.utc.singleoperationapp.ui


import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit

/**
 * Lớp quản lý hoạt động của một activity
 * @author Donly Conan
 * @since 03/02/2001
 */
abstract class SupportFragmentActivity(layoutId: Int) : AppCompatActivity(layoutId), Initialzation {

    private lateinit var firstName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Các khởi tạo truyền thống
        initialize(savedInstanceState)

        // Kiểm tra và đăng ký sự kiện nếu sự kiện chưa được đăng ký
        if (checkOrRegisterPermisstion()) {
            runOnIfHasPermission(savedInstanceState)
        }

        // Đăng ký các sự kiên liên quan tới ViewModel, sự kiện
        register(savedInstanceState)
    }


    open fun <T : BaseFragment> initFragment(@IdRes frameId: Int, cls: Class<T>): BaseFragment? {
        if (supportFragmentManager.backStackEntryCount == 0) {
            firstName = cls.name
            return cls.newInstance().apply {
                supportFragmentManager.beginTransaction()
                    .add(frameId, this, firstName)
                    .setReorderingAllowed(true)
                    .addToBackStack(firstName)
                    .commit()
            }
        } else {
            return supportFragmentManager.findFragmentByTag(firstName) as? BaseFragment
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


    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            if (BaseFragment.flagsUsed == Box.FLAG_BRING_TO_FRONT || BaseFragment.flagsUsed == Box.FLAG_HIDE) {
                showAll()
            }
            supportFragmentManager.popBackStackImmediate()
        } else {
            super.onBackPressed()
        }
    }

    /**
     * Hiển thị tất cả fragment đang bị ẩn
     */
    fun showAll() {
        val sfm = supportFragmentManager
        sfm.commit {
            for (item in sfm.fragments) {
                show(item)
            }
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
    open fun checkOrRegisterPermisstion(): Boolean = false

}

