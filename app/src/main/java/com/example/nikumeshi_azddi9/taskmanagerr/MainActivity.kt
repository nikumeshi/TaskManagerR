package com.example.nikumeshi_azddi9.taskmanagerr

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

const val RC_ADD = 1
const val RC_MOD = 2
const val PROPERTIES = "properties"
const val DELETE_TASK = "delTask"
const val TASK_KEY = "key"
const val MOD_TASK = "modTask"
const val MOD_OR_DEL = "modOrDel"

class MainActivity : AppCompatActivity() {

    private val pref by lazy { getSharedPreferences("pref", Context.MODE_PRIVATE) }

    private val addButtonListener = View.OnClickListener {
        startActivityForResult(Intent(this, AddTaskActivity::class.java), RC_ADD)
    }

    private fun showTask(){
        val taskSet = pref.getStringSet(TASK_KEY, null)?: setOf()

        val adapter = TaskAdapter(this,taskSet.toTypedArray()){
            val intent = Intent(this, ModTaskActivity::class.java)
            intent.putExtra(MOD_TASK,it)
            startActivityForResult(intent, RC_MOD)
        }

        taskList.run{
            setHasFixedSize(true)
            setAdapter(adapter)
            layoutManager = LinearLayoutManager(this@MainActivity,RecyclerView.VERTICAL, false)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addButton.setOnClickListener(addButtonListener)
        showTask()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && data != null) {
            val taskSet = pref.getStringSet(TASK_KEY, mutableSetOf())?.toMutableSet() ?: mutableSetOf()

            val newTask = data.getStringExtra(PROPERTIES)
            val delTask = data.getStringExtra(DELETE_TASK)

            when(requestCode){
                RC_ADD -> {
                    taskSet.add(newTask)
                }
                RC_MOD -> {
                    when(data.getStringExtra(MOD_OR_DEL)){
                        "mod" -> taskSet.apply{
                            remove(delTask)
                            add(newTask)
                        }
                        "del" -> taskSet.remove(delTask)
                    }
                }
            }
            pref.edit().putStringSet(TASK_KEY,taskSet).apply()
        }
        showTask()
    }
}
