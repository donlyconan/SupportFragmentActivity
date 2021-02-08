package com.utc.singleoperationapp.test

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import com.gtvt.relaxgo.base.framework.ui.BaseFragment
import com.utc.singleoperationapp.R
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.fragment_second.*

class SecondFragment : BaseFragment(R.layout.fragment_second), View.OnClickListener {


    override fun onAttach(context: Context) {
        super.onAttach(context)
        Toast.makeText(context, arguments?.getString("aaa"), Toast.LENGTH_SHORT ).show()
    }

    override fun initialize(bundle: Bundle?) {
        super.initialize(bundle)

        Toast.makeText(context, bundle?.getString("aaa"), Toast.LENGTH_SHORT ).show()

        btnClose.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        setResult(100, bundleOf("aaa" to "Hello A"))
        finish()
    }
}