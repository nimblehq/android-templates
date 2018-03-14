package template.nimbl3.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import template.nimbl3.TemplateApplication
import template.nimbl3.extension.setImageUrl
import template.nimbl3.rest.repository.ApiRepository
import template.nimbl3.rest.response.ExampleResponse
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    init {
        TemplateApplication.appComponent.inject(this)
    }

    @Inject lateinit var appRepository: ApiRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.text)
        val imageView = findViewById<ImageView>(R.id.appCompatImageView)

        // Just for exampling the Retrofit implementation
        appRepository.getExampleData()
            .subscribe({ response: ExampleResponse ->
                var displayText = ""
                for (i in 0..4) {
                    val resultData = response.data.children.get(i).data
                    displayText += "Author = ${resultData.author} \nTitle = ${resultData.title} \n\n"
                }

                textView.setText(displayText)
                imageView.setImageUrl("http://www.monkeyuser.com/assets/images/2018/80-the-struggle.png")
            }, { error: Throwable ->
                Toast.makeText(this, "Error: " + error.message, Toast.LENGTH_SHORT).show()
            })
    }
}
