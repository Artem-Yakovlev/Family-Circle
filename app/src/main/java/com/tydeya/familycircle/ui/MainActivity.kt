package com.tydeya.familycircle.ui

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.tydeya.familycircle.App
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.conversationsinteractor.abstraction.ConversationInteractorCallback
import com.tydeya.familycircle.data.conversationsinteractor.details.ConversationInteractor
import com.tydeya.familycircle.ui.firststartpage.FirstStartActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ConversationInteractorCallback {

    private var currentNavController: LiveData<NavController>? = null

    private var conversationInteractor: ConversationInteractor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (verificationCheck()) {
            conversationInteractor = App.getComponent().conversationInteractor
            if (savedInstanceState == null) {
                setupBottomNavigationBar()
            }
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {

        val navGraphIds = listOf(R.navigation.live, R.navigation.plan,
                R.navigation.correspondence, R.navigation.map, R.navigation.manager_menu)

        currentNavController = main_bottom_navigation_view.setupWithNavController(
                navGraphIds = navGraphIds,
                fragmentManager = supportFragmentManager,
                containerId = R.id.nav_host_container,
                intent = intent
        )
    }

    private fun verificationCheck(): Boolean {

        if (FirebaseAuth.getInstance().currentUser == null) {
            val intent = Intent(this, FirstStartActivity::class.java).apply { }
            startActivity(intent)
            this.finish()
            return false
        }
        return true
    }

    /**
     * Interaction callbacks
     * */

    override fun onResume() {
        super.onResume()
        conversationInteractor!!.subscribe(this)
        updateBadges()
    }

    override fun onPause() {
        super.onPause()
        conversationInteractor!!.unsubscribe(this)
    }

    /**
     * Bottom navigation badges
     * */

    override fun conversationsDataUpdated() {
        updateBadges()
    }

    private fun updateBadges() {

        if (conversationInteractor!!.actualConversationBadges == 0) {

            main_bottom_navigation_view.removeBadge(R.id.correspondence)

        } else {

            main_bottom_navigation_view.getOrCreateBadge(R.id.correspondence)
                    .backgroundColor = resources.getColor(R.color.colorConversationBadge)

            main_bottom_navigation_view.getOrCreateBadge(R.id.correspondence)
                    .number = conversationInteractor!!.actualConversationBadges
        }
    }

}
