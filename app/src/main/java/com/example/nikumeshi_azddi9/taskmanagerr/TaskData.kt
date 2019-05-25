package com.example.nikumeshi_azddi9.taskmanagerr

import com.google.gson.Gson
import org.json.JSONObject
import java.io.Serializable

data class TaskData(val priority: String, val title: String, val date: String, val time: String, val detail: String) :
    Serializable {
    fun toJson() = Gson().toJson(this)
}