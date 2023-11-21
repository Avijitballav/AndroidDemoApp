package com.acumen.glow.app.callbacks

/**
 * Created by joydeep on 5/2/18.
 */

interface CallBackButtonClick {
    fun onButtonClick(strButtonText: String)
    fun onButtonClick(ff_pos: Int, ImagePos:Int)
}
