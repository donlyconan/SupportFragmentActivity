package com.utc.singleoperationapp.test

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import com.gtvt.relaxgo.base.framework.ui.BaseFragment
import com.utc.singleoperationapp.R
import kotlinx.android.synthetic.main.fragment_second.*

class SecondFragment : BaseFragment(R.layout.fragment_second), View.OnClickListener {


    override fun initialize(bundle: Bundle?) {
        super.initialize(bundle)

        val message = "data=${arguments?.getString(FirstFragment.EXTRA_DATA)}"
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        btnClose.setOnClickListener(this)
        btnSendData.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            btnClose -> {
                setResult(100, bundleOf(FirstFragment.EXTRA_DATA to "Hello A"))
                finish()
            }
            btnSendData -> {
                send(100, bundleOf(FirstFragment.EXTRA_DATA to "Trao đổi trực tiếp!"))
            }
        }

    }
}