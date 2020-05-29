package com.tydeya.familycircle.domain.familyinteractor.details;

import com.tydeya.familycircle.App;
import com.tydeya.familycircle.data.family.Family;
import com.tydeya.familycircle.data.familymember.FamilyMember;
import com.tydeya.familycircle.domain.familyinteractor.abstraction.FamilyInteractorCallback;
import com.tydeya.familycircle.domain.familyinteractor.abstraction.FamilyInteractorObservable;
import com.tydeya.familycircle.domain.familyinteractor.abstraction.FamilyNetworkInteractor;
import com.tydeya.familycircle.domain.familyinteractor.abstraction.FamilyNetworkInteractorCallback;

import java.util.ArrayList;

public class FamilyInteractor implements FamilyNetworkInteractorCallback, FamilyInteractorObservable {

    private FamilyNetworkInteractor networkInteractor;
    private ArrayList<FamilyInteractorCallback> observers;

    private ArrayList<Family> families = new ArrayList<>();
    private int actualFamilyIndex = 0;

    public FamilyInteractor() {
        observers = new ArrayList<>();
        networkInteractor = new FamilyNetworkInteractorImpl(this);
        prepareFamilyData();
    }

    public Family getActualFamily() {
        if (families.size() != 0) {
            return families.get(actualFamilyIndex);
        }
        return new Family(0, "Test family", new ArrayList<>());
    }

    private void prepareFamilyData() {
        ArrayList<FamilyMember> familyMembers = new ArrayList<>(App.getDatabase().familyMembersDao().getAll());

        families.add(new Family(actualFamilyIndex, "Test family", familyMembers));

        networkInteractor.requireMembersDataFromServer();
    }

    @Override
    public void memberDataFromServerUpdate(ArrayList<FamilyMember> members) {

        families.get(actualFamilyIndex).setFamilyMembers(members);

        App.getDatabase().familyMembersDao().update(families.get(actualFamilyIndex).getFamilyMembers());

        notifyObserversMemberDataUpdated();
    }


    private void notifyObserversMemberDataUpdated() {
        for (FamilyInteractorCallback callback : observers) {
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
