package com.utc.singleoperationapp.test

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import com.gtvt.relaxgo.base.framework.ui.BaseFragment
import com.utc.singleoperationapp.R
import kotlinx.android.synthetic.main.fragment_first.*

class FirstFragment : BaseFragment(R.layout.fragment_first), View.OnClickListener {

    override fun onFragmentResult(requestCode: Int, resultCode: Int, bundle: Bundle?) {
        super.onFragmentResult(requestCode, resultCode, bundle)

        Toast.makeText(context, " ", Toast.LENGTH_SHORT).show()
    }


    override fun initialize(bundle: Bundle?) {
        super.initialize(bundle)
        btnStartFragment.setOnClickListener(this)
        btnStartFragmentForResult.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v) {
            btnStartFragment -> startFragment(SecondFragment::class.java)
            btnStartFragmentForResult -> startFragmentForResult(
                100,
                FirstFragment::class.java,
                bundleOf("aaa" to "Hello")
            )
        }
    }

}