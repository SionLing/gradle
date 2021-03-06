/*
 * Copyright 2007 the original author or authors.
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

package org.gradle.api.internal.artifacts.publish

import org.gradle.api.Attribute
import org.gradle.api.Task

public class DefaultPublishArtifactTest extends AbstractPublishArtifactTest {

    def "init"() {
        given:
        def task1 = Mock(Task)
        def task2 = Mock(Task)

        when:
        def publishArtifact = new DefaultPublishArtifact(testName, testExt, testType,
                testClassifier, date, testFile, task1, task2)
        then:
        publishArtifact.buildDependencies.getDependencies(null) == [task1, task2] as Set
        assertCommonPropertiesAreSet(publishArtifact, true)
    }

    def "can specify the builder tasks on construction"() {
        given:
        def task = Mock(Task)

        when:
        def publishArtifact = new DefaultPublishArtifact("name", "extension", "type", null, null, null, task)

        then:
        publishArtifact.buildDependencies.getDependencies(null) == [task] as Set
    }

    def "can specify the builder tasks"() {
        given:
        def task = Mock(Task)

        when:
        def publishArtifact = new DefaultPublishArtifact("name", "extension", "type", null, null, null)
        publishArtifact.builtBy(task);

        then:
        publishArtifact.buildDependencies.getDependencies(null) == [task] as Set
    }

    def "can add attributes to artifact"() {
        given:
        def publishArtifact = new DefaultPublishArtifact("name", "extension", "type", null, null, null)
        def artifactType = Attribute.of('artifactType', String)

        when:
        publishArtifact.attributes.attribute(artifactType, 'jar')

        then:
        publishArtifact.attributes.contains(artifactType)
        publishArtifact.attributes.getAttribute(artifactType) == 'jar'
    }

    def "can add strongly-typed attributes to artifact"() {
        given:
        def publishArtifact = new DefaultPublishArtifact("name", "extension", "type", null, null, null)
        def artifactType = Attribute.of(ArtifactType)

        when:
        publishArtifact.attributes.attribute(artifactType, ArtifactType.JAR)

        then:
        publishArtifact.attributes.contains(artifactType)
        publishArtifact.attributes.getAttribute(artifactType) == ArtifactType.JAR
    }

    enum ArtifactType {
        JAR,
        WAR,
        ZIP
    }
}
