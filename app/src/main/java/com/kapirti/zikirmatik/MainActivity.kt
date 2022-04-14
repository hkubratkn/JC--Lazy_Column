package com.kapirti.zikirmatik

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Button
import androidx.compose.material.Text
import com.kapirti.zikirmatik.ui.theme.ZikirmatikTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZikirmatikTheme {
                Button(
                    onClick={
                        val intent = Intent()
                        intent.action=Intent.ACTION_SEND
                        intent.putExtra(Intent.EXTRA_TEXT,"lalalaa")
                        intent.type="text/plain"
                        startActivity(Intent.createChooser(intent, "gegegegeg"))
                    }
                ){
                    Text(text="share")
                }
            }
        }
    }
}