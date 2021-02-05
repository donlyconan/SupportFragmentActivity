package com.gtvt.relaxgo.base.framework.ui

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
}