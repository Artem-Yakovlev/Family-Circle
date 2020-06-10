package com.tydeya.familycircle.presentation.ui.livepart.memberpersonpage.twitterrecycler

import androidx.recyclerview.widget.RecyclerView
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
    }

}
