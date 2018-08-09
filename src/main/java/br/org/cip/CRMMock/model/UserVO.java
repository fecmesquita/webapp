package br.org.cip.CRMMock.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;

@Entity
@Table(name = "user")
public class UserVO implements User, Serializable{
	
	private static final long serialVersionUID = 4581848629921562132L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Transient
	private boolean authenticated;
	
	@Transient
	private Enrollment enrollment;

	@Column(name = "afiliacao")
	private String affiliation;

	@Column(name = "mspId")
	private String mspId;
	
	public UserVO() {
		super();
	}
	
	public UserVO(String name, String affiliation, String mspId, Enrollment enrollment) {
        this.username = name;
        this.affiliation = affiliation;
        this.enrollment = enrollment;
        this.mspId = mspId;
    }

	public Enrollment getEnrollment() {
		return enrollment;
	}

	public void setEnrollment(Enrollment enrollment) {
		this.enrollment = enrollment;
	}

	public boolean isAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + username+ "]";
	}

	@Override
	public String getName() {
		return this.getUsername();
	}

	@Override
	public Set<String> getRoles() {
		return null;
	}

	@Override
	public String getAccount() {
		return null;
	}

	@Override
	public String getAffiliation() {
		return affiliation;
	}

	@Override
	public String getMspId() {
		return mspId;
	}

}