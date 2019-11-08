package it.objectmethod.secretaryquiz.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "resp")
public class Response {
	@GeneratedValue
	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "id_user")
	private int idUSer;

	@Column(name = "resp_one")
	private String respOne;

	@Column(name = "resp_two")
	private String respTwo;
	
	@Column(name = "resp_three")
	private String respThree;
	
	@Column(name = "resp_four")
	private String respFour;
	
	@Column(name = "resp_five")
	private String respFive;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdUSer() {
		return idUSer;
	}

	public void setIdUSer(int idUSer) {
		this.idUSer = idUSer;
	}

	public String getRespOne() {
		return respOne;
	}

	public void setRespOne(String respOne) {
		this.respOne = respOne;
	}

	public String getRespTwo() {
		return respTwo;
	}

	public void setRespTwo(String respTwo) {
		this.respTwo = respTwo;
	}

	public String getRespThree() {
		return respThree;
	}

	public void setRespThree(String respThree) {
		this.respThree = respThree;
	}

	public String getRespFour() {
		return respFour;
	}

	public void setRespFour(String respFour) {
		this.respFour = respFour;
	}

	public String getRespFive() {
		return respFive;
	}

	public void setRespFive(String respFive) {
		this.respFive = respFive;
	}

	

}
