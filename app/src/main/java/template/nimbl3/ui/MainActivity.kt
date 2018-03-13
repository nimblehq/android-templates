package template.nimbl3.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import template.nimbl3.extension.setImageUrl

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<ImageView>(R.id.appCompatImageView)
            .setImageUrl("http://www.monkeyuser.com/assets/images/2018/80-the-struggle.png")
    }
}
