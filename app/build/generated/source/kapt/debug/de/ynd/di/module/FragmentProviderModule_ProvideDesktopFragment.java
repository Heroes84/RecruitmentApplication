package de.ynd.di.module;

import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import de.ynd.ui.desktop.DesktopFragment;

@Module(
  subcomponents = FragmentProviderModule_ProvideDesktopFragment.DesktopFragmentSubcomponent.class
)
public abstract class FragmentProviderModule_ProvideDesktopFragment {
  private FragmentProviderModule_ProvideDesktopFragment() {}

  @Binds
  @IntoMap
  @ClassKey(DesktopFragment.class)
  abstract AndroidInjector.Factory<?> bindAndroidInjectorFactory(
      DesktopFragmentSubcomponent.Factory builder);

  @Subcomponent
  public interface DesktopFragmentSubcomponent extends AndroidInjector<DesktopFragment> {
    @Subcomponent.Factory
    interface Factory extends AndroidInjector.Factory<DesktopFragment> {}
  }
}
