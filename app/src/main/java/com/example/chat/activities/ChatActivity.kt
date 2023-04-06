package com.example.chat.activities

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.R
import com.example.chat.adapter.MessageAdapter
import com.example.chat.model.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity:AppCompatActivity() {

    private lateinit var messageRecyclerView: RecyclerView
    private lateinit var messageBox:EditText
    private lateinit var sendBtn:ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var msgList:ArrayList<Message>
    private lateinit var mdbRef:DatabaseReference

    var recieverRoom:String?=null
    var senderRoom:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val name=intent.getStringExtra("name")
        val uid=intent.getStringExtra("uid")

        val senderuid=FirebaseAuth.getInstance().currentUser?.uid
        mdbRef=FirebaseDatabase.getInstance().getReference()
        senderRoom=uid?.plus(senderuid)
        recieverRoom=senderuid+uid

        supportActionBar?.title=name

        messageRecyclerView=findViewById(R.id.chatRecyclerView)
        messageBox=findViewById(R.id.messageBox)
        sendBtn=findViewById(R.id.sendBtn)
        msgList= ArrayList()
        messageAdapter= MessageAdapter(this,msgList) // Pass msgList to the adapter's constructor

        messageRecyclerView.layoutManager=LinearLayoutManager(this)
        messageRecyclerView.adapter=messageAdapter
        //logic for adding data to recyclerView
        mdbRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    msgList.clear()

                    for (postSnapshot in snapshot.children){
                        val message=postSnapshot.getValue(Message::class.java)
                        msgList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })

        //adding the message to database
        sendBtn.setOnClickListener {
            val message=messageBox.text.toString()
            val messageObject= senderuid?.let { it1 -> Message(message, it1) }

            mdbRef.child("chats").child(senderRoom!!)
                .child("messages")   .push()
                .setValue(messageObject)
                .addOnSuccessListener {
                    mdbRef.child("chats").child(recieverRoom!!)
                        .child("messages")   .push()
                        .setValue(messageObject)
                }
            messageBox.setText("")
        }

    }
}
