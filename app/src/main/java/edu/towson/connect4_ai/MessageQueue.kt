package edu.towson.connect4_ai

import androidx.lifecycle.MutableLiveData

class MessageQueue {

    companion object {
        val Channel: MutableLiveData<String> = MutableLiveData()
    }
}