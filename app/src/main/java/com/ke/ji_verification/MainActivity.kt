package com.ke.ji_verification

import android.content.ClipData
import android.content.ClipboardManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.ke.jverification.JiVerificationService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init.setOnClickListener {
            JiVerificationService.init(applicationContext)
                .subscribe {
                    log(it.toString())
                }
        }

        checkVerifyEnable.setOnClickListener {
            val result = JiVerificationService.checkVerifyEnable(applicationContext)

            Toast.makeText(applicationContext, "是否可以认证 $result", Toast.LENGTH_LONG).show()
        }

        getToken.setOnClickListener {
            JiVerificationService.getToken(applicationContext)
                .subscribe {
                    log(it.toString())

                }
        }

        preLogin.setOnClickListener {
            JiVerificationService.preLogin(applicationContext)
                .subscribe {
                    log(it.toString())
                }
        }

        loginAuth.setOnClickListener {
            JiVerificationService.loginAuth(applicationContext)
                .subscribe {
                    log(it.toString())

                    val clipData = ClipData.newPlainText("text", it.content)
                    val clipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                    clipboardManager.setPrimaryClip(clipData)
                    Toast.makeText(applicationContext, "成功复制token到剪贴板", Toast.LENGTH_LONG).show()

                }
        }

        privacy.setOnClickListener {
            JiVerificationService.setCustomUI("用户协议", "https://baidu.com", "logo")
        }

    }


    private fun log(string: String) {
        Log.d("TAG", string)
    }
}