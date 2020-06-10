package com.tydeya.familycircle.domain.tweets

import com.tydeya.familycircle.data.familymember.Tweet
import com.tydeya.familycircle.utils.Resource

interface TweetsNetworkInteractorCallback {

    fun tweetsUpdated(tweetsServerResource: Resource<ArrayList<Tweet>>)

}
