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

    // Function to sign in a student with validation for institute ID
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

    // Function to register a student with institute ID validation
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

    // Function to handle login with user type differentiation (student or institute)
    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        userType: String, // student or institute
        home: () -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // After login success, call the callback to navigate to home
                    home() // You can add specific logic for user type if needed
                } else {
                    Log.d("FB", "Login failed: ${task.exception?.message}")
                }
            }
    }

    // Validate the institute ID during registration
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

                        // Create the institute record
                        val institute = mapOf(
                            "instituteId" to instituteId,
                            "email" to email
                        )

                        // Store the institute record in Firestore
                        firestore.collection("institutes").document(instituteId).set(institute)
                            .addOnCompleteListener { firestoreTask ->
                                if (firestoreTask.isSuccessful) {
                                    Log.d("FB", "Institute registered successfully")
                                    createInstitute(displayName, instituteId)  // Create the institute in Firestore
                                    home()  // Navigate to home
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

    // Utility function to generate a unique Institute ID
    private fun generateInstituteId(): String {
        return "INST${System.currentTimeMillis()}"
    }

    // Validate that the student's institute ID matches during login
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

    // Create a student record in Firestore
    private fun createStudent(displayName: String?, instituteId: String) {
        val userId = auth.currentUser?.uid
        if (displayName != null && userId != null) {
            val student = MUser(
                userId = userId,
                displayName = displayName,
                avatarUrl = "",  //  set this later if the student has an avatar
                quote = "Learning every day",
                profession = "Student",
                id = instituteId // Associate the student with the institute ID
            ).toMap()

            firestore.collection("students").add(student)
                .addOnSuccessListener {
                    Log.d("FB", "Student created successfully")
                }
                .addOnFailureListener { exception ->
                    Log.d("FB", "Error creating student: ${exception.message}")
                }
        } else {
            Log.d("FB", "Display name or user ID is null")
        }
    }

    // Create an institute record in Firestore
    private fun createInstitute(displayName: String?, instituteId: String) {
        val institute = mapOf(
            "instituteId" to instituteId,
            "name" to displayName
        )
        firestore.collection("institutes").document(instituteId).set(institute)
            .addOnSuccessListener {
                Log.d("FB", "Institute created successfully")
            }
            .addOnFailureListener { exception ->
                Log.d("FB", "Error creating institute: ${exception.message}")
            }
    }
}
