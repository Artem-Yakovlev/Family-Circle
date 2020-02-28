package com.tydeya.familycircle.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.tydeya.familycircle.App;
import com.tydeya.familycircle.R;
import com.tydeya.familycircle.data.conversationsinteractor.abstraction.ConversationInteractorCallback;
import com.tydeya.familycircle.data.conversationsinteractor.details.ConversationInteractor;
import com.tydeya.familycircle.ui.firststartpage.FirstStartActivity;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements ConversationInteractorCallback {

    private BottomNavigationView bottomNavigationView;

    @Inject
    ConversationInteractor conversationInteractor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verificationCheck();
        App.getComponent().injectActivity(this);

        bottomNavigationView = findViewById(R.id.main_bottom_navigation_view);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.main_fragment_container);
        assert navHostFragment != null;
        NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.getNavController());
    }

    @Override
    protected void onStart() {
        super.onStart();
        verificationCheck();
    }

    private void verificationCheck() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            Intent intent = new Intent(this, FirstStartActivity.class);
            startActivity(intent);
            this.finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        conversationInteractor.subscribe(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        conversationInteractor.unsubscribe(this);
    }

    @Override
    public void conversationsDataUpdated() {
        if (conversationInteractor.getActualConversationBadges() == 0) {

            bottomNavigationView.removeBadge(R.id.mainConversationPage);

        } else {

            bottomNavigationView.getOrCreateBadge(R.id.mainConversationPage)
                    .setBackgroundColor(getResources().getColor(R.color.colorConversationBadge));

            bottomNavigationView.getOrCreateBadge(R.id.mainConversationPage)
                    .setNumber(conversationInteractor.getActualConversationBadges());
        }
    }
}
