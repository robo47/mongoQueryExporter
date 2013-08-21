package net.robo47.apps.mongoQueryExporter;

import javax.swing.JTextField;

import org.apache.commons.lang.time.StopWatch;

public class ExporterStatus {

	private final StopWatch watch = new StopWatch();
	private final Runtime runtime = Runtime.getRuntime();

	private boolean done = false;
	private long currentRow = 0;
	private long fileSizeWritten = 0;
	private long skippedRows = 0;
	private boolean waiting = false;
	private final JTextField statusField;

	private String status = "Initialized";

	public ExporterStatus(JTextField statusField) {
		this.statusField = statusField;
	}

	public long getCurrentRow() {
		return this.currentRow;
	}

	public long getFileSizeWritten() {
		return this.fileSizeWritten;
	}

	public long getSkippedRows() {
		return this.skippedRows;
	}

	public StopWatch getWatch() {
		return this.watch;
	}

	public void setCurrentRow(long currentRow) {
		this.currentRow = currentRow;
	}

	public void setFileSizeWritten(long fileSizeWritten) {
		this.fileSizeWritten = fileSizeWritten;
	}

	public void setSkippedRows(long skippedRows) {
		this.skippedRows = skippedRows;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getUsedMemory() {
		return runtime.totalMemory() - runtime.freeMemory();
	}

	public void updateStatusRowFromExportStatus() {
		String row = "Lines " + currentRow + " memory: "
				+ ExporterUtil.humanReadableByteCount(getUsedMemory())
				+ " exportFileSize: "
				+ ExporterUtil.humanReadableByteCount(fileSizeWritten)
				+ " skipped: " + skippedRows + " "
				+ ((waiting == true) ? "Waiting" : "");
		setStatus(row);
	}

	public void setWaiting(boolean waiting) {
		this.waiting = waiting;
	}

	public void updateStatusField() {
		statusField.setText(status);
		statusField.update(statusField.getGraphics());
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public boolean isDone() {
		return this.done;
	}
}