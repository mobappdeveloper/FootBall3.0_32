package com.footballio.di;

import com.footballio.repository.DashboardRepository;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public class RespositoryModule {

    public static DashboardRepository provideDashboardRepository() {

        return new DashboardRepository();
    }
}
