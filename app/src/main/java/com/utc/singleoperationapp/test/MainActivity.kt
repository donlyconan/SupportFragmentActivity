package com.utc.singleoperationapp.test

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.utc.singleoperationapp.R
import com.utc.singleoperationapp.ui.BaseFragment
import com.utc.singleoperationapp.ui.Box
import com.utc.singleoperationapp.ui.SupportFragmentActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : SupportFragmentActivity(R.layout.activity_main),
    FragmentManager.OnBackStackChangedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        supportFragmentManager.addOnBackStackChangedListener(this)

        initFragment(R.id.frameLayout, FirstFragment::class.java)
    }


    fun onClick(view: View) {
        val frag = supportFragmentManager.findFragmentById(R.id.frameLayout) as? BaseFragment

        when (view) {
            btnAdd -> {
                if (frag == null) {
                    initFragment(R.id.frameLayout, FirstFragment::class.java)
                } else {
                    when (frag) {
                        is FirstFragment -> frag.startFragment(
                            R.id.frameLayout,
                            Box(SecondFragment::class.java)
                        )
                        is SecondFragment -> frag.startFragment(
                            R.id.frameLayout,
                            Box(ThirdFragment::class.java)
                        )
                        is ThirdFragment -> frag.startFragment(
                            R.id.frameLayout,
                            Box(FirstFragment::class.java)
                        )
                    }
                }
            }
            btnPop -> {
                supportFragmentManager.popBackStack()
            }

            btnRemove -> {
                supportFragmentManager.commit {
                    remove(supportFragmentManager.run { fragments.get(fragments.size - 1) })
                    addToBackStack("Remove: ")
                }
            }
        }

    }

    fun logInfo(): StringBuilder = StringBuilder().apply {
        append("Total: ").append(supportFragmentManager.backStackEntryCount).append("\n")
        for (i in 0 until supportFragmentManager.backStackEntryCount) {
            val data = supportFragmentManager.getBackStackEntryAt(i)
            append(data.name).append("\n")
        }
        Log.d(tag, this.toString())
    }

    val tag = "==TAG"

    override fun onBackStackChanged() {
        logInfo()
    }

}