package com.utc.singleoperationapp.ui



import android.os.Bundle

/**
 * Khởi tạo một đối tượng với những công việc chung được thống nhất trong hành động
 * @author Donly Conan
 */
interface Initialzation {


    /**
     * Chạy hàm khởi tao UI
     * @return Unit
     */
    fun initialize(bundle: Bundle?) {}

    /**
     * Đăng ký các sự kiện liên quan tới model
     * @return Unit
     */
    fun register(bundle: Bundle?) {}
}