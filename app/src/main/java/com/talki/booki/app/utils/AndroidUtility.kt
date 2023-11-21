package com.talki.booki.app.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.talki.booki.app.R
import org.jsoup.Jsoup
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


class AndroidUtility {
    companion object {

        fun dpToPx(context: Context, dp: Int): Int {
            return (dp * (context.resources.displayMetrics.densityDpi / 160))
        }

        fun pxToDp(context: Context, px: Int): Int {
            return (px / context.resources.displayMetrics.density).toInt()
        }

        fun showSnackBar(context: Context, message: String?) {
            val toast = Toast(context)
            val layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = layoutInflater.inflate(R.layout.snack_bar_item, null)
            val textView = view.findViewById<TextView>(R.id.tvSnackBar)
            textView.text = message
            toast.view = view
            toast.setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, 0)
            toast.duration = Toast.LENGTH_LONG
            toast.show()
        }

        fun isEmailValid(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }


        fun isValidPasswordFormat(password: String): Boolean {
            val passwordREGEX = Pattern.compile(
                "^" +
                        "(?=.*[0-9])" +         //at least 1 digit
                        "(?=.*[a-z])" +         //at least 1 lower case letter
                        "(?=.*[A-Z])" +         //at least 1 upper case letter
                        "(?=.*[a-zA-Z])" +      //any letter
                        "(?=.*[@#$%^&+=])" +    //at least 1 special character
                        "(?=\\S+$)" +           //no white spaces
                        ".{6,}" +               //at least 6 characters
                        "$"
            );
            return passwordREGEX.matcher(password).matches()
        }

        fun isNetworkAvailable(context: Context): Boolean {
            val connectivity =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivity != null) {
                val info = connectivity.allNetworkInfo
                if (info != null) {
                    for (i in info!!.indices) {
                        Log.w("INTERNET:", i.toString())
                        if (info!![i].state === NetworkInfo.State.CONNECTED) {
                            Log.w("INTERNET:", "connected!")
                            return true
                        }
                    }
                }
            }
            return false
        }

        fun optionalBlankText(input: String?): String {
            return if (input == null || input.trim { it <= ' ' } == "" || input.isEmpty() || input.trim { it <= ' ' } == "null") {
                ""
            } else {
                input
            }
        }

        fun printModelDataWithGSON(tag: String = "ModelDataTag", modelData: Any?) {
            Log.e(tag, Gson().toJson(modelData))
        }

        fun getAndroidDeviceId(context: Context): String {
            return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        }

        fun convertDateFormat(deliveryDate: String):String{
            var date:String ?= null
            try {
                val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
                val dateObj = sdf.parse(deliveryDate)
                date = SimpleDateFormat("dd MMM, yyyy", Locale.US).format(dateObj)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
//            Log.d("TalkiBooki", "**********position**" + date)

            return date!!
        }

        fun formatDateFromString(
            inputFormat: String,
            outputFormat: String,
            inputDate: String
        ): String {
            if (inputDate == "")
                return ""
            var parsed: Date? = null
            var outputDate = ""
            val df_input = SimpleDateFormat(inputFormat)
            val df_output = SimpleDateFormat(outputFormat)

            try {
                parsed = df_input.parse(inputDate)
                outputDate = df_output.format(parsed)

            } catch (e: ParseException) {
                Log.d("CALENDER", "ParseException - dateFormat")
            }
            return outputDate
        }
    }

    fun removeHtmlTag(htmlText:String?):String{
        return Jsoup.parse(htmlText).text()
    }
}