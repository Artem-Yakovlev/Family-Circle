package com.tydeya.familycircle.presentation.ui.livepart.memberpersonpage.twitterrecycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.data.familymember.FamilyMember
import com.tydeya.familycircle.data.familymember.Tweet
import com.tydeya.familycircle.databinding.RecyclerItemTwitterBinding
import com.tydeya.familycircle.utils.extensions.fillWithMembersData

class TwitterRecyclerViewAdapter(
        private val tweets: ArrayList<Tweet> = ArrayList()
) :
        RecyclerView.Adapter<TweetViewHolder>() {

    private val familyMembers: ArrayList<FamilyMember> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            TweetViewHolder(RecyclerItemTwitterBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false)
            )

    override fun onBindViewHolder(holder: TweetViewHolder, position: Int) {
        holder.bindData(tweets[position])
    }

    override fun getItemCount() = tweets.size

    fun refreshFamilyMembers(familyMembers: ArrayList<FamilyMember>) {
        this.familyMembers.clear()
        this.familyMembers.addAll(familyMembers)
        refreshData()
    }

    fun refreshTweets(tweets: ArrayList<Tweet>) {
        this.tweets.clear()
        this.tweets.addAll(tweets)
        refreshData()
    }

    private fun refreshData() {
        val filledTweets = tweets.fillWithMembersData(familyMembers)
        this.tweets.clear()
        this.tweets.addAll(filledTweets)
        notifyDataSetChanged()
    }

}
