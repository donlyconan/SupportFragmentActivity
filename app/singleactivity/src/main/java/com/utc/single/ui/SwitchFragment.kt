package com.utc.single.ui


import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment

/**
 * Chuyển đổi giữa các trạng thái của các fragment
 * @author Donly Conan
 * @since 03/02/2021
 */
interface SwitchFragment {

    /**
     *  Chuyển tới một fragment đã được xác định từ trước hoặc sẽ được tạo mới
     *  @param cls Fragment được hướng tới
     *  @param intent Intent được đính kèm
     *  @return Unit
     */
    fun <T : Fragment> startFragment(
        @IdRes frameId: Int,
        cls: Class<T>,
        bundle: Bundle? = null
    ): T

    /**
     * Khởi tạo một bundle mới khi bắt đầu 1 fragment đã tồn tại thay vì khởi tạo lại fragment
     * @param newBundle một bundle mới
     * @return Unit
     */
    fun onNewBundle(newBundle: Bundle?) {}

    /**
     * Đăng ký sự kiện callback khi bắt đầu một fragment khác
     * @param intent dữ liệu được truyền đi khi đăng ký fragment
     * @param requestCode mã code được đăng ký gọi đi
     * @return Unit
     */
    fun <T : Fragment> startFragmentForResult(
        @IdRes frameId: Int,
        requestCode: Int,
        cls: Class<T>,
        bundle: Bundle? = null
    ): T


    /**
     * Cài đặt kết quả để trả về cho một fragment
     * @return Unit
     */

    fun setResult(resultCode: Int, bundle: Bundle?) {}

    /**
     * Nhận kết quả trả về từ một fragment
     * @return Unit
     */
    fun onFragmentResult(requestCode: Int, resultCode: Int, bundle: Bundle?) {}


    /**
     * Quay trở về fragment phía sau hoặc thoát ứng dụng nếu fragment đó là fragment cuối cùng
     * @return Unit
     */
    fun finish() {}

}