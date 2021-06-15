package io.tango.challenge.core.data

abstract class Mapper<T, R> {

    abstract fun map(from: T): R

    open fun mapReverse(from: R): T {
        throw UnsupportedOperationException("mapReverse method was not overwritten")
    }

    fun mapList(from: List<T>): List<R> {
        return from.map { map(it) }
    }
}