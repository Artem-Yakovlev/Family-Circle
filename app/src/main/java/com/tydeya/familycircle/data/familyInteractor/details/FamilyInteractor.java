package com.tydeya.familycircle.data.familyInteractor.details;

import com.tydeya.familycircle.data.familyInteractor.abstraction.FamilyInteractorCallback;
import com.tydeya.familycircle.data.familyInteractor.abstraction.FamilyInteractorObservable;
import com.tydeya.familycircle.data.familyInteractor.abstraction.FamilyNetworkInteractor;
import com.tydeya.familycircle.data.familyInteractor.abstraction.FamilyNetworkInteractorCallback;
import com.tydeya.familycircle.domain.family.Family;
import com.tydeya.familycircle.domain.family.description.FamilyDescription;
import com.tydeya.familycircle.domain.familymember.FamilyMember;

import java.util.ArrayList;

public class FamilyInteractor implements FamilyNetworkInteractorCallback, FamilyInteractorObservable {

    private FamilyNetworkInteractor networkInteractor;
    private ArrayList<FamilyInteractorCallback> observers;

    private ArrayList<Family> families = new ArrayList<>();
    private int actualFamilyIndex = 0;


    public FamilyInteractor() {
        observers = new ArrayList<>();
        networkInteractor = new FamilyNetworkInteractorImpl(this);

        prepareFamilyMemberData();
    }

    public Family getActualFamily() {
        if (families.size() != 0) {
            return families.get(actualFamilyIndex);
        }
        return new Family(0, new FamilyDescription("Test family"), new ArrayList<>());
    }

    private void prepareFamilyMemberData() {
        networkInteractor.requireMembersDataFromServer();
    }

    @Override
    public void memberDataFromServerUpdate(ArrayList<FamilyMember> members) {
        families = new ArrayList<>();
        FamilyDescription description = new FamilyDescription("Test family");
        families.add(new Family(0, description, members));
        notifyObserversMemberDataUpdated();
    }

    private void notifyObserversMemberDataUpdated() {
        for (FamilyInteractorCallback callback: observers) {
            callback.memberDataUpdated();
        }
    }

    @Override
    public void subscribe(FamilyInteractorCallback callback) {
        if (!observers.contains(callback)) {
            observers.add(callback);
        }
    }

    @Override
    public void unsubscribe(FamilyInteractorCallback callback) {
        observers.remove(callback);
    }
}
