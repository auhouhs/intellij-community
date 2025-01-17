// Copyright 2000-2024 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.intellij.platform.ml.impl

import com.intellij.platform.ml.impl.apiPlatform.MLApiPlatform
import com.intellij.platform.ml.impl.apiPlatform.MLApiPlatform.ExtensionController
import com.intellij.platform.ml.impl.monitoring.MLTaskGroupListener

abstract class TestApiPlatform : MLApiPlatform() {
  private val dynamicTaskListeners: MutableList<MLTaskGroupListener> = mutableListOf()

  abstract val initialTaskListeners: List<MLTaskGroupListener>

  final override val taskListeners: List<MLTaskGroupListener>
    get() = initialTaskListeners + dynamicTaskListeners

  override fun addTaskListener(taskListener: MLTaskGroupListener): ExtensionController {
    return extend(taskListener, dynamicTaskListeners)
  }

  private fun <T> extend(obj: T, collection: MutableCollection<T>): ExtensionController {
    collection.add(obj)
    return ExtensionController { collection.remove(obj) }
  }
}
