package com.footballio.di;

import com.footballio.network.FootballService;
import com.footballio.repository.DashboardRepository;
import com.footballio.repository.LoginRepository;
import com.footballio.repository.ProfileRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public final class RepositoryModule {
    @Singleton
    @Provides
    public static DashboardRepository provideDashboardRepository(FootballService service) {
        return new DashboardRepository(service);
    }

    @Singleton
    @Provides
    public static LoginRepository provideLoginRepository(FootballService service) {
        return new LoginRepository(service);
    }
    @Singleton
    @Provides
    public static ProfileRepository provideProfileRepository(FootballService service) {
        return new ProfileRepository(service);
    }
}
