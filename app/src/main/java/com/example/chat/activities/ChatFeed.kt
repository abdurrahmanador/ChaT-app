package com.example.chat.activities

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.R
import com.example.chat.adapter.UserListAdapter
import com.example.chat.utils.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ChatFeed : AppCompatActivity() {
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userList: ArrayList<User>
    private lateinit var adapter: UserListAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_feed)

        userList = ArrayList()
        adapter = UserListAdapter(this, userList)

        userRecyclerView = findViewById(R.id.userRecyclerView) // Replace with your RecyclerView ID
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.adapter = adapter

        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        db.collection("users")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    // handle error
                    return@addSnapshotListener
                }
                if (value != null) {
                    userList.clear()
                    for (document in value) {
                        val currentUser = document.toObject(User::class.java)
                        userList.add(currentUser)
                    }

                    adapter.notifyDataSetChanged()
                }
            }
    }
}
