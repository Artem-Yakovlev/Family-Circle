package com.tydeya.familycircle.domain.tweets

import com.google.firebase.firestore.*
import com.tydeya.familycircle.data.constants.FireStore.TWEET_COLLECTION
import com.tydeya.familycircle.data.constants.FireStore.TWEET_TIME
import com.tydeya.familycircle.data.familymember.Tweet
import com.tydeya.familycircle.domain.familyinteraction.convertServerDataToTweet
import com.tydeya.familycircle.domain.kitchenorganizer.utils.EventListenerObservable
import com.tydeya.familycircle.utils.Resource
import com.tydeya.familycircle.utils.extensions.firestoreFamily
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TweetsNetworkInteractor(
        private val familyId: String,
        private val callback: TweetsNetworkInteractorCallback
) :
        EventListener<QuerySnapshot>, EventListenerObservable {

    private val tweetsCollectionRef = firestoreFamily(familyId)
            .collection(TWEET_COLLECTION)
            .orderBy(TWEET_TIME, Query.Direction.DESCENDING)

    private lateinit var registration: ListenerRegistration

    override fun register() {
        registration = tweetsCollectionRef.addSnapshotListener(this)
    }

    override fun unregister() {
        registration.remove()
    }

    override fun onEvent(snapshot: QuerySnapshot?, exception: FirebaseFirestoreException?) {
        if (exception == null) {
            GlobalScope.launch(Dispatchers.Default) {
                snapshot?.let {
                    val tweets = ArrayList<Tweet>()
                    for (document in it.documents) {
                        tweets.add(convertServerDataToTweet(document))
                    }
                    withContext(Dispatchers.Main) {
                        callback.tweetsUpdated(Resource.Success(tweets))
                    }
                }
            }
        } else {
            callback.tweetsUpdated(Resource.Failure(exception))
        }
    }

}
