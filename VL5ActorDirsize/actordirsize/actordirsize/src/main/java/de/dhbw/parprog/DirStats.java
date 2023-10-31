package de.dhbw.parprog;

public class DirStats {
	final int fileCount;
	final long totalSize;
	
	public DirStats() {
		fileCount = 0;
		totalSize = 0;
	}

	public DirStats(int fileCount, long totalSize) {
		this.fileCount = fileCount;
		this.totalSize = totalSize;
	}

	public DirStats add(DirStats other) {
		return new DirStats(fileCount + other.fileCount, totalSize + other.totalSize);
	}

	@Override
	public String toString() {
		return "DirStats(fileCount=" + fileCount + ", totalSize=" + totalSize + ")";
	}
}
