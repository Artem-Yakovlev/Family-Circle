package com.tydeya.familycircle.presentation.ui.livepart.memberpersonpage.twitterrecycler

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.familymember.Tweet
import com.tydeya.familycircle.databinding.RecyclerItemTwitterBinding
import java.text.SimpleDateFormat
import java.util.*


class TweetViewHolder(
        private val binding: RecyclerItemTwitterBinding
) :
        RecyclerView.ViewHolder(binding.root) {

    fun bindData(tweet: Tweet) {
        binding.tweetAuthor.text = tweet.name
        binding.tweetText.text = tweet.text
        binding.tweetTime.text = SimpleDateFormat("E, dd MMM yyyy", Locale.getDefault())
                .format(tweet.date)
        binding.root.setOnLongClickListener {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, itemView.context
                        .getString(R.string.tweet_intent_placeholder, tweet.text, tweet.name)
                )
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            itemView.context.startActivity(shareIntent)
            return@setOnLongClickListener true

        }

    }

}
