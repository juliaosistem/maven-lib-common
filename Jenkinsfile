pipeline {
    agent {
        kubernetes {
            yaml "apiVersion: v1\nkind: Pod\nspec:\n  containers:\n  - name: maven\n    image: maven:3.9.9-eclipse-temurin-21\n    command: [\"cat\"]\n    tty: true\n"
        }
    }

    environment {
        NEXUS_MAVEN_BASE_URL = 'https://nexus.twincode.site'
        NEXUS_MAVEN_REPOSITORY = 'maven-releases'
        NEXUS_CREDS_ID = 'nexus-credentials'
        COMMON_PARENT_POM = 'common-parent/pom.xml'
        COMMON_LIB_POM = 'pom.xml'
        ARTIFACT_VERSION = ''
    }

    options {
        timeout(time: 45, unit: 'MINUTES')
        timestamps()
        disableConcurrentBuilds()
        overrideIndexTriggers(true)
    }

    triggers {
        githubPush()
        pollSCM('H/5 * * * *')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Resolve Version') {
            when {
                anyOf {
                    branch 'develop'
                    branch 'master'
                }
            }
            steps {
                container('maven') {
                    script {
                        def branchName = (env.BRANCH_NAME ?: '').trim()
                        def buildNumber = env.BUILD_NUMBER ?: '0'
                        def artifactVersion = ''

                        if (branchName == 'develop' || branchName.endsWith('/develop')) {
                            artifactVersion = "develop.${buildNumber}"
                        } else if (branchName == 'master' || branchName.endsWith('/master')) {
                            sh 'git fetch --tags --force'
                            def tagAtHead = sh(script: "git tag --points-at HEAD | head -n 1", returnStdout: true).trim()
                            if (!tagAtHead) {
                                error('En branch master se requiere un tag en el commit (ej: v1.2.3).')
                            }
                            artifactVersion = tagAtHead.startsWith('v') ? tagAtHead.substring(1) : tagAtHead
                        } else {
                            // Fallback defensivo para ramas no estandar.
                            def safeBranch = branchName ? branchName.replaceAll('[^A-Za-z0-9._-]', '-') : 'unknown'
                            artifactVersion = "${safeBranch}.${buildNumber}"
                        }

                        def resolvedVersion = (artifactVersion ?: '').trim()
                        if (!resolvedVersion) {
                            error('No se pudo resolver ARTIFACT_VERSION para la build actual.')
                        }

                        env.ARTIFACT_VERSION = resolvedVersion

                        // Repository target can be switched automatically if a SNAPSHOT version is used.
                        env.NEXUS_MAVEN_REPOSITORY = resolvedVersion.toUpperCase().endsWith('-SNAPSHOT') ? 'maven-snapshots' : 'maven-releases'
                        currentBuild.displayName = "#${env.BUILD_NUMBER} ${env.BRANCH_NAME} ${env.ARTIFACT_VERSION}"
                        echo "Version de artefacto: ${resolvedVersion}"
                        echo "Repositorio destino Nexus: ${env.NEXUS_MAVEN_REPOSITORY}"

                        sh """
                            set -e
                            mvn -B -ntp -f \"$COMMON_PARENT_POM\" versions:set -DnewVersion=\"${resolvedVersion}\" -DgenerateBackupPoms=false
                            mvn -B -ntp -f \"$COMMON_LIB_POM\" versions:set -DnewVersion=\"${resolvedVersion}\" -DgenerateBackupPoms=false
                        """
                    }
                }
            }
        }

        stage('Build Common Parent + Common Lib') {
            when {
                anyOf {
                    branch 'develop'
                    branch 'master'
                }
            }
            steps {
                container('maven') {
                    sh '''
                        set -e
                        mvn -B -ntp -f "$COMMON_PARENT_POM" -DskipTests clean install
                        mvn -B -ntp -f "$COMMON_LIB_POM" -DskipTests clean package
                        ls -lh target/*.jar
                    '''
                }
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('Publish common-parent (pom)') {
            when {
                anyOf {
                    branch 'develop'
                    branch 'master'
                }
            }
            steps {
                container('maven') {
                    withCredentials([usernamePassword(credentialsId: "${NEXUS_CREDS_ID}", usernameVariable: 'NEXUS_USER', passwordVariable: 'NEXUS_PASS')]) {
                        sh '''
                            set -e
                            cat > .jenkins-settings.xml <<EOF
<settings>
  <servers>
    <server>
      <id>nexus</id>
      <username>${NEXUS_USER}</username>
      <password>${NEXUS_PASS}</password>
    </server>
  </servers>
</settings>
EOF

                            PARENT_GROUP=$(mvn -q -DforceStdout -f "$COMMON_PARENT_POM" help:evaluate -Dexpression=project.groupId)
                            PARENT_ARTIFACT=$(mvn -q -DforceStdout -f "$COMMON_PARENT_POM" help:evaluate -Dexpression=project.artifactId)
                            PARENT_VERSION=$(mvn -q -DforceStdout -f "$COMMON_PARENT_POM" help:evaluate -Dexpression=project.version)

                            mvn -B -ntp -s .jenkins-settings.xml deploy:deploy-file \
                              -DrepositoryId=nexus \
                              -Durl="${NEXUS_MAVEN_BASE_URL}/repository/${NEXUS_MAVEN_REPOSITORY}" \
                              -DgroupId="$PARENT_GROUP" \
                              -DartifactId="$PARENT_ARTIFACT" \
                              -Dversion="$PARENT_VERSION" \
                              -Dpackaging=pom \
                              -Dfile="$COMMON_PARENT_POM" \
                              -DpomFile="$COMMON_PARENT_POM"
                        '''
                    }
                }
            }
        }

        stage('Publish common-lib (jar)') {
            when {
                anyOf {
                    branch 'develop'
                    branch 'master'
                }
            }
            steps {
                container('maven') {
                    withCredentials([usernamePassword(credentialsId: "${NEXUS_CREDS_ID}", usernameVariable: 'NEXUS_USER', passwordVariable: 'NEXUS_PASS')]) {
                        sh '''
                            set -e
                            cat > .jenkins-settings.xml <<EOF
<settings>
  <servers>
    <server>
      <id>nexus</id>
      <username>${NEXUS_USER}</username>
      <password>${NEXUS_PASS}</password>
    </server>
  </servers>
</settings>
EOF

                            LIB_GROUP=$(mvn -q -DforceStdout -f "$COMMON_LIB_POM" help:evaluate -Dexpression=project.groupId)
                            LIB_ARTIFACT=$(mvn -q -DforceStdout -f "$COMMON_LIB_POM" help:evaluate -Dexpression=project.artifactId)
                            LIB_VERSION=$(mvn -q -DforceStdout -f "$COMMON_LIB_POM" help:evaluate -Dexpression=project.version)
                            LIB_FILE="target/${LIB_ARTIFACT}-${LIB_VERSION}.jar"

                            test -f "$LIB_FILE"

                            mvn -B -ntp -s .jenkins-settings.xml deploy:deploy-file \
                              -DrepositoryId=nexus \
                              -Durl="${NEXUS_MAVEN_BASE_URL}/repository/${NEXUS_MAVEN_REPOSITORY}" \
                              -DgroupId="$LIB_GROUP" \
                              -DartifactId="$LIB_ARTIFACT" \
                              -Dversion="$LIB_VERSION" \
                              -Dpackaging=jar \
                              -Dfile="$LIB_FILE" \
                              -DpomFile="$COMMON_LIB_POM"
                        '''
                    }
                }
            }
        }

        stage('Verify Artifact Reachability') {
            when {
                anyOf {
                    branch 'develop'
                    branch 'master'
                }
            }
            steps {
                container('maven') {
                    withCredentials([usernamePassword(credentialsId: "${NEXUS_CREDS_ID}", usernameVariable: 'NEXUS_USER', passwordVariable: 'NEXUS_PASS')]) {
                        sh '''
                            set -e
                            LIB_GROUP=$(mvn -q -DforceStdout -f "$COMMON_LIB_POM" help:evaluate -Dexpression=project.groupId)
                            LIB_ARTIFACT=$(mvn -q -DforceStdout -f "$COMMON_LIB_POM" help:evaluate -Dexpression=project.artifactId)
                            LIB_VERSION=$(mvn -q -DforceStdout -f "$COMMON_LIB_POM" help:evaluate -Dexpression=project.version)

                            GROUP_PATH=$(echo "$LIB_GROUP" | tr '.' '/')
                            ARTIFACT_URL="${NEXUS_MAVEN_BASE_URL}/repository/${NEXUS_MAVEN_REPOSITORY}/${GROUP_PATH}/${LIB_ARTIFACT}/${LIB_VERSION}/${LIB_ARTIFACT}-${LIB_VERSION}.jar"

                            curl -sfI -u "$NEXUS_USER:$NEXUS_PASS" "$ARTIFACT_URL" >/dev/null
                            echo "Artifact disponible en Nexus: $ARTIFACT_URL"
                        '''
                    }
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline de maven-lib-common finalizado.'
        }
    }
}