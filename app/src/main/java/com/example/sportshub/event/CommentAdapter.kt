package com.example.sportshub.event

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sportshub.R
import com.example.sportshub.event.model.CommentModel

class CommentAdapter: RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {
    var commentList: MutableList<CommentModel> = mutableListOf()

    class CommentViewHolder(view: View):RecyclerView.ViewHolder(view) {
        private val owner: TextView = view.findViewById(R.id.comment_owner)
        private val body: TextView = view.findViewById(R.id.comment_body)
        fun bind(commentModel: CommentModel){
            owner.text = "${commentModel.owner}:"
            body.text = commentModel.body
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.comment_item, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(commentList[position])
    }

    override fun getItemCount(): Int {
        return commentList.size
    }
}