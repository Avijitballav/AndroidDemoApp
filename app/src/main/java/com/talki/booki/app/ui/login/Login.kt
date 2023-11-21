package com.talki.booki.app.ui.login


import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.talki.booki.app.R
import com.talki.booki.app.core.AppConstants
import com.talki.booki.app.core.AppConstants.Bearer
import com.talki.booki.app.core.TalkiBookiApplication
import com.talki.booki.app.databinding.ActivityLoginBinding
import com.talki.booki.app.model.LanguageBody

import com.talki.booki.app.ui.privacy.PrivacyPolicy
import com.talki.booki.app.utils.AndroidUtility
import com.talki.booki.app.utils.NetworkResult
import com.talki.booki.app.utils.OnPauseSlider
import com.talki.booki.app.utils.Prefs
import dagger.hilt.android.AndroidEntryPoint
import java.security.MessageDigest
import java.util.*



/**
 * Created by AB on 09/09/21.
 */

//This class is created to show Login

@AndroidEntryPoint
class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var fbCallbackManager: CallbackManager
    private lateinit var fbLoginManager: LoginManager
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    private val loginViewModel by viewModels<LoginViewModel>()
    private val readFBContentEmail: String = "email"
    private val readFBContentPublicProfile: String = "public_profile"
    private val RC_GOOGLE_SIGN_IN: Int = 9001
    private val TAG: String = "TalkiBooki"

    private var login_type = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        initVariable()
        initListeners()
        printHashKey()
    }

    public override fun onPause() {
        super.onPause()
        OnPauseSlider(this, AppConstants.transitionflagNext) // Screen transition animation
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleGoogleSignInResult(task)
        } else {
            fbCallbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }

    // initialize methods
    private fun initVariable() {
        fbCallbackManager = CallbackManager.Factory.create()
        fbLoginManager = LoginManager.getInstance()

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(AppConstants.GOOGLE_SIGN_IN_WEB_CLIENT_ID)
                .requestEmail()
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
    }


    // Facebook and G+ click listener
    private fun initListeners() {


        binding.ivFacebooklogin.setOnClickListener {

            if(AndroidUtility.isNetworkAvailable(this)) {

                if (binding.chPp.isChecked()) {
                    fbLoginClickHandler()
                } else {
                    AndroidUtility.showSnackBar(this, "Please accept privacy policy")
                }

            }else{
                AndroidUtility.showSnackBar(this, getString(R.string.please_check_internet))
            }


        }

        binding.ivGooglelogin.setOnClickListener {

            if(AndroidUtility.isNetworkAvailable(this)) {
                if (binding.chPp.isChecked()) {
                    googleSignInClickHandler()
                } else {
                    AndroidUtility.showSnackBar(this, "Please accept privacy policy")
                }
            }else{
                AndroidUtility.showSnackBar(this, getString(R.string.please_check_internet))
            }
        }

        binding.tvPp.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, PrivacyPolicy::class.java))
        })
    }

    // ****** G+ Sign in Response
    private fun handleGoogleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val googleUserAccount = completedTask.getResult(
                ApiException::class.java
            )

            // Signed in successfully
            googleUserAccount?.let { profileData ->
                login_type= AppConstants.login_type_g

                val googleId = profileData.id ?: ""
                val googleDisplayName = AndroidUtility.optionalBlankText(profileData.displayName)
                val googleFirstName = profileData.givenName ?: ""
                val googleLastName = profileData.familyName ?: ""
                val googleEmail = profileData.email ?: ""
                val googleProfilePicURL = profileData.photoUrl.toString()
                val googleIdToken = profileData.idToken ?: ""

                Log.i("Google ID", googleId)
                Log.i("Google Display Name", googleDisplayName)
                Log.i("Google First Name", googleFirstName)
                Log.i("Google Last Name", googleLastName)
                Log.i("Google Email", googleEmail)
                Log.i("Google Profile Pic URL", googleProfilePicURL)
                Log.i("Google ID Token", googleIdToken)

                if (googleId.isNotEmpty()) {
                    callLoginAPI(
                        userEmail = googleEmail,
                        userName = googleDisplayName,
                        userPhone = "",
                        fb_id = ""
                    )
                }
            }
        } catch (e: ApiException) {
            // Sign in was unsuccessful
            Log.e(
                "failed code=", e.statusCode.toString()
            )
        }
    }

    /**
     * Print HashKey For
     * Facebook-Android Sign In
     * Integration
     */
    private fun printHashKey() {
        try {
            val info = packageManager.getPackageInfo(
                "com.talki.booki.app",
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.e(
                    getString(R.string.app_name), "KeyHash: " + Base64.encodeToString(
                        md.digest(),
                        Base64.DEFAULT
                    )
                )
            }
        } catch (e: PackageManager.NameNotFoundException) {

        } catch (e: Exception) {

        }
    }

    /**
     * Click Handler for Facebook
     */
    private fun fbLoginClickHandler() {

        fbLoginManager.logInWithReadPermissions(this@Login, listOf(readFBContentEmail))
        fbLoginManager.registerCallback(fbCallbackManager, object : FacebookCallback<LoginResult> {
            override fun onCancel() {
                Log.e(getString(R.string.app_name), "FB onCancel Callback")
            }

            override fun onError(error: FacebookException) {
                Log.e(getString(R.string.app_name), "${error?.message}")
                Toast.makeText(this@Login, "${error?.message} , Try again", Toast.LENGTH_SHORT)
                    .show()
                if (AccessToken.getCurrentAccessToken() != null) {
                    LoginManager.getInstance().logOut()
                }

            }

            override fun onSuccess(result: LoginResult) {

                result?.let { fbLoginResult ->
                    Log.e(TAG, "FB ACCESS TOKEN: ${fbLoginResult.accessToken}")

                    val request = GraphRequest.newMeRequest(
                        fbLoginResult.accessToken
                    ) { obj, response ->
                        login_type = AppConstants.login_type_fb

                        val userProfile: Profile? = Profile.getCurrentProfile()
                        Log.d(getString(R.string.app_name), "" + Gson().toJson(userProfile))

                        val fbUserId: String = AndroidUtility.optionalBlankText(userProfile?.id)
                        val fbUserName: String = AndroidUtility.optionalBlankText(userProfile?.name)
                        val fbUserEmail: String =
                            AndroidUtility.optionalBlankText(obj?.optString("email"))



                        Log.e(
                            TAG,
                            "FBUserId: $fbUserId, FBUserName: $fbUserName, FBUserEmail: $fbUserEmail"
                        )
                        callLoginAPI(
                            userEmail = fbUserEmail,
                            userName = fbUserName,
                            userPhone = "",
                            fb_id = fbUserId
                        )
                    }
                    val parameters = Bundle()
                    parameters.putString("fields", "id,name,email")
                    request.parameters = parameters
                    request.executeAsync()

                    /* val userProfile: Profile? = Profile.getCurrentProfile()
                     userProfile?.let { fbProfileData ->
                         val fbUserId: String? = fbProfileData.id
                         val fbUserName: String? = fbProfileData.name
                         val fbUserProfileLink: Uri? = fbProfileData.linkUri
                         val fbUserPictureLink: Uri? = fbProfileData.pictureUri
                     }

                     callLoginAPI(
                         userEmail =
                     )*/
                }
            }

        })
    }

    private fun googleSignInClickHandler() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(
            signInIntent, RC_GOOGLE_SIGN_IN
        )
    }

    private fun googleSignOutClickHandler() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this) {
                    // Update your UI here
                }
    }


/*
    Login API calling
 */
    fun callLoginAPI(
    userEmail: String,
    userName: String,
    userPhone: String,
    fb_id: String
) {

        var email = "$userEmail"
//        var email = "avijitballav@matrixnmedia.com"
        var login_type = login_type
        var name = "$userName"
        var phone = "$userPhone"
        var device_type = AppConstants.device_type
        var device_token = Prefs.with(baseContext.applicationContext).read(
            AppConstants.DEVICE_TOKEN,
            ""
        )
        val device_id = AndroidUtility.getAndroidDeviceId(this)
        val task = LanguageBody(
            email,
            login_type,
            name,
            phone,
            device_type,
            device_token,
            device_id,
            fb_id
        )

        Log.d(getString(R.string.app_name), "" + Gson().toJson(task))

        binding.pbloader.visibility = View.VISIBLE
        loginViewModel.fetchLoginResponse(task)
        loginViewModel.response.observe(this) { response ->
            Log.d(getString(R.string.app_name), Gson().toJson(response.data))
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let {
                        Log.d(getString(R.string.app_name), "" + response.data.token)
                        val gson = GsonBuilder().setPrettyPrinting().create()
                        val userdetails: String? = gson.toJson(response.data)
                        TalkiBookiApplication.applicationInstance.tinyDB.writeBoolean(
                            AppConstants.KEY_PREFS_USER_IS_LOGGED_IN,
                            true
                        )
                        Prefs.with(this).write(AppConstants.USER_DETAILS, userdetails!!)
                        Prefs.with(this)
                            .write(AppConstants.User_TOKEN, Bearer + response.data.token!!)


                    }
                    binding.pbloader.visibility = View.GONE
                }

                is NetworkResult.Error -> {
//                    Log.d(getString(R.string.app_name), "" + response.message!!)
                    if (response.message!!.contains("500")) {
                        Toast.makeText(
                            this,
                            "This email already exist from other platform",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(this, response.message!!, Toast.LENGTH_SHORT).show()
                    }

                    binding.pbloader.visibility = View.GONE
                }

                is NetworkResult.Loading -> {


                }
            }
        }
    }
}