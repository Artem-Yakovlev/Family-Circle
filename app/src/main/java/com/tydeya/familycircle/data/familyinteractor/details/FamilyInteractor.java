package com.tydeya.familycircle.data.familyinteractor.details;

import com.tydeya.familycircle.App;
import com.tydeya.familycircle.data.familyassistant.abstraction.FamilyAssistant;
import com.tydeya.familycircle.data.familyassistant.details.FamilyAssistantImpl;
import com.tydeya.familycircle.data.familyinteractor.abstraction.FamilyInteractorCallback;
import com.tydeya.familycircle.data.familyinteractor.abstraction.FamilyInteractorObservable;
import com.tydeya.familycircle.data.familyinteractor.abstraction.FamilyNetworkInteractor;
import com.tydeya.familycircle.data.familyinteractor.abstraction.FamilyNetworkInteractorCallback;
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
        prepareFamilyData();
    }

    public Family getActualFamily() {
        if (families.size() != 0) {
            return families.get(actualFamilyIndex);
        }
        return new Family(0, new FamilyDescription("Test family"), new ArrayList<>());
    }

    public FamilyAssistant getFamilyAssistant() {
        return new FamilyAssistantImpl(getActualFamily());
    }

    private void prepareFamilyData() {
        ArrayList<FamilyMember> familyMembers = new ArrayList<>(App.getDatabase().familyMembersDao().getAll());

        families.add(new Family(actualFamilyIndex, new FamilyDescription("Test family"), familyMembers));

        networkInteractor.requireMembersDataFromServer();
    }

    @Override
    public void memberDataFromServerUpdate(ArrayList<FamilyMember> members) {
        families.get(actualFamilyIndex).setFamilyMembers(members);

        App.getDatabase().familyMembersDao().update(families.get(actualFamilyIndex).getFamilyMembers());

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
