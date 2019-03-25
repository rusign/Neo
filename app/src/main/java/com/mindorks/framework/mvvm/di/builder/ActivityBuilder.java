package com.mindorks.framework.mvvm.di.builder;

import com.mindorks.framework.mvvm.ui.call.CallActivity;
import com.mindorks.framework.mvvm.ui.call.CallActivityModule;
import com.mindorks.framework.mvvm.ui.chat.ChatActivity;
import com.mindorks.framework.mvvm.ui.chat.ChatActivityModule;
import com.mindorks.framework.mvvm.ui.chat.chatadd.ChatAddActivity;
import com.mindorks.framework.mvvm.ui.chat.chatadd.ChatAddActivityModule;
import com.mindorks.framework.mvvm.ui.chatlist.ChatlistActivity;
import com.mindorks.framework.mvvm.ui.chatlist.ChatlistActivityModule;
import com.mindorks.framework.mvvm.ui.chatlist.ChatlistProvider;
import com.mindorks.framework.mvvm.ui.contacts.ContactsActivity;
import com.mindorks.framework.mvvm.ui.contacts.ContactsActivityModule;
import com.mindorks.framework.mvvm.ui.contacts.circlecontacts.CircleContactsActivityModule;
import com.mindorks.framework.mvvm.ui.contacts.circlecontacts.CircleContactsActivity;
import com.mindorks.framework.mvvm.ui.contacts.circleinvitation.CircleInvitationActivity;
import com.mindorks.framework.mvvm.ui.contacts.circleinvitation.CircleInvitationActivityModule;
import com.mindorks.framework.mvvm.ui.contacts.contact.ContactActivity;
import com.mindorks.framework.mvvm.ui.contacts.contact.ContactActivityModule;
import com.mindorks.framework.mvvm.ui.inscription.Email.InscriptionEmailActivity;
import com.mindorks.framework.mvvm.ui.inscription.Email.InscriptionEmailActivityModule;
import com.mindorks.framework.mvvm.ui.inscription.Mdp.InscriptionMdpActivity;
import com.mindorks.framework.mvvm.ui.inscription.Mdp.InscriptionMdpActivityModule;
import com.mindorks.framework.mvvm.ui.settings.SettingsActivity;
import com.mindorks.framework.mvvm.ui.settings.SettingsActivityModule;
import com.mindorks.framework.mvvm.ui.about.AboutFragmentProvider;
import com.mindorks.framework.mvvm.ui.feed.FeedActivity;
import com.mindorks.framework.mvvm.ui.feed.FeedActivityModule;
import com.mindorks.framework.mvvm.ui.feed.blogs.BlogFragmentProvider;
import com.mindorks.framework.mvvm.ui.feed.opensource.OpenSourceFragmentProvider;
import com.mindorks.framework.mvvm.ui.firstPage.FirstPageActivity;
import com.mindorks.framework.mvvm.ui.firstPage.FirstPageActivityModule;
import com.mindorks.framework.mvvm.ui.home.HomeActivity;
import com.mindorks.framework.mvvm.ui.home.HomeActivityModule;
import com.mindorks.framework.mvvm.ui.inscription.InscriptionActivity;
import com.mindorks.framework.mvvm.ui.inscription.InscriptionActivityModule;
import com.mindorks.framework.mvvm.ui.inscription.Name.InscriptionNameActivity;
import com.mindorks.framework.mvvm.ui.inscription.Name.InscriptionNameActivityModule;
import com.mindorks.framework.mvvm.ui.inscription.Birth.InscriptionBirthActivity;
import com.mindorks.framework.mvvm.ui.inscription.Birth.InscriptionBirthActivityModule;
import com.mindorks.framework.mvvm.ui.login.LoginActivity;
import com.mindorks.framework.mvvm.ui.login.LoginActivityModule;
import com.mindorks.framework.mvvm.ui.main.MainActivity;
import com.mindorks.framework.mvvm.ui.main.MainActivityModule;
import com.mindorks.framework.mvvm.ui.main.rating.RateUsDialogProvider;
import com.mindorks.framework.mvvm.ui.splash.SplashActivity;
import com.mindorks.framework.mvvm.ui.splash.SplashActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {
            FeedActivityModule.class,
            BlogFragmentProvider.class,
            OpenSourceFragmentProvider.class})
    abstract FeedActivity bindFeedActivity();

    @ContributesAndroidInjector(modules = LoginActivityModule.class)
    abstract LoginActivity bindLoginActivity();

    @ContributesAndroidInjector(modules = FirstPageActivityModule.class)
    abstract FirstPageActivity bindFirstPageActivity();

    @ContributesAndroidInjector(modules = InscriptionActivityModule.class)
    abstract InscriptionActivity bindInscriptionActivity();
    @ContributesAndroidInjector(modules = InscriptionNameActivityModule.class)
    abstract InscriptionNameActivity bindInscriptionNameActivity();
    @ContributesAndroidInjector(modules = InscriptionBirthActivityModule.class)
    abstract InscriptionBirthActivity bindInscriptionBirthActivity();
    @ContributesAndroidInjector(modules = InscriptionEmailActivityModule.class)
    abstract InscriptionEmailActivity bindInscriptionEmailActivity();
    @ContributesAndroidInjector(modules = InscriptionMdpActivityModule.class)
    abstract InscriptionMdpActivity bindInscriptionMdpActivity();

    @ContributesAndroidInjector(modules = HomeActivityModule.class)
    abstract HomeActivity bindHomeActivity();

    @ContributesAndroidInjector(modules = SettingsActivityModule.class)
    abstract SettingsActivity bindSettingsActivity();

    @ContributesAndroidInjector(modules = {ChatlistActivityModule.class, ChatlistProvider.class})
    abstract ChatlistActivity bindChatlistActivity();

    @ContributesAndroidInjector(modules = ChatActivityModule.class)
    abstract ChatActivity bindChatActivity();
    @ContributesAndroidInjector(modules = ChatAddActivityModule.class)
    abstract ChatAddActivity bindChatAddActivity();

    @ContributesAndroidInjector(modules = ContactsActivityModule.class)
    abstract ContactsActivity bindContactsActivity();
    @ContributesAndroidInjector(modules = CircleContactsActivityModule.class)
    abstract CircleContactsActivity bindCircleContactsActivity();
    @ContributesAndroidInjector(modules = ContactActivityModule.class)
    abstract ContactActivity bindContactActivity();

    @ContributesAndroidInjector(modules = CircleInvitationActivityModule.class)
    abstract CircleInvitationActivity bindCircleInvitationActivity();

    @ContributesAndroidInjector(modules = CallActivityModule.class)
    abstract CallActivity bindCallActivity();


    @ContributesAndroidInjector(modules = {
            MainActivityModule.class,
            AboutFragmentProvider.class,
            RateUsDialogProvider.class})
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = SplashActivityModule.class)
    abstract SplashActivity bindSplashActivity();
}
