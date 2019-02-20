package com.example.sleeperappmessaging

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.sleeperappmessaging.MessageFragment.OnListFragmentInteractionListener
import com.example.sleeperappmessaging.dummy.MessageContent
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_message.view.*

class MyMessageRecyclerViewAdapter(
    private val mValues: List<MessageContent.Message>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MyMessageRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as MessageContent.Message
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_message, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.userName.text = item.userName
        holder.timeStamp.text = item.timeStamp
        Picasso.get()
            .load(item.userImage)
            .into(holder.userImage)

        if(item.hasMultiple){
            holder.userName.visibility = View.GONE
            holder.userImage.visibility = View.INVISIBLE
            holder.timeStamp.visibility = View.GONE
        } else {
            holder.userName.visibility = View.VISIBLE
            holder.userImage.visibility = View.VISIBLE
            holder.timeStamp.visibility = View.VISIBLE
        }

        if(item.hasGiphy && item.giphyUrl.isNotEmpty()){
            holder.messageContent.text = item.content + "\n" + item.giphyUrl
        } else {
            holder.messageContent.text = item.content
        }

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val timeStamp: TextView = mView.timeStampText
        val messageContent: TextView = mView.messageContentText
        val userName: TextView = mView.userNameText
        val userImage: ImageView = mView.userImageIcon
        override fun toString(): String {
            return super.toString() + " '" + timeStamp.text + "'" +
                    " '" + messageContent.text + "'" +
                    " '" + userName.text + "'"
        }
    }
}
