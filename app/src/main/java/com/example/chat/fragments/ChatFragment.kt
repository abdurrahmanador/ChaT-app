package com.example.chat.fragments
/*
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.R
import com.example.chat.adapter.UserListAdaptar
import com.example.chat.utils.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class ChatFragment : Fragment(R.layout.fragment_chat) {
    private lateinit var userRecyclerView:RecyclerView
    private lateinit var userList:ArrayList<User>
    private lateinit var adaptar: UserListAdaptar
    private lateinit var mAuth:FirebaseAuth
    private lateinit var mDbRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAuth=FirebaseAuth.getInstance()
        mDbRef=FirebaseDatabase.getInstance().getReference()
        userList= ArrayList()
        adaptar= UserListAdaptar(this@ChatFragment,userList)

        mDbRef.child("user").addValueEventListener(
            object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (postSnapshot in snapshot.children){
                        val currentUser=postSnapshot.getValue(User::class.java)
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            }
        )
    }
}
*/