package com.easylife.easylifes.utils

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.easylife.easylifes.R
import org.aviran.cookiebar2.CookieBar
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.SimpleDateFormat
import java.util.*


class Utilities(context: Context) {

    private val context: Context? = null
    var dialog: ProgressDialog? = null


    fun showProgressDialog(ctx: Context?, msg: String?) {
        dialog = ProgressDialog(ctx)
        dialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        dialog!!.setMessage(msg)
        dialog!!.setIndeterminate(true)
        dialog!!.setCanceledOnTouchOutside(false)
        dialog!!.show()
    }

    fun hideProgressDialog() {
        if (dialog != null && dialog!!.isShowing()) {
            dialog!!.cancel()
            dialog = null
        }
    }

    fun saveInt(context: Context, key: String?, value: Int) {

        val sharedPref = context.getSharedPreferences("fedsenseappshared", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putInt(key, value)
        editor.apply()
    }


    fun getInt(context: Context, key: String?): Int {
        val sharedPref = context.getSharedPreferences("fedsenseappshared", Context.MODE_PRIVATE)
        return sharedPref.getInt(key, 0)
    }


    fun saveString(
        context: Context, key: String, value: String
    ) {
        val sharedPref = context.getSharedPreferences("fedsenseappshared", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(context: Context, key: String): String {
        val sharedPref = context.getSharedPreferences("fedsenseappshared", Context.MODE_PRIVATE)
        return sharedPref.getString(key, "").toString()
    }


    fun clearSharedPref(context: Context) {
        val sharedPref =
            context.getSharedPreferences("fedsenseappshared", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.clear()
        editor.apply()
    }

    fun printHashKey(Context: Context) {
        try {
            val info = Context.packageManager.getPackageInfo(
                Context.packageName,
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey: String = String(Base64.encode(md.digest(), 0))
                Log.i(TAG, "printHashKey() Hash Key: $hashKey")
            }
        } catch (e: NoSuchAlgorithmException) {
            Log.e(TAG, "printHashKey()", e)
        } catch (e: Exception) {
            Log.e(TAG, "printHashKey()", e)
        }
    }


    fun saveStrings(
        context: Context, key: String, value: String
    ) {
        val sharedPref = context.getSharedPreferences("loginshared", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getStrings(context: Context, key: String): String {
        val sharedPref = context.getSharedPreferences("loginshared", Context.MODE_PRIVATE)
        return sharedPref.getString(key, "").toString()
    }


    fun saveBoolean(
        context: Context, key: String, value: Boolean
    ) {
        val sharedPref = context.getSharedPreferences("fedsenseappshared", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolean(context: Context, key: String): String {
        val sharedPref = context.getSharedPreferences("fedsenseappshared", Context.MODE_PRIVATE)
        return sharedPref.getBoolean(key,false).toString()
    }





    fun saveStringInterest(
        context: Context, key: String, value: String
    ) {
        val sharedPref = context.getSharedPreferences("interest", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getStringInterest(context: Context, key: String): String {
        val sharedPref = context.getSharedPreferences("interest", Context.MODE_PRIVATE)
        return sharedPref.getString(key, "").toString()
    }

    fun isConnectingToInternet(context: Context): Boolean {
        val connectivity = context.getSystemService(
            AppCompatActivity.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (connectivity != null) {
            val info = connectivity.allNetworkInfo
            if (info != null) for (i in info.indices) if (info[i].state == NetworkInfo.State.CONNECTED) {
                return true
            }
        }
        return false
    }
    fun setWhiteBars(activity: Activity) {
        val window = activity.window
        val view = window.decorView
        view.systemUiVisibility = view.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        view.systemUiVisibility =
            view.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        window.navigationBarColor = activity.resources.getColor(android.R.color.white)
        window.statusBarColor = activity.resources.getColor(android.R.color.white)
    }
    fun setGreenBar(activity: Activity) {
        val window = activity.window
        val view = window.decorView
        view.systemUiVisibility = view.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        view.systemUiVisibility =
            view.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        window.navigationBarColor = activity.resources.getColor(R.color.statusbarcolor)
        window.statusBarColor = activity.resources.getColor(R.color.statusbarcolor)
    }
    fun setLightGreenBar(activity: Activity) {
        val window = activity.window
        val view = window.decorView
        view.systemUiVisibility = view.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        view.systemUiVisibility =
            view.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        window.navigationBarColor = activity.resources.getColor(R.color.statusbarlogoutcolor)
        window.statusBarColor = activity.resources.getColor(R.color.statusbarlogoutcolor)
    }
    fun setGrayBar(activity: Activity) {
        val window = activity.window
        val view = window.decorView
        view.systemUiVisibility = view.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        view.systemUiVisibility =
            view.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        window.navigationBarColor = activity.resources.getColor(R.color.porcelain)
        window.statusBarColor = activity.resources.getColor(R.color.porcelain)
    }
    fun openDialog(manager: FragmentManager, fragment: DialogFragment) {
        val ft = manager.beginTransaction()
        val prev = manager.findFragmentByTag("dialog")
        if (prev != null) {
            ft.remove(prev)
        }
        ft.addToBackStack(null)
        fragment.show(ft, "dialog")
    }

    fun Context.toast(message : String)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }




    fun timestamp()
    {
        val timestampString = "model.product_upload_time".toLong()
        val value = SimpleDateFormat("dd/MM/yyyy").format(Date(timestampString * 1000))
    }


    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager: InputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
    }

    fun showSuccessToast(context: Activity?, msg: String?) {
        CookieBar.build(context).setCustomView(R.layout.custom_banner)
            .setCustomViewInitializer { view: View ->
                val cookie = view.findViewById<LinearLayout>(R.id.ll_cookie)
                val ivIcon =
                    view.findViewById<ImageView>(R.id.iv_icon)
                val tvTitle = view.findViewById<TextView>(R.id.tv_title)
                val tvMessage = view.findViewById<TextView>(R.id.tv_message)
                tvTitle.text = "Success!"
                tvMessage.text = msg
                ivIcon.setImageResource(R.drawable.ic_baseline_sentiment_satisfied)
                cookie.setBackgroundResource(R.drawable.banner_primary_color_corner10dp)
            }
            .setCookiePosition(CookieBar.TOP)
            .setDuration(3000)
            .setEnableAutoDismiss(true) // Cookie will stay on display until manually dismissed
            .setSwipeToDismiss(true) // Deny dismiss by swiping off the view
            .show()
    }

    fun showFailureToast(context: Activity?, msg: String?) {
        CookieBar.build(context).setCustomView(R.layout.custom_banner)
            .setCustomViewInitializer { view: View ->
                val cookie = view.findViewById<LinearLayout>(R.id.ll_cookie)
                val ivIcon =
                    view.findViewById<ImageView>(R.id.iv_icon)
                val tvTitle = view.findViewById<TextView>(R.id.tv_title)
                val tvMessage = view.findViewById<TextView>(R.id.tv_message)
                tvTitle.text = "Failed!"
                tvMessage.text = msg
                ivIcon.setImageResource(R.drawable.ic_baseline_warning_24)
                cookie.setBackgroundResource(R.drawable.banner_primary_color_corner10dp)
            }
            .setCookiePosition(CookieBar.TOP)
            .setDuration(3000)
            .setEnableAutoDismiss(true) // Cookie will stay on display until manually dismissed
            .setSwipeToDismiss(true) // Deny dismiss by swiping off the view
            .show()
    }
    fun showSuccessToast(context: Activity?, title: String?, msg: String?) {
        CookieBar.build(context).setCustomView(R.layout.custom_banner)
            .setCustomViewInitializer { view: View ->
                val cookie = view.findViewById<LinearLayout>(R.id.ll_cookie)
                val ivIcon =
                    view.findViewById<ImageView>(R.id.iv_icon)
                val tvTitle = view.findViewById<TextView>(R.id.tv_title)
                val tvMessage = view.findViewById<TextView>(R.id.tv_message)
                tvTitle.text = title
                tvMessage.text = msg
                ivIcon.setImageResource(R.drawable.ic_baseline_sentiment_satisfied)
                cookie.setBackgroundResource(R.drawable.banner_primary_color_corner10dp)
            }
            .setCookiePosition(CookieBar.TOP)
            .setDuration(3000)
            .setEnableAutoDismiss(true) // Cookie will stay on display until manually dismissed
            .setSwipeToDismiss(true) // Deny dismiss by swiping off the view
            .show()
    }

    fun showFailureToast(context: Activity?, title: String?, msg: String?) {
        CookieBar.build(context).setCustomView(R.layout.custom_banner)
            .setCustomViewInitializer { view: View ->
                val cookie = view.findViewById<LinearLayout>(R.id.ll_cookie)
                val ivIcon =
                    view.findViewById<ImageView>(R.id.iv_icon)
                val tvTitle = view.findViewById<TextView>(R.id.tv_title)
                val tvMessage = view.findViewById<TextView>(R.id.tv_message)
                tvTitle.text = title
                tvMessage.text = msg
                ivIcon.setImageResource(R.drawable.ic_warning_sign)
                cookie.setBackgroundResource(R.drawable.banner_primary_color_corner10dp)
            }
            .setCookiePosition(CookieBar.TOP)
            .setDuration(3000)
            .setEnableAutoDismiss(true) // Cookie will stay on display until manually dismissed
            .setSwipeToDismiss(true) // Deny dismiss by swiping off the view
            .show()
    }
    fun showNoInternetToast(context: Activity?) {
        CookieBar.build(context)
            .setTitle("Can not connect to Internet!")
            .setMessage("Make sure you have internet connection and Try again!")
            .setIcon(R.drawable.ic_baseline_wifi_off_24)
            .setCookiePosition(CookieBar.TOP)
            .setDuration(3000)
            .setBackgroundColor(R.color.green_outline)
            .show()
    }
    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target!!).matches()
    }

}