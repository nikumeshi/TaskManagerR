package com.example.nikumeshi_azddi9.taskmanagerr

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_mod_task.*
import java.lang.reflect.Type

class ModTaskActivity : AppCompatActivity() {

    private val clickListener = View.OnClickListener {
        setResult(Activity.RESULT_CANCELED)
        val priority = when(rg_priority.checkedRadioButtonId){
            R.id.rb_Immediately -> "!!!"
            R.id.rb_ASAP -> "!!"
            R.id.rb_Soon -> "!"
            else -> " "
        }
        val properties = TaskData(priority, taskTitle_mod.text.toString(), taskDate_mod.text.toString(), taskTime_mod.text.toString(), taskDetail_mod.text.toString())
        val result = Intent().putExtra(PROPERTIES,properties.toJson())
        result.putExtra(DELETE_TASK,intent.getStringExtra(MOD_TASK))
        when(it){
            modifyButton_mod -> {
                result.putExtra(MOD_OR_DEL,"mod")
                setResult(Activity.RESULT_OK, result)
            }
            deleteButton_mod -> {
                result.putExtra(MOD_OR_DEL,"del")
                setResult(Activity.RESULT_OK, result)
            }

        }
        finish()
    }

    private fun showTaskDetail(){
        val taskDetails = intent.getStringExtra(MOD_TASK)
        val type : Type = object : TypeToken<TaskData>() {}.type
        val json = Gson().fromJson<TaskData>(taskDetails,type)

        when(json.priority){
            "!!!" -> rb_Immediately.isChecked = true
            "!!" -> rb_ASAP.isChecked = true
            "!" -> rb_Soon.isChecked = true
            " " -> rb_AnyTime.isChecked = true
        }
        taskTitle_mod.setText(json.title)
        taskDate_mod.setText(json.date)
        taskTime_mod.setText(json.time)
        taskDetail_mod.setText(json.detail)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mod_task)

        cancelButton_mod.setOnClickListener(clickListener)
        modifyButton_mod.setOnClickListener(clickListener)
        deleteButton_mod.setOnClickListener(clickListener)
        showTaskDetail()
    }
}
