package ie.wit.teamcomwatch.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import ie.wit.teamcomwatch.R
import ie.wit.teamcomwatch.main.MainApp

class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash)

        val animIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        Handler().postDelayed({
            val animOut = AnimationUtils.loadAnimation(this, R.anim.fade_out)
        }, 3000) // 3.. 4..

//        },100) // 3.. 4..

        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, MainApp::class.java))
            finish()
        }, 4500) // 4.5 ..
        //       },200) // 4.5 ..

    }
}