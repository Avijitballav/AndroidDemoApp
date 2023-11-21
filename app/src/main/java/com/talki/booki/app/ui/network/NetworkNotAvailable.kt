package com.talki.booki.app.ui.network

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.talki.booki.app.R
import com.talki.booki.app.ui.home.HomeActivity
import com.talki.booki.app.ui.login.Login
import com.talki.booki.app.utils.AndroidUtility.Companion.isNetworkAvailable
import kotlinx.android.synthetic.main.activity_network_not_available.*

/**
 * Created by AB on 05/07/19.
 */

//This class is created to show if Network is not available

class NetworkNotAvailable : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network_not_available)

        tv_refresh.setOnClickListener {
            if (isNetworkAvailable(this@NetworkNotAvailable)) {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finishAffinity()
            }
        }
    }

    override fun onBackPressed() {

    }
}
