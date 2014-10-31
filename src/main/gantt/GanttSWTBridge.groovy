package gantt

import java.awt.Canvas

import org.apache.log4j.Logger
import org.eclipse.nebula.widgets.ganttchart.GanttChart
import org.eclipse.nebula.widgets.ganttchart.GanttEvent
import org.eclipse.swt.SWT
import org.eclipse.swt.awt.SWT_AWT
import org.eclipse.swt.layout.FillLayout
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Shell

class GanttSWTBridge extends Canvas {
	
	private static final Logger log = Logger.getLogger(this.class)
	
	Thread swtThread
	GanttChart ganttChart
	
	def controller
	
	GanttSWTBridge(def controller){
		System.setProperty("sun.awt.xembedserver", "true");
		this.controller=controller
	}
	
	public void connect() {		
		if (this.swtThread == null) {
		  final Canvas canvas = this;
		  this.swtThread = new Thread() {
			@Override
			public void run() {
			  try {
			  	log.debug "Creating display and  shell"
				Display display = new Display();
				Shell shell = SWT_AWT.new_Shell(display, canvas);
				shell.setLayout(new FillLayout());
	 
				synchronized (this) {
				  log.debug "Adding gantt into shell"
				  ganttChart = new GanttChart(shell, SWT.NONE);
				  this.notifyAll();
				}
	 
	 			log.debug "Opening shell"
				shell.open();
				while (!isInterrupted() && !shell.isDisposed()) {
				  log.debug "SWT Loop"
				  if (!display.readAndDispatch()) {
					display.sleep();
				  }
				}
				shell.dispose();
				display.dispose();
			  } catch (Exception e) {
			  	log.debug "Exception: $e"
				interrupt();
			  }
			}
		  };
		  this.swtThread.start();
		}
	 
		// Wait for the Browser instance to become ready
		synchronized (this.swtThread) {
		  while (this.ganttChart == null) {
			try {
			  this.swtThread.wait(100);
			} catch (InterruptedException e) {
			  log.debug "InterruptedException: $e"
			  this.ganttChart = null;
			  this.swtThread = null;
			  break;
			}
		  }
		}
	  }
	
	/**
	 * Stops the swt background thread.
	 */
	public void disconnect() {
	  if (swtThread != null) {
		ganttChart = null;
		swtThread.interrupt();
		swtThread = null;
	  }
	}
	
	/**
	 * Ensures that the SWT background thread
	 * is stopped if this canvas is removed from
	 * it's parent component (e.g. because the
	 * frame has been disposed).
	 */
	@Override
	public void removeNotify() {
	  super.removeNotify();
	  disconnect();
	}
	
	void init(){		 
		 this.ganttChart.getDisplay().asyncExec(new Runnable() {
		   @Override
		   public void run() {
			  
			 log.debug "Adding data to gantt"		
			 // make a 10 day long event
			 Calendar cStartDate = Calendar.getInstance(Locale.getDefault());
			 Calendar cEndDate = Calendar.getInstance(Locale.getDefault());
			 cEndDate.add(Calendar.DATE, 10);
			  
			 // make some revised dates
			 Calendar rStartDate = Calendar.getInstance(Locale.getDefault());
			 Calendar rEndDate = Calendar.getInstance(Locale.getDefault());
			 // pretend dates were 2 days earlier than the start date and 5 days later than the end date
			 rStartDate.add(Calendar.DATE, -2);
			 rEndDate.add(Calendar.DATE, 15);

			 GanttEvent revisedEvent = new GanttEvent(ganttChart, "Revised", cStartDate, cEndDate, rStartDate, rEndDate, 50);
		   }
		 });
		
	}
	
}
