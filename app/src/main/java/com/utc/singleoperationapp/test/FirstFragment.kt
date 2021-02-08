package com.utc.singleoperationapp.test

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import com.gtvt.relaxgo.base.framework.ui.BaseFragment
import com.utc.singleoperationapp.R
import kotlinx.android.synthetic.main.fragment_first.*

class FirstFragment: BaseFragment(R.layout.fragment_first), View.OnClickListener {


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Toast.makeText(context, data?.getStringExtra("aaa"), Toast.LENGTH_SHORT ).show()
    }

    override fun initialize(bundle: Bundle?) {
        super.initialize(bundle)
        btnOpen.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        startFragmentForResult(R.id.frameLayout, 100,SecondFragment::class.java, bundleOf("aaa" to "Hello!"))
    }


}