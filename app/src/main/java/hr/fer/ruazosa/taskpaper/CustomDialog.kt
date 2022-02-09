package hr.fer.ruazosa.taskpaper

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.Window
import android.widget.ImageButton
import kotlinx.android.synthetic.main.dialog_layout.*

class CustomDialog(activity: Activity): Dialog(activity){
    lateinit var submitButton: ImageButton
    lateinit var cancelButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_layout)
        submitButton=dialogConfirmButton
        cancelButton=dialogCancelButton
    }
}