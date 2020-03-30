package com.tydeya.familycircle.ui.conversationpart.inconversation

import android.os.Bundle
import android.os.Messenger
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.FragmentTransaction
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.constants.Application.CONVERSATION_ID
import com.tydeya.familycircle.domain.messenger.interactor.details.MessengerInteractor
import com.tydeya.familycircle.ui.conversationpart.inconversation.conversationfragment.InConversationFragment
import javax.inject.Inject


class InConversationActivity : AppCompatActivity(R.layout.activity_in_conversation) {



    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }
}
