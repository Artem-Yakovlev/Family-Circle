package com.tydeya.familycircle.domain.familyinteractor.abstraction;

public interface FamilyInteractorObservable {

    void subscribe(FamilyInteractorCallback callback);

    void unsubscribe(FamilyInteractorCallback callback);
}
