package gantt

application(title: 'gantt',
  preferredSize: [800,600],
  pack: true,
  //location: [50,50],
  locationByPlatform: true,
  iconImage:   imageIcon('/griffon-icon-48x48.png').image,
  iconImages: [imageIcon('/griffon-icon-48x48.png').image,
               imageIcon('/griffon-icon-32x32.png').image,
               imageIcon('/griffon-icon-16x16.png').image]) {
    borderLayout()
    panel(id:"campaignGanttPanel", cssClass:"campaignGanttPanel") {      
      migLayout(layoutConstraints: "fill")
      button(text:"Load Gantt",actionPerformed:controller.clickAction,constraints:"wrap")
      widget(id:'ganttBridge',new GanttSWTBridge(controller))
      model.ganttBridge=ganttBridge
    }
}
