package com.utc.singleoperationapp.test

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import com.utc.singleoperationapp.R
import com.utc.singleoperationapp.ui.BaseFragment
import com.utc.singleoperationapp.ui.Box
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

    override fun onNewBundle(newBundle: Bundle?) {
        super.onNewBundle(newBundle)
        show("On New Intent: " + newBundle?.get(EXTRA_DATA))
    }

    override fun initialize(bundle: Bundle?) {
        super.initialize(bundle)
        btnStartFrag.setOnClickListener(this)
        btnStartFragForResult.setOnClickListener(this)
        btnSingleTask.setOnClickListener(this)
        btnSingleTop.setOnClickListener(this)
        btnClearTop.setOnClickListener(this)
        btnToBack.setOnClickListener(this)
        btnToFont.setOnClickListener(this)
        btnClearHisotry.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            btnStartFrag -> startFragment(
                R.id.frameLayout,
                Box(SecondFragment::class.java, bundleOf(EXTRA_DATA to "Hello Second Fragment []"))
            )
            btnStartFragForResult -> startFragmentForResult(
                R.id.frameLayout,
                REQUEST_CODE,
                Box(SecondFragment::class.java,
                    bundleOf(EXTRA_DATA to "Hello Second Fragment [RESULT]")
                )
            )

            btnSingleTop -> {
                val box = Box(
                    FirstFragment::class.java,
                    bundleOf(EXTRA_DATA to "Single Top"),
                    Box.FLAG_SINGLE_TOP
                )
                startFragment(R.id.frameLayout, box)
            }

            btnSingleTask -> {
                val box = Box(
                    SecondFragment::class.java,
                    bundleOf(EXTRA_DATA to "Single Task"),
                    Box.FLAG_SINGLE_TASK
                )
                startFragment(R.id.frameLayout, box)
            }

            btnClearHisotry -> {
                val box = Box(
                    FirstFragment::class.java,
                    bundleOf(EXTRA_DATA to "New Task"),
                    Box.FLAG_CLEAR_HISTORY
                )
                startFragment(R.id.frameLayout, box)
            }

            btnToBack -> {
                hide()
            }

            btnToFont -> {
                val box = Box(
                    SecondFragment::class.java,
                    bundleOf(EXTRA_DATA to "To Font"),
                    Box.FLAG_SHOW
                )
                startFragment(R.id.frameLayout, box)
            }

            btnClearTop -> {
                val box = Box(
                    SecondFragment::class.java,
                    bundleOf(EXTRA_DATA to "Clear Top"),
                    Box.FLAG_CLEAR_TOP
                )
                startFragment(R.id.frameLayout, box)
            }
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