package com.utc.single.ui

import android.os.Bundle

interface DirectInteraction {

    /**
     *  Giử dữ liệu từ fragment này sang fragment kia ngay lập tức
     *  @param bundle: Là dữ liệu được truyền đi
     *  @param code: Là mã thông điệp được truyền tải
     *  @return Unit
     */
    fun send(code: Int, bundle: Bundle?) {}


    /**
     *  Hàm có chức năng nhập dữ liệu từ một dữ liệu được giử đi
     *  @param bundle: là dữ liệu được nhận về
     *  @param code: Là mã thông điệp được truyền tải
     *  @return Unit
     */
    fun receive(code: Int, bundle: Bundle?) {}
}