package com.sample.twitterapiapp.ui.base

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Color.blue
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.agrawalsuneet.loaderspack.loaders.CurvesLoader
import com.agrawalsuneet.loaderspack.loaders.RingAndCircleLoader
import com.agrawalsuneet.loaderspack.loaders.RotatingCircularSticksLoader
import com.sample.newsapp.R
import kotlinx.android.synthetic.main.custom_dialog.*

open class BaseActivity: AppCompatActivity() {
    private val TAG = BaseActivity::class.java.simpleName

    var alertDialog: AlertDialog? = null
    private var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // we can initialize something here, that needs to be initialized for all activities
    }

    /**
     * shows alert dialog
     * @param title: The title of alert dialog
     * @param message: The message of alert dialog
     * @param buttonText: The text for positive Button button
     * @param positiveListener: The listener for positive button
     */
    fun showAlertDialog(title: String, message: String,buttonText: String, positiveListener: DialogInterface.OnClickListener){
        if (alertDialog!=null && alertDialog?.isShowing == true){
            alertDialog?.dismiss()
        }
        alertDialog = AlertDialog.Builder(this).create()
        alertDialog?.setTitle(title)
        alertDialog?.setMessage(message)
        alertDialog?.setButton(AlertDialog.BUTTON_POSITIVE,buttonText, positiveListener)
        alertDialog?.setCancelable(false)
        alertDialog?.show()
    }

    /**
     *  Shows Loading dialog
     *  @param cancellable
     */
    fun showLoadingDialog( cancellable: Boolean) {

        val curvesLoader = CurvesLoader(
            this,
            4,
            100,
            10,
            10,
            160.0f,
            ContextCompat.getColor(this, R.color.loader_color))
            .apply {
                animDuration = 1000
                interpolator = LinearInterpolator()
            }
        dialog = Dialog(this)

        dialog?.setContentView(R.layout.custom_dialog)
        val frame = dialog?.findViewById<FrameLayout>(R.id.frameLoader)
        frame?.addView(curvesLoader)
        dialog?.setCancelable(cancellable)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.show()
    }

    fun hideLoadingDialog(){
        if (dialog!=null ){
            dialog?.dismiss()
        }
    }
}