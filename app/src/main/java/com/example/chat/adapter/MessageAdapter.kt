package com.example.chat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.chat.R
import com.example.chat.model.Message
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context, val messageList: ArrayList<Message>) :
    RecyclerView.Adapter<ViewHolder>() {

    val ITEM_RECIEVED=1
    val ITEM_SENT=3

    override fun getItemViewType(position: Int): Int {
        val currentMessage=messageList[position]

        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderID)){
            return ITEM_SENT
        }else{
            return ITEM_RECIEVED
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        if(viewType==1){
            val view:View=
                LayoutInflater.from(parent.context).inflate(R.layout.receive,parent,false)
            return ReceivedViewHolder(view)
        }else{
            val view:View=
                LayoutInflater.from(parent.context).inflate(R.layout.sent,parent,false)
            return SentViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentMessage=messageList[position]
        if (holder.javaClass == SentViewHolder::class.java) {

            val viewHolder = holder as SentViewHolder
            holder.sentMessgae.text=currentMessage.message
        } else {
            val viewholder = holder as ReceivedViewHolder
            holder.recievedMessage.text=currentMessage.message
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sentMessgae=itemView.findViewById<TextView>(R.id.sentMessage)
    }

    class ReceivedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recievedMessage=itemView.findViewById<TextView>(R.id.recievedMessage)
    }

}