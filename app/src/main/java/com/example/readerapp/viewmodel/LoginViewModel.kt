package com.example.readerapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readerapp.model.MUser
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

    fun signInStudent(
        email: String,
        password: String,
        instituteId: String,
        home: () -> Unit
    ) = viewModelScope.launch {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        validateStudentInstitute(instituteId, home)
                    } else {
                        Log.d("FB", "Student login failed: ${task.exception?.message}")
                    }
                }
        } catch (ex: Exception) {
            Log.d("FB", "Student login exception: ${ex.message}")
        }
    }

    fun registerStudent(
        email: String,
        password: String,
        instituteId: String,
        home: () -> Unit
    ) {
        if (_loading.value == false) {
            _loading.value = true
            validateInstituteId(instituteId) { isValid ->
                if (isValid) {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val displayName = task.result?.user?.email?.split('@')?.get(0)
                                createStudent(displayName, instituteId)
                                home()
                            } else {
                                Log.d("FB", "Student registration failed: ${task.exception?.message}")
                            }
                            _loading.value = false
                        }
                } else {
                    Log.d("FB", "Invalid Institute ID provided.")
                    _loading.value = false
                }
            }
        }
    }

    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        userType: String,
        home: () -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        checkUserDocumentExistence(userId)
                    }
                    home()
                } else {
                    Log.d("FB", "Login failed: ${task.exception?.message}")
                }
            }
    }

    private fun validateInstituteId(instituteId: String, callback: (Boolean) -> Unit) {
        firestore.collection("institutes").document(instituteId).get()
            .addOnSuccessListener { document ->
                callback(document.exists())
            }
            .addOnFailureListener {
                Log.d("FB", "Error validating Institute ID: ${it.message}")
                callback(false)
            }
    }

    fun registerInstitute(
        email: String,
        password: String,
        home: () -> Unit
    ) {
        if (_loading.value == false) {
            _loading.value = true
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { authTask ->
                    if (authTask.isSuccessful) {
                        val displayName = authTask.result?.user?.email?.split('@')?.get(0)
                        val instituteId = generateInstituteId()

                        val institute = mapOf(
                            "instituteId" to instituteId,
                            "email" to email
                        )

                        firestore.collection("institutes").document(instituteId).set(institute)
                            .addOnCompleteListener { firestoreTask ->
                                if (firestoreTask.isSuccessful) {
                                    Log.d("FB", "Institute registered successfully")
                                    createInstitute(displayName, instituteId)
                                    home()
                                } else {
                                    Log.d("FB", "Firestore registration failed: ${firestoreTask.exception?.message}")
                                }
                                _loading.value = false
                            }
                            .addOnFailureListener { exception ->
                                Log.d("FB", "Firestore error: ${exception.message}")
                                _loading.value = false
                            }
                    } else {
                        Log.d("FB", "Firebase Auth registration failed: ${authTask.exception?.message}")
                        _loading.value = false
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("FB", "Firebase Auth error: ${exception.message}")
                    _loading.value = false
                }
        }
    }

    private fun generateInstituteId(): String {
        return "INST${System.currentTimeMillis()}"
    }

    private fun validateStudentInstitute(instituteId: String, home: () -> Unit) {
        val userId = auth.currentUser?.uid
        firestore.collection("users")
            .whereEqualTo("userId", userId)
            .whereEqualTo("instituteId", instituteId)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.size() > 0) {
                    home()
                } else {
                    Log.d("FB", "Invalid Institute ID for student.")
                }
            }
            .addOnFailureListener {
                Log.d("FB", "Error validating student: ${it.message}")
            }
    }

    private fun createStudent(displayName: String?, instituteId: String) {
        val userId = auth.currentUser?.uid
        if (displayName != null && userId != null) {
            val student = MUser(
                userId = userId,
                displayName = displayName,
                avatarUrl = "",
                quote = "Learning every day",
                profession = "Student",
                id = instituteId
            ).toMap().toMutableMap()

            student["role"] = "student"

            firestore.collection("users").document(userId).set(student)
                .addOnSuccessListener {
                    Log.d("FB", "Student created successfully with role")
                    // Confirm document existence
                    firestore.collection("users").document(userId).get().addOnSuccessListener { doc ->
                        if (doc.exists()) {
                            Log.d("FB", "Document data: ${doc.data}")
                        } else {
                            Log.d("FB", "Document does not exist after creation")
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("FB", "Error creating student: ${exception.message}")
                }
        } else {
            Log.d("FB", "Display name or user ID is null")
        }
    }


    private fun createInstitute(displayName: String?, instituteId: String) {
        val userId = auth.currentUser?.uid
        if (displayName != null && userId != null) {
            val institute = mapOf(
                "userId" to userId,
                "instituteId" to instituteId,
                "name" to displayName,
                "role" to "institute"
            )

            firestore.collection("users").document(userId).set(institute)
                .addOnSuccessListener {
                    Log.d("FB", "Institute created successfully with role")
                }
                .addOnFailureListener { exception ->
                    Log.d("FB", "Error creating institute: ${exception.message}")
                }
        } else {
            Log.d("FB", "Display name or user ID is null")
        }
    }
}
