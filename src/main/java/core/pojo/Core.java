package core.pojo;

import java.io.Serializable;

import lombok.Data;

@Data
public class Core implements Serializable {
	private static final long serialVersionUID = 1457755989409740329L;
	private boolean successful;
	private String message;

	public Core() {
	}

	public Core(boolean successful, String message) {
		this.successful = successful;
		this.message = message;
	}
}
