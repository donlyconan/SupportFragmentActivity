package com.utc.singleoperationapp.test

import android.os.Bundle
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import com.gtvt.relaxgo.base.framework.ui.BaseFragment
import com.utc.singleoperationapp.R
import kotlinx.android.synthetic.main.fragment_fifth.*

class ThirdFragment : BaseFragment(R.layout.fragment_third) {

    lateinit var frag1: FifthFragment
    lateinit var frag2: FourthFragment

    override fun initialize(bundle: Bundle?) {
        super.initialize(bundle)

        frag1 = startFragment(R.id.frameLayout1, FifthFragment::class.java)  as FifthFragment
        frag2 = startFragment(R.id.frameLayout2, FourthFragment::class.java)  as FourthFragment
    }

    override fun receive(code: Int, bundle: Bundle?) {
        super.receive(code, bundle)

        bundle?.apply {
            if (code == 100) {
                frag1.switchFrag?.isChecked = !getBoolean("_data")
            } else {
                frag2.switchFrag?.isChecked = !getBoolean("_data")
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
                bundleOf("_data" to switchFrag.isChecked)
            )
        }
    }
}

class FourthFragment : BaseFragment(R.layout.fragment_fourth) {
    override fun initialize(bundle: Bundle?) {
        super.initialize(bundle)
        switchFrag.setOnClickListener {
            send(
                100,
                bundleOf("_data" to switchFrag.isChecked)
            )
        }
    }
}