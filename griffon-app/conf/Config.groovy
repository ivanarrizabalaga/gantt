log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    appenders {
        console name: 'stdout', layout: pattern(conversionPattern: '%d [%t] %-5p %c - %m%n')
    }


    debug	'griffon.app',
		    'griffon.core',
		    'griffon.swing',
		    'gantt'
}

