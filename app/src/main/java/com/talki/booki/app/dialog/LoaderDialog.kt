package com.acumen.glow.app.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window

import com.github.ybq.android.spinkit.SpinKitView
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.DoubleBounce
import com.talki.booki.app.R


/**
 * Created by Avijit on 10,Sep,2021
 */
class LoaderDialog(context: Context): Dialog(context) {
    lateinit var spinKitView: SpinKitView
    lateinit var doubleBounce:Sprite

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.loader)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCancelable(false)
        initView()
    }

    private fun initView() {
        spinKitView = findViewById(R.id.spin_kit)
        doubleBounce = DoubleBounce()
        spinKitView.setIndeterminateDrawable(doubleBounce)
    }

    override fun setOnCancelListener(listener: DialogInterface.OnCancelListener?) {
        super.setOnCancelListener(listener)
        spinKitView.visibility= View.GONE
    }

    override fun setOnShowListener(listener: DialogInterface.OnShowListener?) {
        super.setOnShowListener(listener)
        spinKitView.visibility = View.VISIBLE
    }
}