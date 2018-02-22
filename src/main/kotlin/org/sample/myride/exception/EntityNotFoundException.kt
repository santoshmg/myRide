package org.sample.myride.exception

class EntityNotFoundException(val errorCode: String,
                              val errorDescription: String) : RuntimeException()