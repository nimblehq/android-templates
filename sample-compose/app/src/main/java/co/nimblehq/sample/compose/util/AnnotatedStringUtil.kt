package co.nimblehq.sample.compose.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle

fun buildAnnotatedStringWithPart(
    fullText: String,
    clickablePart: String,
    clickableStyle: SpanStyle,
    onClick: () -> Unit,
): AnnotatedString {
    return buildAnnotatedString {
        val startIndex = fullText.indexOf(clickablePart)
        if (startIndex != -1) {
            append(fullText.take(startIndex))
            withLink(
                LinkAnnotation.Clickable(
                    tag = "CLICKABLE",
                    linkInteractionListener = { onClick() }
                )
            ) {
                withStyle(style = clickableStyle) {
                    append(clickablePart)
                }
            }
            append(fullText.substring(startIndex + clickablePart.length))
        } else {
            append(fullText)
        }
    }
}
