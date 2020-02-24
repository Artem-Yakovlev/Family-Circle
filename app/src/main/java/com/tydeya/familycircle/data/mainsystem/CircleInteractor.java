package com.tydeya.familycircle.data.mainsystem;

import com.tydeya.familycircle.data.familyInteractor.abstraction.FamilyInteractor;
import com.tydeya.familycircle.data.networkinteraction.abstraction.NetworkInteractor;
import com.tydeya.familycircle.data.settingsinteraction.abstraction.SettingsInteractor;
import com.tydeya.familycircle.data.userinteractor.abstraction.UserInteractor;

public class CircleInteractor {

    private FamilyInteractor familyInteractor;
    private NetworkInteractor networkInteractor;
    private SettingsInteractor settingsInteractor;
    private UserInteractor userInteractor;

    public CircleInteractor(FamilyInteractor familyInteractor, NetworkInteractor networkInteractor,
                            SettingsInteractor settingsInteractor, UserInteractor userInteractor) {

        this.familyInteractor = familyInteractor;
        this.networkInteractor = networkInteractor;
        this.settingsInteractor = settingsInteractor;
        this.userInteractor = userInteractor;
    }

    public FamilyInteractor getFamilyInteractor() {
        return familyInteractor;
    }

    public void setFamilyInteractor(FamilyInteractor familyInteractor) {
        this.familyInteractor = familyInteractor;
    }

    public NetworkInteractor getNetworkInteractor() {
        return networkInteractor;
    }

    public void setNetworkInteractor(NetworkInteractor networkInteractor) {
        this.networkInteractor = networkInteractor;
    }

    public SettingsInteractor getSettingsInteractor() {
        return settingsInteractor;
    }

    public void setSettingsInteractor(SettingsInteractor settingsInteractor) {
        this.settingsInteractor = settingsInteractor;
    }

    public UserInteractor getUserInteractor() {
        return userInteractor;
    }

    public void setUserInteractor(UserInteractor userInteractor) {
        this.userInteractor = userInteractor;
    }
}
