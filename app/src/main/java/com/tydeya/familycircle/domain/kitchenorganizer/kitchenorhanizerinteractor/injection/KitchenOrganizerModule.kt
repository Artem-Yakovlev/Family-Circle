package com.tydeya.familycircle.domain.kitchenorganizer.kitchenorhanizerinteractor.injection

import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorhanizerinteractor.details.KitchenOrganizerInteractor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class KitchenOrganizerModule {

    @Singleton
    @Provides
    fun provideKitchenOrganizerInteractor(): KitchenOrganizerInteractor {
        return KitchenOrganizerInteractor()
    }
}