package com.example.enggo.ui.lesson

import android.util.Log
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp

// Regex containing the syntax tokens
//val symbolPattern by lazy {
//    Regex("""(https?://[^\s\t\n]+)|(`[^`]+`)|(@\w+)|(\*[\w]+\*)|(_[\w]+_)|(~[\w]+~)""")
//}

val symbolPattern by lazy {
    Regex("""(https?://[^\s\t\n]+)|(`[^`]+`)|(@[^@]+@)|(\*[^*]+\*)|(_[^_]+_)|(~[^~]+~)|(![^!]+!)""")
}



// Accepted annotations for the ClickableTextWrapper
enum class SymbolAnnotationType {
    PERSON, LINK
}
typealias StringAnnotation = AnnotatedString.Range<String>
// Pair returning styled content and annotation for ClickableText when matching syntax token
typealias SymbolAnnotation = Pair<AnnotatedString, StringAnnotation?>

/**
 * Format a message following Markdown-lite syntax
 * | @title@ -> bold + italic
 * | http(s)://... -> clickable link, opening it into the browser
 * | *bold* -> bold
 * | _italic_ -> italic
 * | ~strikethrough~ -> strikethrough
 * | `MyClass.myMethod` -> inline code styling
 *
 * @param text contains message to be parsed
 * @return AnnotatedString with annotations used inside the ClickableText wrapper
 */
@Composable
fun contentFormatter(
    text: String,
): AnnotatedString {
    val tokens = symbolPattern.findAll(text)

    return buildAnnotatedString {

        var cursorPosition = 0


        for (token in tokens) {
            append(text.slice(cursorPosition until token.range.first))

            val (annotatedString, stringAnnotation) = getSymbolAnnotation(
                matchResult = token,
                colorScheme = MaterialTheme.colorScheme
            )
            append(annotatedString)

            if (stringAnnotation != null) {
                val (item, start, end, tag) = stringAnnotation
                addStringAnnotation(tag = tag, start = start, end = end, annotation = item)
            }

            cursorPosition = token.range.last + 1
        }

        if (!tokens.none()) {
            append(text.slice(cursorPosition..text.lastIndex))
        } else {
            append(text)
        }
    }
}

/**
 * Map regex matches found in a message with supported syntax symbols
 *
 * @param matchResult is a regex result matching our syntax symbols
 * @return pair of AnnotatedString with annotation (optional) used inside the ClickableText wrapper
 */
private fun getSymbolAnnotation(
    matchResult: MatchResult,
    colorScheme: ColorScheme,
): SymbolAnnotation {
    return when (matchResult.value.first()) {
        '@' -> SymbolAnnotation(
            AnnotatedString(
                text = matchResult.value.trim('@'),
                spanStyle = SpanStyle(
                    fontSize = 25.sp, // TODO
                    fontStyle = FontStyle.Italic
                )
            ),
            null
        )
        '*' -> SymbolAnnotation(
            AnnotatedString(
                text = matchResult.value.trim('*'),
                spanStyle = SpanStyle(fontWeight = FontWeight.Bold)
            ),
            null
        )
        '_' -> SymbolAnnotation(
            AnnotatedString(
                text = matchResult.value.trim('_'),
                spanStyle = SpanStyle(fontStyle = FontStyle.Italic)
            ),
            null
        )
        '~' -> SymbolAnnotation(
            AnnotatedString(
                text = matchResult.value.trim('~'),
                spanStyle = SpanStyle(textDecoration = TextDecoration.LineThrough)
            ),
            null
        )
        '`' -> SymbolAnnotation(
            AnnotatedString(
                text = matchResult.value.trim('`'),
                spanStyle = SpanStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 12.sp,
                    baselineShift = BaselineShift(0.2f)
                )
            ),
            null
        )
        '!' -> SymbolAnnotation(
            AnnotatedString(
                text = matchResult.value.trim('!'),
                spanStyle = SpanStyle(fontSize = 30.sp)
            ),
            null
        )
        'h' -> SymbolAnnotation(
            AnnotatedString(
                text = matchResult.value,

            ),
            StringAnnotation(
                item = matchResult.value,
                start = matchResult.range.first,
                end = matchResult.range.last,
                tag = SymbolAnnotationType.LINK.name
            )
        )
        else -> SymbolAnnotation(AnnotatedString(matchResult.value), null)
    }
}
