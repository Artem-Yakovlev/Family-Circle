package com.tydeya.familycircle.data.familyInteractor.abstraction;

public interface FamilyInteractorObservable {

    void subscribe(FamilyInteractorCallback callback);

    void unsubscribe(FamilyInteractorCallback callback);
}
