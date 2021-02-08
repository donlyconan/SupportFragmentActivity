package com.utc.single.ui


import android.os.Bundle

/**
 * Khởi tạo một đối tượng với những công việc chung được thống nhất trong hành động
 * @author Donly Conan
 */
interface Initialzation {


    /**
     * Chạy hàm khởi tạo sau @OnCreateView
     * @return Unit
     */
    fun initialize(bundle: Bundle?) {}

    /**
     * Đăng ký các sự kiện liên quan tới model
     * @return Unit
     */
    fun register(bundle: Bundle?) {}
}