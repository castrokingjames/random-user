/*
 * Copyright 2024, King James Castro and project contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package timber.log

import timber.log.Timber.ASSERT
import timber.log.Timber.DEBUG
import timber.log.Timber.ERROR
import timber.log.Timber.INFO
import timber.log.Timber.VERBOSE
import timber.log.Timber.WARNING

abstract class Tree {
  /**
   * Returns true when [priority] will be logged. Behavior is undefined for values other than
   * [Timber.ASSERT], [Timber.ERROR], [Timber.WARNING], [Timber.INFO], [Timber.DEBUG], and
   * [Timber.VERBOSE].
   */
  open fun isLoggable(
    priority: Int,
    tag: String? = null,
  ) = true

  fun log(
    priority: Int,
    tag: String?,
    throwable: Throwable?,
    message: String?,
  ) {
    if (isLoggable(priority, tag)) {
      performLog(priority, tag, throwable, message)
    }
  }

  /** Invoked only when [isLoggable] has returned true. */
  @PublishedApi
  internal fun rawLog(
    priority: Int,
    tag: String?,
    throwable: Throwable?,
    message: String?,
  ) {
    performLog(priority, tag, throwable, message)
  }

  protected abstract fun performLog(
    priority: Int,
    tag: String?,
    throwable: Throwable?,
    message: String?,
  )
}

inline fun Tree.log(
  priority: Int,
  throwable: Throwable? = null,
  message: () -> String,
) {
  if (isLoggable(priority, null)) {
    rawLog(priority, null, throwable, message())
  }
}

inline fun Tree.assert(
  throwable: Throwable? = null,
  message: () -> String,
) {
  log(ASSERT, throwable, message)
}

inline fun Tree.error(
  throwable: Throwable? = null,
  message: () -> String,
) {
  log(ERROR, throwable, message)
}

inline fun Tree.warn(
  throwable: Throwable? = null,
  message: () -> String,
) {
  log(WARNING, throwable, message)
}

inline fun Tree.info(
  throwable: Throwable? = null,
  message: () -> String,
) {
  log(INFO, throwable, message)
}

inline fun Tree.debug(
  throwable: Throwable? = null,
  message: () -> String,
) {
  log(DEBUG, throwable, message)
}

inline fun Tree.verbose(
  throwable: Throwable? = null,
  message: () -> String,
) {
  log(VERBOSE, throwable, message)
}
