package com.devrachit.insta.util

import android.text.TextUtils
import android.util.Patterns
import java.util.regex.Matcher
import java.util.regex.Pattern

fun isValidEmail(target: CharSequence?): Boolean {
    return if (TextUtils.isEmpty(target)) {
        false
    } else {
        Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}

fun isValidPassword(password: String?): Boolean {
    val pattern: Pattern
    val matcher: Matcher
    val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*+=]).{8,}$"
    pattern = Pattern.compile(PASSWORD_PATTERN)
    matcher = pattern.matcher(password)
    return matcher.matches()
}

class ValidationUtils {
    var emailValid: Boolean = false
    var passwordValid: Boolean = false

    fun CharSequence?.isValidEmail(): Boolean {
        emailValid = if (TextUtils.isEmpty(this)) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(this).matches()
        }
        return emailValid
    }

    fun String?.isValidPassword(): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*+=]).{8,}$"
        pattern = Pattern.compile(PASSWORD_PATTERN)
        matcher = pattern.matcher(this)
        passwordValid = matcher.matches()
        return passwordValid
    }
}
