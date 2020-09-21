package com.sample.twitterapiapp.ui.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.agrawalsuneet.loaderspack.loaders.CurvesLoader
import com.sample.newsapp.R

open class BaseActivity : AppCompatActivity() {
    private val TAG = BaseActivity::class.java.simpleName

    private var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // we can initialize something here, that needs to be initialized for all activities
    }


    /**
     *  Shows Loading dialog
     *  @param cancellable
     */
    fun showLoadingDialog(cancellable: Boolean) {

        val curvesLoader = CurvesLoader(
            this,
            4,
            100,
            10,
            10,
            160.0f,
            ContextCompat.getColor(this, R.color.loader_color)
        )
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

    fun hideLoadingDialog() {
        if (dialog != null) {
            dialog?.dismiss()
        }
    }
}