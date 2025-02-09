package com.example.readerapp.Screen.Login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class LoginScreenViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val firestore = FirebaseFirestore.getInstance()

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _userDocumentExists = MutableLiveData(false)
    val userDocumentExists: LiveData<Boolean> = _userDocumentExists

    fun checkUserDocumentExistence(userId: String) {
        firestore.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                _userDocumentExists.value = document.exists()
            }
            .addOnFailureListener {
                Log.d("FB", "Error checking user document existence: ${it.message}")
                _userDocumentExists.value = false
            }
    }

    fun signIn(
        email: String,
        password: String,
        home: () -> Unit
    ) = viewModelScope.launch {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        home()
                    } else {
                        Log.d("FB", "Login failed: ${task.exception?.message}")
                    }
                }
        } catch (ex: Exception) {
            Log.d("FB", "Login exception: ${ex.message}")
        }
    }

    fun register(
        email: String,
        password: String,
        home: () -> Unit
    ) {
        if (_loading.value == false) {
            _loading.value = true
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        home()
                    } else {
                        Log.d("FB", "Registration failed: ${task.exception?.message}")
                    }
                    _loading.value = false
                }
        }
    }

}
