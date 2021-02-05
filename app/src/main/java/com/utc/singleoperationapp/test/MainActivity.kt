package com.utc.singleoperationapp.test

import android.os.Bundle
import com.gtvt.relaxgo.base.framework.ui.SupportFragmentActivity
import com.utc.singleoperationapp.R

class MainActivity : SupportFragmentActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun getIdOfFrameLayout(): Int = R.id.frameLayout
}