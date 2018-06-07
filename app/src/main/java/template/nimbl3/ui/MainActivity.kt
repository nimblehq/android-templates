package template.nimbl3

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import dagger.android.AndroidInjection
import template.nimbl3.extension.setImageUrl
import template.nimbl3.services.ApiRepository
import template.nimbl3.services.response.ExampleResponse
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var appRepository: ApiRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this) // If we have baseActivity. we can bring it into the base class.
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.text)
        val imageView = findViewById<ImageView>(R.id.appCompatImageView)

        // Just for exampling the Retrofit implementation
        appRepository.getExampleData()
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