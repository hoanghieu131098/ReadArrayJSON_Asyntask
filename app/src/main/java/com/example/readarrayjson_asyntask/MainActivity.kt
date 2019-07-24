package com.example.readarrayjson_asyntask

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    private var arr: ArrayList<String> = ArrayList()
    private var adapterkhoahoc: ArrayAdapter<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val url = "https://khoapham.vn/KhoaPhamTraining/json/tien/demo2.json"
        ReadJSON().execute(url)
        adapterkhoahoc = ArrayAdapter(this, android.R.layout.simple_list_item_1, arr)
        listview.adapter = adapterkhoahoc

    }

    inner class ReadJSON : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg p0: String?): String {
            var content: StringBuffer = StringBuffer()
            val url: URL = URL(p0[0])
            val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
            val inputStream: InputStreamReader = InputStreamReader(urlConnection.inputStream)
            val bufferedReader: BufferedReader = BufferedReader(inputStream)
            var line: String = ""
            try {
                do {
                    line = bufferedReader.readLine()
                    if (line != null) {
                        content.append(line)
                    }
                } while (line != null)

            } catch (e: Exception) {
                Log.d("AAA", e.toString())
            }
            return content.toString()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            val jsonObject: JSONObject = JSONObject(result)
            val jsonArray: JSONArray = jsonObject.getJSONArray("danhsach")
            for (i in 0..jsonArray.length() - 1) {
                var objecti: JSONObject = jsonArray.get(i) as JSONObject
                var name = objecti.getString("khoahoc")
                arr.add(name)
            }
            adapterkhoahoc?.notifyDataSetChanged()
        }

    }
}
