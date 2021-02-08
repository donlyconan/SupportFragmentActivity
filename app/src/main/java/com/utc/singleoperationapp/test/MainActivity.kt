package com.utc.singleoperationapp.test

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.utc.single.ui.SupportFragmentActivity
import com.utc.singleoperationapp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : SupportFragmentActivity(R.layout.activity_main),
    FragmentManager.OnBackStackChangedListener {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.commit {
            replace(R.id.frameLayout, FirstFragment())
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }


    fun onClick(view: View) {
        val prefragment = supportFragmentManager.findFragmentById(R.id.frameLayout)
        when (view) {
            btnAdd -> {
                supportFragmentManager.beginTransaction().apply {
                    if (prefragment == null) {
                        replace(R.id.frameLayout, FirstFragment())
                    } else
                        when (prefragment) {
                            is FirstFragment -> prefragment.startFragment(R.id.frameLayout, SecondFragment::class.java)
                            is SecondFragment -> prefragment.startFragment(R.id.frameLayout, ThirdFragment::class.java)
                            is ThirdFragment -> prefragment.startFragment(R.id.frameLayout, FirstFragment::class.java)
                        }
                    addToBackStack(null)
                }.commit()
            }
            btnPop -> {
                supportFragmentManager.popBackStack()
            }

            btnRemove -> {
                supportFragmentManager.beginTransaction().apply {
                    supportFragmentManager.findFragmentById(R.id.frameLayout)?.let {
                        remove(it)
                        addToBackStack("Remove: " + it::class.java.simpleName)
                    }
                }.commit()
            }
        }

        logInfo()
    }

    fun logInfo(): StringBuilder = StringBuilder().apply {
        for (i in 0 until supportFragmentManager.backStackEntryCount) {
            append((supportFragmentManager.getBackStackEntryAt(i))::class.java.simpleName).append("\n")
        }
        Log.d(tag, this.toString())
    }

    val tag = "==Tag"
    override fun onBackStackChanged() {
        Log.d(tag, "Back stack: " + supportFragmentManager.backStackEntryCount.toString())
    }
}