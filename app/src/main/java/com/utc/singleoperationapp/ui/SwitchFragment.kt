package com.gtvt.relaxgo.base.framework.ui

import android.os.Bundle
import androidx.fragment.app.Fragment

/**
 * Chuyển đổi giữa các trạng thái của các fragment
 * @author Donly Conan
 * @since 03/02/2021
 */
interface SwitchFragment {

    /**
     *  Chuyển tới một fragment đã được xác định từ trước hoặc sẽ được tạo mới
     *  @param fragTaget Fragment được hướng tới
     *  @param intent Intent được đính kèm
     *  @return Unit
     */
    fun <T : Fragment> startFragment(fragTaget: Class<T>, bundle: Bundle? = null) {}


    /**
     * Đăng ký sự kiện callback khi bắt đầu một fragment khác
     * @param intent dữ liệu được truyền đi khi đăng ký fragment
     * @param requestCode mã code được đăng ký gọi đi
     * @return Unit
     */
    fun <T : Fragment> startFragmentForResult(requestCode: Int, bundle: Bundle? = null) {}


    /**
     * Quay trở về fragment phía sau hoặc thoát ứng dụng nếu fragment đó là fragment cuối cùng
     * @return Unit
     */
    fun popBackStack() {}

    /**
     * Đăng ký sự kiện chuyển đổi giữa các fragment
     * @param switch: tham chiếu đến triệu hồi sự kiện
     * @return Unit
     */
    fun setOnSwitchFragment(switch: SwitchFragment) {}

    /**
     *  Dùng để nhận một intent được quăng sang từ một instance
     *  @param Bundle Nơi chứa dữ liệu truyền vào
     *  @return Unit
     */
    fun onAttack(bundle: Bundle?) {}

}