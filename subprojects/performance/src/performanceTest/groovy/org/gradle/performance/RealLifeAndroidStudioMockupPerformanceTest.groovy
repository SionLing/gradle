/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.performance

import org.gradle.performance.categories.AndroidPerformanceTest
import org.junit.experimental.categories.Category
import spock.lang.Unroll

@Category([AndroidPerformanceTest])
class RealLifeAndroidStudioMockupPerformanceTest extends AbstractAndroidStudioMockupCrossVersionPerformanceTest {

    @Unroll
    def "simulate Android Studio #template synchronization"() {
        given:

        experiment(template, "simulate Android Studio $template synchronization") {
            action('org.gradle.performance.android.SyncAction', 'sync')
        }

        when:
        def results = performMeasurements()

        then:
        results.assertCurrentVersionHasNotRegressed()

        where:
        template << ["mediumAndroidBuild", "largeAndroidBuild"]
    }

}