package com.tydeya.familycircle.domain.oldfamilyinteractor.abstraction;

public interface FamilyInteractorObservable {

    void subscribe(FamilyInteractorCallback callback);

    void unsubscribe(FamilyInteractorCallback callback);
}
