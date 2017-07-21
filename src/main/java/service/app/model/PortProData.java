package service.app.model;

public class PortProData extends AllSimData {

	private Double diesel;
	private Double gasoline;
	private Double coal;
	private Double power;
	private Double other;
	
	private Double proTask;

	public Double getDiesel() {
		return diesel;
	}

	public void setDiesel(Double diesel) {
		this.diesel = diesel;
	}

	public Double getGasoline() {
		return gasoline;
	}

	public void setGasoline(Double gasoline) {
		this.gasoline = gasoline;
	}

	public Double getCoal() {
		return coal;
	}

	public void setCoal(Double coal) {
		this.coal = coal;
	}

	public Double getPower() {
		return power;
	}

	public void setPower(Double power) {
		this.power = power;
	}

	public Double getOther() {
		return other;
	}

	public void setOther(Double other) {
		this.other = other;
	}

	public Double getProTask() {
		return proTask;
	}

	public void setProTask(Double proTask) {
		this.proTask = proTask;
	}
}
