pipelineJob('First_job') {
  definition {
    cpsScm {
      scm {
        git {
          remote {
            url('https://github.com/jenkinsci/job-dsl-plugin.git')
          }
          branch('*/master')
        }
      }
      lightweight()
    }
  }
}