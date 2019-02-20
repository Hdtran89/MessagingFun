package com.example.sleeperappmessaging

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import com.example.sleeperappmessaging.dummy.MessageContent
import java.text.SimpleDateFormat
import java.util.*
import android.view.inputmethod.InputMethodManager
import com.giphy.sdk.core.models.enums.MediaType
import com.giphy.sdk.core.network.api.CompletionHandler
import com.giphy.sdk.core.network.api.GPHApiClient
import com.giphy.sdk.core.network.response.MediaResponse

class MainActivity : AppCompatActivity(), MessageFragment.OnListFragmentInteractionListener {

    private lateinit var sendButton : Button
    private lateinit var messageText : EditText
    private lateinit var displayFrameLayout: FrameLayout
    private var count = 0
    private var hasGiphy = false
    private var giphyUrl = ""
    private var hasMultiple = false
    private var messageContent : Editable ?= null
    private val userName = "Android User"
    private val userImage = "https://via.placeholder.com/50/09f/fff.png"
    private val API_KEY_GIPHY = "Awq5410QQl0416nJogqlsinldM2s9PCA"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sendButton = findViewById(R.id.sendButton)
        messageText = findViewById(R.id.messageText)
        displayFrameLayout = findViewById(R.id.messageDisplayFrameLayout)
        val messageFragment = MessageFragment()
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.messageDisplayFrameLayout,messageFragment).commit()
        val client = GPHApiClient(API_KEY_GIPHY)
        sendButton.setOnClickListener {
            count ++
            messageContent = messageText.text
            if(count > 1){
                hasMultiple = true
                if(messageContent!!.contains("/giphy")){
                    getGif(client)
                } else {
                    val message = MessageContent.Message(userName,
                        messageContent.toString(),
                        getCurrentTimeStamp().orEmpty(),
                        userImage,
                        giphyUrl,
                        hasGiphy,
                        hasMultiple)
                    sendItem(message)
                }
            } else {
                hasMultiple = false
                if(messageContent!!.contains("/giphy")){
                    getGif(client)
                } else {
                    val message = MessageContent.Message(userName,
                        messageContent.toString(),
                        getCurrentTimeStamp().orEmpty(),
                        userImage,
                        giphyUrl ,
                        hasGiphy,
                        hasMultiple)
                    sendItem(message)
                }
            }

            val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                currentFocus!!.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    fun getGif(client:GPHApiClient){
        this.runOnUiThread {
            client.random("cats dogs", MediaType.gif, null, object : CompletionHandler<MediaResponse> {
                override fun onComplete(result: MediaResponse?, e: Throwable?) {
                    if (result == null) {
                        // Do what you want to do with the error
                    } else {
                        if (result.data != null) {
                            hasGiphy = true
                            giphyUrl = result.data.url.orEmpty()
                            Log.v("giphy", giphyUrl)
                            val message = MessageContent.Message(userName,
                                messageContent.toString(),
                                getCurrentTimeStamp().orEmpty(),
                                userImage,
                                giphyUrl ,
                                hasGiphy,
                                hasMultiple)
                            sendItem(message)
                        } else {
                            Log.e("giphy error", "No results found")
                        }
                    }
                }
            })
        }
    }
    fun sendItem(message: MessageContent.Message){
        addItem(message)
        val adapter = MyMessageRecyclerViewAdapter(MessageContent.ITEMS,null)
        adapter.notifyDataSetChanged()
        messageText.text.clear()
        giphyUrl = ""
    }
    fun addItem(item: MessageContent.Message) {
        MessageContent.ITEMS.add(item)
        MessageContent.ITEM_MAP.put(item.userName, item)
    }

    fun getCurrentTimeStamp(): String? {
        try {
            val dateFormat = SimpleDateFormat("HH:mm")
            return dateFormat.format(Date())
        } catch (e: Exception) {
            e.printStackTrace()

            return null
        }

    }
    override fun onListFragmentInteraction(item: MessageContent.Message?) {

    }
}
