package com.tydeya.familycircle.data.familyinteractor.abstraction;

public interface FamilyInteractorObservable {

    void subscribe(FamilyInteractorCallback callback);

    void unsubscribe(FamilyInteractorCallback callback);
}
