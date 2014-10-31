application {
    title = 'Gantt'
    startupGroups = ['gantt']

    // Should Griffon exit when no Griffon created frames are showing?
    autoShutdown = true

    // If you want some non-standard application class, apply it here
    //frameClass = 'javax.swing.JFrame'
}
mvcGroups {
    // MVC Group for "gantt"
    'gantt' {
        model      = 'gantt.GanttModel'
        view       = 'gantt.GanttView'
        controller = 'gantt.GanttController'
    }

}
