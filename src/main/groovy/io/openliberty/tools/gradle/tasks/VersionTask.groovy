/**
 * (C) Copyright IBM Corporation 2014, 2020.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.openliberty.tools.gradle.tasks

import io.openliberty.tools.common.plugins.util.InstallFeatureUtil
import org.gradle.api.Project
import org.gradle.api.logging.LogLevel

import org.gradle.api.tasks.TaskAction
import io.openliberty.tools.ant.ServerTask

import java.util.Map.Entry
import java.util.Set

class VersionTask extends InstallLibertyTask {
    VersionTask() {
        configure({
            description 'Returns the version of the liberty installation'
            group 'Liberty'
        })
        skipInstall = true;
    }

    @TaskAction
    void checkVersion() {
      String version;

      File serverInstallDir = getInstallDir(project);
        // If the server is installed, return the version from the installed liberty properties file
        if (serverInstallDir != null && serverInstallDir.exists()) {
            List<InstallFeatureUtil.ProductProperties> propertiesList = InstallFeatureUtil.loadProperties(serverInstallDir);
            logger.warn("Retrieving version from install directory");
            version = InstallFeatureUtil.getOpenLibertyVersion(propertiesList);
        }
        // else use the version configured in the gradle project
        else {
            // TODO 
            if (project.liberty.runtime.version != null) {
                version = project.liberty.runtime.version
            } else {
                def params = buildInstallLibertyMap(project)
                params.each{ k, v -> logger.warn("${k}:${v}")}
                // could manually get the version from the resolved runtimeURL but might be a better way to do this 
            }
        }
        logger.warn(version)
    }
}
