package dev.wxlf.github_repo_list

import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReposListAdapter(private val reposNames : List<String>, private val reposLangs : List<String>) :
    RecyclerView.Adapter<ReposListAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val repoName: TextView = itemView.findViewById(R.id.repoName)
        val repoLang: TextView = itemView.findViewById(R.id.repoLang)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.repoName.movementMethod = LinkMovementMethod.getInstance()
        holder.repoName.text = Html.fromHtml(reposNames[position])
        holder.repoLang.text = reposLangs[position]
    }

    override fun getItemCount(): Int {
        return reposNames.size
    }

}