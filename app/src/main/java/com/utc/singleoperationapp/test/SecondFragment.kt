package com.utc.singleoperationapp.test

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.gtvt.relaxgo.base.framework.ui.BaseFragment
import com.utc.singleoperationapp.R
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.fragment_second.*

class SecondFragment : BaseFragment(R.layout.fragment_second), View.OnClickListener {


    override fun initialize(bundle: Bundle?) {
        super.initialize(bundle)
        btnClose.setOnClickListener(this)

        arguments?.getString("aaa")?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(v: View?) {
        popBackStack(100, Bundle().apply {
            putString("aaa", "12312313")
        })

    }


}