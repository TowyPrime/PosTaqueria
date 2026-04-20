package com.example.postacos.data

import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthRepository(private val userDao: UserDao) {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()




    fun signUp(
        name: String,
        email: String,
        password: String,
        onResult: (Boolean, String?) -> Unit
    ){
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){

                    val uid = auth.currentUser?.uid?: ""

                    val user = hashMapOf(
                        "uid" to uid,
                        "name" to name,
                        "role" to "user",
                        "email" to email
                    )
                    db.collection("users")
                        .document(uid)
                        .set(user)
                        .addOnSuccessListener {
                            onResult(true, null)
                        }
                        .addOnFailureListener {
                            onResult(false,it.message)
                        }
                }else{
                    onResult(false,task.exception?.message)
                }
            }

    }

    private fun fetcherUserAndCache(onResult: (Boolean, String?) -> Unit){
        val uid = auth.currentUser?.uid ?: return

        db.collection("users").document(uid).get()
            .addOnSuccessListener { doc ->

                val user = doc.toObject(UserEntity::class.java)

                if (user != null){
                    CoroutineScope(Dispatchers.IO).launch {
                        userDao.insertUser(user)
                    }
                    onResult(true, null)
                }else{
                    onResult(false, "Usuario no encontrado")
                }
            }
    }

    fun login(
        email: String,
        password: String,
        onResult: (Boolean, String?) -> Unit
    ){
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    onResult(true,null)
                }else{
                    onResult(false,task.exception?.message)
                }
            }
    }

    fun currentUser() = auth.currentUser

    fun logout(){
        auth.signOut()
    }




}