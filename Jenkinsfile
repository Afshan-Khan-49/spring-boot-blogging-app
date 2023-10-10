pipeline {
  agent any
  parameters {
    string defaultValue: 'blogging-app', description: 'EBS App Name', name: 'APP_NAME', trim: true
    string defaultValue: 'elasticbeanstalk-ap-south-1-790440752804', description: 'Artifact Bucket', name: 'BUCKET_NAME', trim: true
    string defaultValue: 'ap-south-1', description: 'AWS Region', name: 'REGION', trim: true
    string defaultValue: 'Blogging-app-env', description: 'EBS Env Name', name: 'ENV_NAME', trim: true
  }
  stages {
    stage('Checkout code') {
      steps {
        git(url: 'https://github.com/Afshan-Khan-49/spring-boot-blogging-app.git', branch: 'main')
      }
    }

    stage('Build') {
      steps {
        sh 'mvn clean package -DskipTests'
      }
    }

    stage('Preparation') {
            steps {
                script {
                    echo "Fetching timestamp..."
    def currentTimestamp = new Date().format("yyyyMMddHHmmss", TimeZone.getTimeZone('UTC'))
    echo "Current timestamp: ${currentTimestamp}"

    echo "Fetching commit hash..."
    def commitHash = sh(script: 'git rev-parse --short=8 HEAD', returnStdout: true).trim()
    echo "Commit hash: ${commitHash}"

    buildLabel = "${params.APP_NAME}-${currentTimestamp}-${commitHash}"
    echo "Generated build label: ${buildLabel}"
                }
            }
        }

    stage('Upload artifact') {
            steps {
                sh "aws s3 cp target/blogging-app-0.0.1-SNAPSHOT.jar s3://${params.BUCKET_NAME}/${buildLabel}.jar"
            }
    }

    stage('Deploy') {
      steps {
        // Create a new application version in EBS
        sh "aws elasticbeanstalk create-application-version --application-name ${params.APP_NAME} --version-label ${buildLabel} --source-bundle S3Bucket=${params.BUCKET_NAME},S3Key=${buildLabel}.jar --region ${params.REGION}"

        // Update the EBS environment to use the new version
        sh "aws elasticbeanstalk update-environment --application-name ${params.APP_NAME} --environment-name ${params.ENV_NAME} --version-label ${buildLabel} --region ${params.REGION}"
      }
    }

  }

  post {
    always {
        // Always run, regardless of build status
        echo 'Cleaning up workspace...'
        cleanWs()
    }
    failure {
        // Runs only if the build fails
        echo 'Build failed. Check logs for details.'
    }
}

}