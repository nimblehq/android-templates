package co.nimblehq.sample.compose.util

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import co.nimblehq.sample.compose.ui.theme.ComposeTheme

fun buildAnnotatedStringWithPart(
    text: String,
    annotatedStringPart: AnnotatedStringPart,
) = buildAnnotatedStringWithParts(
    text = text,
    annotatedStringParts = listOf(annotatedStringPart),
)

fun buildAnnotatedStringWithParts(
    text: String,
    annotatedStringParts: List<AnnotatedStringPart>,
) = buildAnnotatedString {
    append(text)

    annotatedStringParts.forEach { annotationPart ->
        val startIndex = text.indexOf(annotationPart.text)
        if (startIndex == -1) return@forEach

        val endIndex = startIndex + annotationPart.text.length
        annotationPart.spanStyle?.let {
            addStyle(
                style = annotationPart.spanStyle,
                start = startIndex,
                end = endIndex
            )
        }
        annotationPart.onClick?.let {
            addLink(
                clickable = LinkAnnotation.Clickable(
                    tag = annotationPart.text,
                    linkInteractionListener = {
                        annotationPart.onClick()
                    }
                ),
                start = startIndex,
                end = endIndex
            )
        }
    }
}

data class AnnotatedStringPart(
    val text: String,
    val spanStyle: SpanStyle? = null,
    val onClick: (() -> Unit)? = null,
)

@Preview(showBackground = true)
@Composable
private fun AnnotatedTextPreview() {
    ComposeTheme {
        Text(
            text = buildAnnotatedStringWithPart(
                text = "This is a sample annotated text.",
                annotatedStringPart = AnnotatedStringPart(
                    text = "annotated text",
                    spanStyle = SpanStyle(
                        color = Color.Blue,
                    ),
                ),
            ),
        )
    }
}
