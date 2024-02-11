package com.devrachit.insta.hilt

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import com.devrachit.insta.Constants.Constants.Companion.API_KEY
import com.devrachit.insta.Models.ProfileSharedViewModel

import com.devrachit.insta.Models.SharedViewModel
import com.devrachit.insta.R
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import java.lang.invoke.MethodHandles.identity
import com.google.android.gms.auth.api.identity.*
import dagger.hilt.android.qualifiers.ApplicationContext
import io.getstream.chat.android.offline.plugin.factory.StreamOfflinePluginFactory
import javax.inject.Singleton
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.models.Config
import io.getstream.chat.android.state.plugin.config.StatePluginConfig
import io.getstream.chat.android.state.plugin.factory.StreamStatePluginFactory


@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {
    @Provides
    fun provideAuth(): FirebaseAuth = Firebase.auth

    @Provides
    fun provideFirestore(): FirebaseFirestore = Firebase.firestore

    @Provides
    fun provideStorage(): FirebaseStorage = Firebase.storage
    @Provides
    fun provideSharedViewModel(): SharedViewModel = SharedViewModel()

    @Provides
    fun provideOfflinePluginFactory(@ApplicationContext context: Context) =
        StreamOfflinePluginFactory(
            appContext = context
        )

    @Provides
    fun provideStatePluginFactory(@ApplicationContext context: Context)=
        StreamStatePluginFactory(
            config = StatePluginConfig(),
            appContext = context)


    @Singleton
    @Provides
    fun provideChatClient(@ApplicationContext context: Context, offlinePluginFactory: StreamOfflinePluginFactory,statePluginFactory: StreamStatePluginFactory) =
        ChatClient.Builder(context.getString(R.string.key), context)
            .withPlugins(offlinePluginFactory,statePluginFactory)
            .logLevel(ChatLogLevel.ALL)
            .build()
}