package com.utc.singleoperationapp.test

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.os.bundleOf
import com.utc.singleoperationapp.R
import com.utc.singleoperationapp.ui.BaseFragment
import com.utc.singleoperationapp.ui.Box
import kotlinx.android.synthetic.main.fragment_fifth.*

class ThirdFragment : BaseFragment(R.layout.fragment_third) {

    lateinit var frag1: FifthFragment
    lateinit var frag2: FourthFragment

    override fun initialize(bundle: Bundle?) {
        super.initialize(bundle)
        frag1 = startFragment(R.id.frameLayout1, Box(FifthFragment::class.java)) as FifthFragment
        frag2 = startFragment(R.id.frameLayout2, Box(FourthFragment::class.java)) as FourthFragment
    }

    override fun receive(code: Int, bundle: Bundle?) {
        super.receive(code, bundle)

        bundle?.apply {
            if (code == 100) {
                frag1.switchFrag?.isChecked = !getBoolean(FirstFragment.EXTRA_DATA)
                frag1.receive(code, this)
            } else {
                frag2.switchFrag?.isChecked = !getBoolean(FirstFragment.EXTRA_DATA)
                frag2.receive(code, this)
            }
        }

    }
}

class FifthFragment : BaseFragment(R.layout.fragment_fifth) {

    override fun initialize(bundle: Bundle?) {
        super.initialize(bundle)
        switchFrag.setOnClickListener {
            send(
                0,
                bundleOf(FirstFragment.EXTRA_DATA to switchFrag.isChecked)
            )
        }
        btnSendData.setOnClickListener {
            send(0, bundleOf(FirstFragment.EXTRA_DATA to textMessage.text))
        }
    }
}

class FourthFragment : BaseFragment(R.layout.fragment_fourth) {
    override fun initialize(bundle: Bundle?) {
        super.initialize(bundle)
        switchFrag.setOnClickListener {
            send(
                100,
                bundleOf(FirstFragment.EXTRA_DATA to switchFrag.isChecked)
            )
        }
    }

    override fun receive(code: Int, bundle: Bundle?) {
        super.receive(code, bundle)
        Log.d("==TAG", "" + bundle.toString())

        bundle?.get("_data")?.apply {
            Toast.makeText(context, this.toString(), Toast.LENGTH_SHORT)
                .show()
        }

    }
}