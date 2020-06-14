package com.tydeya.familycircle.presentation.viewmodel.familyviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tydeya.familycircle.data.family.Family
import com.tydeya.familycircle.data.familymember.FamilyMember
import com.tydeya.familycircle.data.familymember.Tweet
import com.tydeya.familycircle.domain.familyinteraction.FamilyInteractor
import com.tydeya.familycircle.domain.familyinteraction.FamilyNetworkListener
import com.tydeya.familycircle.domain.familyinteraction.FamilyNetworkListenerCallback
import com.tydeya.familycircle.domain.familyselection.addFamilyMemberInFirestore
import com.tydeya.familycircle.domain.familyselection.addTweetInFirestore
import com.tydeya.familycircle.domain.tweets.TweetsNetworkInteractor
import com.tydeya.familycircle.domain.tweets.TweetsNetworkInteractorCallback
import com.tydeya.familycircle.presentation.viewmodel.base.FirestoreViewModel
import com.tydeya.familycircle.utils.Resource
import com.tydeya.familycircle.utils.extensions.getUserPhone
import java.util.*
import kotlin.collections.ArrayList

class FamilyViewModel(
        familyId: String
) :
        FirestoreViewModel(), FamilyNetworkListenerCallback, TweetsNetworkInteractorCallback {

    private val familyDataLiveData = MutableLiveData<Resource<Family>>(Resource.Loading())
    val familyData: LiveData<Resource<Family>> get() = familyDataLiveData

    private val familyMembersLiveData = MutableLiveData<Resource<ArrayList<FamilyMember>>>(Resource.Loading())
    val familyMembers: LiveData<Resource<ArrayList<FamilyMember>>> get() = familyMembersLiveData

    private val familyNetworkInteractor = FamilyNetworkListener(familyId, this)
    private var familyInteractor = FamilyInteractor(ArrayList())

    private val tweetsNetworkInteractor = TweetsNetworkInteractor(familyId, this)

    private val tweetsLiveData = MutableLiveData<Resource<ArrayList<Tweet>>>(Resource.Loading())
    val tweets: LiveData<Resource<ArrayList<Tweet>>> get() = tweetsLiveData

    init {
        familyNetworkInteractor.register()
        tweetsNetworkInteractor.register()
    }

    override fun familyDataUpdated(familyServerResource: Resource<Family>) {
        familyDataLiveData.value = familyServerResource
    }

    override fun familyMembersUpdated(membersServerResource: Resource<ArrayList<FamilyMember>>) {
        if (membersServerResource is Resource.Success) {
            familyInteractor.refreshData(membersServerResource.data)
        } else {
            familyInteractor.refreshData(ArrayList())
        }
        familyMembersLiveData.value = membersServerResource
    }

    override fun tweetsUpdated(tweetsServerResource: Resource<ArrayList<Tweet>>) {
        tweetsLiveData.value = tweetsServerResource
    }

    override fun onCleared() {
        super.onCleared()
        familyNetworkInteractor.unregister()
        tweetsNetworkInteractor.unregister()
    }

    /**
     * Interaction
     * */

    fun getFamilyMemberByNumber(phoneNumber: String): Resource<FamilyMember> {
        return familyInteractor.familyMemberByPhone(phoneNumber)
    }

    fun inviteUserToFamily(familyId: String, phoneNumber: String) {
        addFamilyMemberInFirestore(familyId, phoneNumber)
    }

    fun addTweet(familyId: String, text: String) {
        addTweetInFirestore(familyId, Tweet(
                authorPhone = getUserPhone(),
                text = text,
                date = Date()
        ))
    }

}
