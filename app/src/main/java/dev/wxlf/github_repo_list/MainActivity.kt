package dev.wxlf.github_repo_list

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val namesList = mutableListOf<String>()
        val langsList = mutableListOf<String>()
        val adapter = ReposListAdapter(namesList, langsList)
        val recyclerView: RecyclerView = findViewById(R.id.reposList)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val usrName: EditText = findViewById(R.id.username)
        val searchBtn: Button = findViewById(R.id.searchBtn)

        searchBtn.setOnClickListener {
            val thread = Thread {
                try {
                    val url = URL("https://api.github.com/users/${usrName.text}/repos")
                    var a = ""
                    with(url.openConnection() as HttpURLConnection) {
                        requestMethod = "GET"
                        inputStream.bufferedReader().use {
                            it.lines().forEach { line ->
                                a += line + "\n"
                            }
                        }
                    }
                    val jsonAr = JSONArray(a)
                    namesList.clear()
                    langsList.clear()
                    for (i in 0 until jsonAr.length()) {
                        namesList.add("<a href=\"${jsonAr.getJSONObject(i).getString("html_url")}\">${jsonAr.getJSONObject(i).getString("full_name")}</a>")
                        langsList.add(jsonAr.getJSONObject(i).getString("language"))
                    }

                    runOnUiThread {
                        adapter.notifyDataSetChanged()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            thread.start()

        }
    }
}