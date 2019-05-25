package com.example.nikumeshi_azddi9.taskmanagerr

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.task_list_row.view.*
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import org.w3c.dom.Text
import java.lang.reflect.Type
import java.text.FieldPosition

class TaskAdapter(context: Context,
                  private val taskArray: Array<String>,
                  private val onItemClicked: (String)->Unit): RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val type : Type = object : TypeToken<TaskData>() {}.type
        val json = Gson().fromJson<TaskData>(taskArray[position], type)

        holder.priority.text = json.priority
        holder.title.text = json.title
        holder.date.text = json.date
        holder.time.text = json.time

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewTipe: Int): TaskViewHolder {
        val view = inflater.inflate(R.layout.task_list_row, parent, false)
        val viewHolder = TaskViewHolder(view)

        view.setOnClickListener {
            val position = viewHolder.adapterPosition
            val task = taskArray[position]
            onItemClicked(task)
        }
        return viewHolder
    }

    override fun getItemCount(): Int = taskArray.size

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val priority: TextView = view.adap_priority
        val title: TextView = view.adap_title
        val date: TextView = view.adap_date
        val time: TextView = view.adap_time
    }
}