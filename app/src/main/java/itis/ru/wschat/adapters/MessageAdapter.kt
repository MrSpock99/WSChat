package itis.ru.wschat.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import itis.ru.wschat.R
import itis.ru.wschat.models.Message

class MessageAdapter internal constructor(
    private val uidFrom: String, private var data: MutableList<Message>
) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {
    private var view: View? = null

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.setMessage(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return if (viewType == R.layout.item_message_to) {
            view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_message_to, parent, false)
            MessageViewHolder()
        } else {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_message_from, parent, false)
            MessageViewHolder()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (uidFrom != data[position].user) {
            R.layout.item_message_to
        } else {
            R.layout.item_message_from
        }
    }

    override fun getItemId(position: Int): Long {
        return data[position].id
    }

    fun updateData(list: MutableList<Message>) {
        val diffResult = DiffUtil.calculateDiff(RecentDiffUtilCallback(this.data, list))
        diffResult.dispatchUpdatesTo(this)
        this.data = list
    }

    inner class MessageViewHolder : RecyclerView.ViewHolder(view!!) {
        internal fun setMessage(message: Message) {
            val textView = view?.findViewById<TextView>(R.id.text_view)
            textView?.text = message.message
        }
    }

    class RecentDiffUtilCallback internal constructor(
        private val oldList: List<Message>,
        private val newList: List<Message>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val old = oldList[oldItemPosition]
            val new = newList[newItemPosition]
            return old.id == new.id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val old = oldList[oldItemPosition]
            val new = newList[newItemPosition]
            return old == new
        }
    }


}
