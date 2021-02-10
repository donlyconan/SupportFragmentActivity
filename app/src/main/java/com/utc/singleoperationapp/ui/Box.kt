package com.utc.singleoperationapp.ui


import android.os.Bundle
import androidx.fragment.app.Fragment

class Box<T : Fragment> {
    companion object {
        const val FLAG_CLEAR_TOP = 0xfb007
        const val FLAG_CLEAR_HISTORY = 0xfb008
        const val FLAG_SHOW = 0xfb001
        const val FLAG_SINGLE_TOP = 0xfb003
        const val FLAG_SINGLE_TASK = 0xfb005
        const val FLAG_STANDARD = 0xfb006
    }

    lateinit var cls: Class<T>
    var bundle: Bundle? = null
    var resultCode: Int = Int.MIN_VALUE
    var flag: Int = FLAG_STANDARD

    constructor(cls: Class<T>, bundle: Bundle? = null) {
        this.bundle = bundle
        this.cls = cls
    }

    constructor(cls: Class<T>, bundle: Bundle? = null, flag: Int) {
        this.bundle = bundle
        this.cls = cls
        this.flag = flag
    }

    constructor(resultCode: Int, bundle: Bundle? = null) {
        this.bundle = bundle
        this.resultCode = resultCode
    }

    fun getClassName(): String {
        return cls.name ?: "Class Anonymous"
    }
}