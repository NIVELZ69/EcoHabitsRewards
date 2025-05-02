package com.actividad3.ecohabitsrewards.view.activities.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.actividad3.ecohabitsrewards.databinding.ActivityRegisterBinding
import com.actividad3.ecohabitsrewards.view.activities.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnRegister.setOnClickListener {
            val email = binding.etEmailRegister.text.toString().trim()
            val password = binding.etPasswordRegister.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()

            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    Toast.makeText(this, "Cuenta creada con éxito", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                    val user = auth.currentUser
                    user?.let {
                        val userData = hashMapOf(
                            "email" to it.email,
                            "displayName" to it.email?.substringBefore("@"),
                            "points" to 0L,
                            "createdAt" to com.google.firebase.Timestamp.now()
                        )

                        FirebaseFirestore.getInstance().collection("users")
                            .document(it.uid)
                            .set(userData)
                            .addOnSuccessListener {
                                // Usuario creado correctamente
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "Error al crear perfil: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    }

                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_LONG).show()
                }
        }

        binding.tvGoToLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
