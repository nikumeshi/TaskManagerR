package com.example.nikumeshi_azddi9.taskmanagerr

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_add_task.*

class AddTaskActivity : AppCompatActivity() {

    private val buttonListener = View.OnClickListener{
        when(it){
            okButton -> {
                val priority = when(rg_priority.checkedRadioButtonId){
                    R.id.rb_Immediately -> "!!!"
                    R.id.rb_ASAP -> "!!"
                    R.id.rb_Soon -> "!"
                    else -> " "
                }
                val properties = TaskData(priority, taskTitle.text.toString(), taskDate.text.toString(), taskTime.text.toString(), taskDetail.text.toString()).toJson()
                setResult(Activity.RESULT_OK, Intent().putExtra(PROPERTIES,properties.toString()))
            }
        }
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        setResult(Activity.RESULT_CANCELED)

        cancelButton.setOnClickListener(buttonListener)
        okButton.setOnClickListener(buttonListener)
    }
}
