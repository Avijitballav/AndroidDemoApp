package com.talki.booki.app.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.talki.booki.app.R
import com.talki.booki.app.core.AppConstants
import com.talki.booki.app.ui.home.HomeActivity
import com.talki.booki.app.ui.login.Login
import com.talki.booki.app.utils.OnPauseSlider
import com.talki.booki.app.utils.Prefs

/**
 * Created by AB on 07/09/21.
 */

//This class is created to show Splash


class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //  3sec Delay
        Handler().postDelayed(
            {
                splashTransitionHandler()
            }, 3000
        )
    }

    public override fun onPause() {
        super.onPause()
        OnPauseSlider(this, AppConstants.transitionflagNext) // Screen transition animation
    }

    // Move to next screen
    private fun splashTransitionHandler() {


        if( Prefs.with(this).read(AppConstants.USER_DETAILS,"").equals("")){
            moveToSignUpActivity()
        }else{
            moveToHomeActivity()
        }
    }

    /**
     * Redirection Login screen
     */
    private fun moveToSignUpActivity() {
        val intent = Intent(this@Splash, Login::class.java)
        startActivity(intent)
        finishAffinity()
    }

    /**
     * Redirection Home screen
     */
    private fun moveToHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }
}