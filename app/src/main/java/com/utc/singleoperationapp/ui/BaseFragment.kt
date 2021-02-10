package com.utc.singleoperationapp.ui

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.utc.singleoperationapp.R

/**
 * Cài đặt các thiết lập mặc định cho một Fragment
 * @author Donly Conan
 * @since 04/02/2021
 */
abstract class BaseFragment(layoutId: Int) : Fragment(layoutId), Initialzation, DirectInteraction,
    SwitchFragment {

    companion object {
        var flagsUsed = 0
            private set

        // Đánh dấu cờ đang được sử dụng của fragment
        val ENTER_LEFT = "enter-left"
        val ENTER_RIGHT = "enter-right"
        val EXIT_LEFT = "exit-left"
        val EXIT_RIGHT = "exit-right"

        val Animators = mapOf<String, @IdRes Int>(
            ENTER_RIGHT to R.anim.slide_in,
            ENTER_LEFT to R.anim.fade_out,
            EXIT_LEFT to R.anim.fade_in,
            EXIT_RIGHT to R.anim.slide_out
        )
    }

    private data class Result(var resultCode: Int, var bundle: Bundle?)

    private var result: Result? = null
    private var interaction: DirectInteraction? = null
    protected var animators = Animators


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Khởi tạo các cài đặt ban đầu cho fragment
        initialize(savedInstanceState)
        // Chạy trình đăng ký sự kiện
        register(savedInstanceState)
    }


    override fun onNewBundle(newBundle: Bundle?) {
        super.onNewBundle(newBundle)
        arguments?.putAll(newBundle)
    }


    override fun <T : Fragment> startFragment(@IdRes frameId: Int, box: Box<T>): T {
        val fragment = box.cls.newInstance().apply {
            arguments = box.bundle
            (this as? BaseFragment)?.setDirectInteraction(this@BaseFragment)
        }
        return handleFlag(frameId, fragment, box)
    }


    override fun <T : Fragment> startFragmentForResult(
        @IdRes frameId: Int,
        requestCode: Int,
        box: Box<T>
    ): T {
        // Tạo instance cho fragment
        val fragment = box.cls.newInstance().apply {
            arguments = box.bundle
            (this as? BaseFragment)?.setDirectInteraction(this@BaseFragment)
            setTargetFragment(this@BaseFragment, requestCode)
        }
        return handleFlag(frameId, fragment, box)
    }

    /**
     * Hiển thị tất cả fragment đang bị ẩn
     */
    fun showAll() {
        val sfm = requireActivity().supportFragmentManager
        sfm.commit {
            for (item in sfm.fragments) {
                show(item)
            }
        }
    }

    override fun show() {
        requireActivity().supportFragmentManager.beginTransaction()
            .show(this)
            .commit()
    }

    override fun hide() {
        requireActivity().supportFragmentManager.beginTransaction()
            .hide(this)
            .commit()
    }


    /**
     *  Xử lý các hành động liên quan tới flag
     *  Các cờ có thể có là: SINGLE_TOP, NEW_TASK, BRING_TO_FONT, SEND_TO_BACK
     *  @param frameId
     *  @param fragment
     *  @param box
     *  @return Unit
     */
    @Suppress("UNCHECKED_CAST")
    private fun <T : Fragment> handleFlag(@IdRes frameId: Int, fragment: T, box: Box<T>): T {
        val sfm = requireActivity().supportFragmentManager
        val target = sfm.findFragmentByTag(box.getClassName()) as? BaseFragment
        var prefragment = sfm.findFragmentById(frameId) as? BaseFragment

        // Kiểm tra hoạt động của cờ
        if (flagsUsed == Box.FLAG_SHOW) {
            // Xoá cờ và hiển thi toàn bộ fragment
            showAll()
        }

        when (box.flag) {
            /**
             * Nếu 1 fragment đang được thực thi và lưu lại trạng thái ở backstack
             * thì nó sẽ được hiển thị nhưng nó vẫn nằm ở đúng vị trí ban đầu của nó
             * - Hàm này sẽ gọi đên onNewBundle
             * Giả định: A > B > C > D
             * Show:     A
             * Kết quả:  B > C > D > A
             */
            Box.FLAG_SHOW -> {
                if (target == null) {
                    // Bắt đầu 1 fragment mới khi target null
                    startNewFragment(frameId, fragment, box.getClassName())
                } else {
                    sfm.commit {
                        for (item in sfm.fragments) {
                            if (item::class.java != target::class.java) {
                                hide(item)
                            }
                        }
                    }
                    target.show()
                    target.onNewBundle(box.bundle)
                    prefragment = target
                }
            }

            /**
             * Bắt đầu một fragment và thay thế vị trí đầu tiên của Fragment trên đầu stack
             * Giả định: A > B > C
             * Start:    D
             * Kết quả:  A > B > D
             */
            Box.FLAG_CLEAR_TOP -> {
                if (prefragment != null) {
                    sfm.popBackStack()
                }
                // Bắt đầu 1 fragment
                startNewFragment(frameId, fragment, box.getClassName())
            }

            /**
             * Viết lại lịch sử cho back stack
             * Giả định: A > B > C
             * Start:    D
             * Kết quả:  D
             */
            Box.FLAG_CLEAR_HISTORY -> {
                for (i in 0..sfm.backStackEntryCount - 1) {
                    sfm.popBackStack()
                }
                // Bắt đầu 1 fragment
                startNewFragment(frameId, fragment, box.getClassName())
            }

            /**
             * Đưa một fragment xuống cuối stack và hiển thị fragment phía sau nó đồng thời gọi
             * tới hàm newBundle của fragment đó
             * Giả định: A > B > C > D
             * Start:    B
             * Kết quả:  A > B
             */
            Box.FLAG_SINGLE_TASK -> {
                if (target == null) {
                    startNewFragment(frameId, fragment, box.getClassName())
                } else {
                    sfm.popBackStack(box.getClassName(), 0)
                    prefragment = sfm.findFragmentById(frameId) as? BaseFragment
                    prefragment?.onNewBundle(box.bundle)
                }
            }

            /**
             * Đưa một fragment xuống cuối stack và hiển thị fragment phía sau nó đồng thời gọi
             * tới hàm newBundle của fragment đó
             * Start:    D
             * Giả định: A > B > C > D -> A > B > C > D
             * Giả định: A > B > D > C -> A > B > D > C > D
             */
            Box.FLAG_SINGLE_TOP -> {
                if (prefragment != null && box.cls == prefragment::class.java) {
                    prefragment.onNewBundle(box.bundle)
                } else {
                    startNewFragment(frameId, fragment, box.getClassName())
                }
            }
            /**
             * Ở cờ mặc định sẽ khởi tạo một instance của fragment và thêm vào stack
             * Start:    D
             * Giả định: A > B > C > D
             * Kết quả:  A > B > C > D > D
             */
            else -> {
                startNewFragment(frameId, fragment, box.getClassName())
            }
        }

        flagsUsed = box.flag
        return prefragment?.run { this as T } ?: fragment
    }

    private fun <T : Fragment> startNewFragment(@IdRes frameId: Int, fragment: T, tag: String) {
        requireActivity().supportFragmentManager.commit {
            // Custom animation
            setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out, R.anim.fade_in, R.anim.slide_out
            )
            add(frameId, fragment, tag)
            setReorderingAllowed(true)
            addToBackStack(tag)
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

    override fun send(code: Int, bundle: Bundle?) {
        super.send(code, bundle)
        this.interaction?.receive(code, bundle)
    }


    fun setDirectInteraction(interaction: DirectInteraction?) {
        this.interaction = interaction
    }
}