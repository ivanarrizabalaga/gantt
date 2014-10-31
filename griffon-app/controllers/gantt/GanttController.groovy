package gantt

import org.apache.log4j.Logger

class GanttController {
	// these will be injected by Griffon
	def model
	def view
	
	void mvcGroupInit(Map args) {
		log.debug "Init app"
	}

	def clickAction={evt=null->
		log.debug "Load Gantt"
		GanttSWTBridge ganttSWTBridge=((GanttSWTBridge)model.ganttBridge)
		doLater{
			ganttSWTBridge.connect()
			ganttSWTBridge.init()
		}		
	}

	void mvcGroupDestroy(){
		model.ganttBridge.disconnect()
	}
}
