package com.mubarakansari.contacts_content_provider_kotlin_android


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_biometric.*
import java.util.concurrent.Executor

class BiometricActivity : AppCompatActivity() {
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: PromptInfo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_biometric)

        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(
            this@BiometricActivity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(
                        this@BiometricActivity,
                        "Authentication Successfully",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    startActivity(Intent(this@BiometricActivity, MainActivity::class.java))
                }

                @SuppressLint("SetTextI18n")
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    tv_bio_finger.text = "Authentication $errString"
                }

                @SuppressLint("SetTextI18n")
                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    tv_bio_finger.text = "Authentication Failed"
                }

            })

        //setDialog

        promptInfo = PromptInfo.Builder()
            .setTitle("Unlock Demo App")
            .setSubtitle("Confirm your screen finger password!")
            .setNegativeButtonText("Cancel")
            .build()

        biometricPrompt.authenticate(promptInfo)

        btn_bio_finger.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }

    }
}