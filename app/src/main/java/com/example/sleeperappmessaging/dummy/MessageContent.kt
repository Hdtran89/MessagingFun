package com.example.sleeperappmessaging.dummy

import java.util.ArrayList
import java.util.HashMap

object MessageContent {

    /**
     * An array of sample (dummy) items.
     */
    val ITEMS: MutableList<Message> = ArrayList()

    /**
     * A map of sample (dummy) items, by ID.
     */
    val ITEM_MAP: MutableMap<String, Message> = HashMap()

    private val COUNT = 25

    init {
        // Add some sample items.
//        for (i in 1..COUNT) {
//            addItem(createDummyItem(i))
//        }
    }

//    private fun createDummyItem(position: Int): Message {
//        return Message(position.toString(), "Item " + position, makeDetails(position))
//    }

    private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
        builder.append("Details about Item: ").append(position)
        for (i in 0..position - 1) {
            builder.append("\nMore details information here.")
        }
        return builder.toString()
    }

    /**
     * A dummy item representing a piece of content.
     */
    data class Message(val userName: String,
                       val content: String,
                       val timeStamp: String,
                       val userImage: String,
                       val giphyUrl : String,
                       val hasGiphy: Boolean = false,
                       val hasMultiple: Boolean = false) {
        override fun toString(): String = content
    }
}
