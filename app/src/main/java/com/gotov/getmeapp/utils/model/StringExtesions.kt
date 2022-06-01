package com.gotov.getmeapp.utils.model

fun String.removeTgTag(): String {
    return this.replace("@", "")
}
