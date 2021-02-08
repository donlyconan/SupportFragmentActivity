package com.utc.singleoperationapp.test

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import com.gtvt.relaxgo.base.framework.ui.BaseFragment
import com.utc.singleoperationapp.R
import kotlinx.android.synthetic.main.fragment_first.*

class FirstFragment : BaseFragment(R.layout.fragment_first), View.OnClickListener {
    companion object {
        val REQUEST_CODE = 101
        val EXTRA_DATA = "_data"
    }

    override fun onFragmentResult(requestCode: Int, resultCode: Int, bundle: Bundle?) {
        super.onFragmentResult(requestCode, resultCode, bundle)

        val message =
            "request: $requestCode, result=$resultCode, data=${bundle?.getString(EXTRA_DATA)}"
        show(message)
    }

    override fun initialize(bundle: Bundle?) {
        super.initialize(bundle)
        btnStartFrag.setOnClickListener(this)
        btnStartFragForResult.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            btnStartFrag -> startFragment(
                R.id.frameLayout,
                SecondFragment::class.java,
                bundleOf(EXTRA_DATA to "Hello Second Fragment []")
            )
            btnStartFragForResult -> startFragmentForResult(
                R.id.frameLayout,
                REQUEST_CODE,
                SecondFragment::class.java,
                bundleOf(EXTRA_DATA to "Hello Second Fragment [RESULT]")
            )
        }
    }


    override fun receive(code: Int, bundle: Bundle?) {
        super.receive(code, bundle)
        val message = "Class: " + this::class.java.simpleName + "  data: " + bundle?.getString(
            EXTRA_DATA
        )
        show(message)
    }


    fun show(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        textMessage2?.text = message
    }

}