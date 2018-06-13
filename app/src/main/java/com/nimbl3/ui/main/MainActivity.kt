package com.nimbl3

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import com.nimbl3.extension.setImageUrl
import com.nimbl3.data.service.ApiRepository
import com.nimbl3.data.service.response.ExampleResponse
import com.nimbl3.ui.base.BaseActivity
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject lateinit var appRepository: ApiRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.text)
        val imageView = findViewById<ImageView>(R.id.appCompatImageView)

        // Just for exampling the Retrofit implementation
        appRepository
            .getExampleData()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response: ExampleResponse ->
                var displayText = ""
                (0..2)
                    .map { response.data.children.get(it).data }
                    .forEach { displayText += "Author = ${it.author} \nTitle = ${it.title} \n\n" }

                textView.setText(displayText)
                imageView.setImageUrl("http://www.monkeyuser.com/assets/images/2018/80-the-struggle.png")
            }, { error: Throwable ->
                Toast.makeText(this, "Error: " + error.message, Toast.LENGTH_SHORT).show()
            })
    }
}