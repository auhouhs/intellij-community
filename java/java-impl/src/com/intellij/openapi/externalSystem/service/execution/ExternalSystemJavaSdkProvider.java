// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.intellij.openapi.externalSystem.service.execution;

import com.intellij.openapi.projectRoots.JavaSdk;
import com.intellij.openapi.projectRoots.JdkUtil;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.SdkType;
import com.intellij.openapi.projectRoots.impl.JavaAwareProjectJdkTableImpl;
import com.intellij.openapi.vfs.VirtualFileManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;

public final class ExternalSystemJavaSdkProvider implements ExternalSystemJdkProvider {
  @NotNull
  @Override
  public SdkType getJavaSdkType() {
    return JavaSdk.getInstance();
  }

  @NotNull
  @Override
  public Sdk getInternalJdk() {
    return JavaAwareProjectJdkTableImpl.getInstanceEx().getInternalJdk();
  }

  @NotNull
  @Override
  public Sdk createJdk(@Nullable String jdkName, @NotNull String homePath) {
    SdkType javaSdk = getJavaSdkType();
    String sdkName = jdkName != null ? jdkName : javaSdk.suggestSdkName(null, homePath);
    // We must refresh the location of JDK in VFS before creating an IDE object for it,
    // because the jdk may be downloaded by Gradle and be out of sync at the moment of reaching this place.
    // https://youtrack.jetbrains.com/issue/IJPL-784/Certain-Gradle-directories-are-not-tracked-by-VFS
    VirtualFileManager.getInstance().refreshAndFindFileByNioPath(Path.of(homePath));
    return ((JavaSdk)javaSdk).createJdk(sdkName, homePath, !JdkUtil.checkForJdk(homePath));
  }
}
