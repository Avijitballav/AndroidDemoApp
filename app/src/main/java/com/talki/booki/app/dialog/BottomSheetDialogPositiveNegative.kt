package com.talki.booki.app.dialog


import android.app.Activity
import android.content.Context
import android.widget.Button
import android.widget.TextView
import com.acumen.glow.app.callbacks.CallBackButtonClick


import com.google.android.material.bottomsheet.BottomSheetDialog
import com.talki.booki.app.R


/**
 * Created by kamail on 15/11/17.
 */

class BottomSheetDialogPositiveNegative {

    private var mContext: Context? = null
    private var mActivity: Activity? = null
    private var mStrMsg: String? = null
    private var mStrNegative: String? = null
    private var mStrPositive: String? = null
//    private val buttonClick: CallBackButtonClick? = null
    var buttonClick: CallBackButtonClick? = null

    constructor(
        context: Context, activity: Activity,
        strMsg: String,
        strNegative: String,
        strPositive: String
    ) {
        this.mContext = context
        this.mActivity = activity
        this.mStrMsg = strMsg
        this.mStrNegative = strNegative
        this.mStrPositive = strPositive

        // call bottom sheet dialog
        callBottomSheetDialog()
    }

    constructor(
        context: Context, activity: Activity,
        buttonClick: CallBackButtonClick,
        strMsg: String,
        strNegative: String,
        strPositive: String
    ) {
        this.mContext = context
        this.mActivity = activity
        this.buttonClick = buttonClick
        this.mStrMsg = strMsg
        this.mStrNegative = strNegative
        this.mStrPositive = strPositive

        // call bottom sheet dialog
        callBottomSheetDialog()
    }


    private fun callBottomSheetDialog() {
        val mBottomSheetDialog = BottomSheetDialog(mContext!!)
        val sheetView =
            mActivity!!.layoutInflater.inflate(R.layout.bottom_sheet_dialog_positive_negative, null)
        mBottomSheetDialog.setContentView(sheetView)
        mBottomSheetDialog.show()

        val tvMsg: TextView
        val btNegative: Button
        val btPositive: Button

        tvMsg = sheetView.findViewById(R.id.tvMsg)
        btNegative = sheetView.findViewById(R.id.btNegative)
        btPositive = sheetView.findViewById(R.id.btPositive)

        tvMsg.text = mStrMsg
        btNegative.text = mStrNegative
        btPositive.text = mStrPositive

        btNegative.setOnClickListener { view ->
            mBottomSheetDialog.dismiss()
            if (buttonClick == null) {
                (mActivity as CallBackButtonClick).onButtonClick(mStrNegative!!)
            } else {
                buttonClick?.onButtonClick(mStrNegative!!)
            }
        }

        btPositive.setOnClickListener { view ->
            mBottomSheetDialog.dismiss()
            if (buttonClick == null) {
                (mActivity as CallBackButtonClick).onButtonClick(mStrPositive!!)
            } else {
                buttonClick?.onButtonClick(mStrPositive!!)
            }
        }
    }


}