package com.tydeya.familycircle.domain.taskorganizer.inject

import com.tydeya.familycircle.domain.taskorganizer.interactor.details.TasksOrganizerInteractor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class TasksOrganizerModule {

    @Singleton
    @Provides
    fun provideTasksOrganizerInteractor(): TasksOrganizerInteractor {
        return TasksOrganizerInteractor()
    }
}