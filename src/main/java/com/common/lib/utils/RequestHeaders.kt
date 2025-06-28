package com.common.lib.utils

interface RequestHeaders<I> {
    val id: I?
    val ip: String
    val dominio: String
    val usuario: String
    val idBussines: Long?
    val proceso: String
    val topic: String
    val token: String?
}
