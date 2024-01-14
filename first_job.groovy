job('example-job') {
    description 'To jest przykładowe zadanie Jenkins'

    triggers {
        cron('H/15 * * * *')
    }

    steps {
        shell('echo "Hello, World!"')
    }
}